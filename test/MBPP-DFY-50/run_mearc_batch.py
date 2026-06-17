#!/usr/bin/env python3
"""
Run the mearc tool on each JSON object in test.json to translate
NL preconditions/postconditions into Dafny specifications,
then verify the translated code against Dafny.

Usage:
    python run_mearc_batch.py [--limit N] [--task-id ID]

Output files (in the same directory as this script):
    original_conditions.json   - extracted requires/ensures per task
    code_without_conditions/   - Dafny files with requires/ensures removed
    verification_results.json  - per-task translation + verification results
"""

import json
import os
import re
import subprocess
import sys
import argparse

# ============================================================================
# Configuration
# ============================================================================
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
HAFIS_ROOT = os.path.abspath(os.path.join(SCRIPT_DIR, "..", ".."))
DAFYNY_BIN = "/mnt/e/Post_Phd_work/Software/dafny/dafny"
MEARC_CMD = "bash ./mearc"


# ============================================================================
# WSL helpers
# ============================================================================
def is_wsl():
    """Check if running under WSL."""
    try:
        result = subprocess.run(
            ["wsl", "--list"], capture_output=True, timeout=5
        )
        return result.returncode == 0
    except Exception:
        return False


USE_WSL = is_wsl()


def to_wsl_path(win_path):
    """Convert a Windows absolute path to a WSL /mnt/... path."""
    win_path = str(win_path).replace("/", "\\")
    if win_path.startswith("\\\\"):
        return win_path
    if len(win_path) >= 2 and win_path[1] == ":":
        drive = win_path[0].lower()
        rest = win_path[2:].replace("\\", "/")
        return f"/mnt/{drive}{rest}"
    return win_path.replace("\\", "/")


def run_cmd(cmd, cwd=None, timeout=300):
    """Run a shell command, using WSL if needed. Returns (returncode, stdout, stderr)."""
    if USE_WSL:
        if cwd:
            cwd_wsl = to_wsl_path(cwd)
            full_cmd = f'wsl --user root -- bash -c "cd {cwd_wsl} && {cmd}"'
        else:
            full_cmd = f'wsl --user root -- bash -c "{cmd}"'
    else:
        full_cmd = cmd

    try:
        result = subprocess.run(
            full_cmd,
            capture_output=True,
            text=True,
            timeout=timeout,
            shell=True,
        )
        return result.returncode, result.stdout, result.stderr
    except subprocess.TimeoutExpired:
        return -1, "", "Command timed out"
    except Exception as e:
        return -1, "", str(e)


# ============================================================================
# NL text helpers
# ============================================================================
def parse_nl_conditions(text):
    """Parse NL precondition/postcondition text into a list of condition strings."""
    if not text:
        return []
    # Skip "no precondition" messages
    if re.search(r"no\s+(preconditions?|conditions?)", text, re.IGNORECASE):
        return []
    lines = text.strip().split("\n")
    conditions = []
    for line in lines:
        line = line.strip()
        # Remove leading bullet: "- ", "* ", "• "
        line = re.sub(r"^[-*•]\s+", "", line)
        line = line.strip()
        if line:
            conditions.append(line)
    return conditions


def generate_annotations(method_sig, preconditions, postconditions):
    """Generate a minimal annotated .dfy file content for mearc.

    mearc inserts translated clauses after ``method`` declaration lines,
    so we wrap the NL annotations inside a minimal method skeleton.
    """
    # Extract just "method Name(params)" from the full signature
    # (strip any "returns (...)" part so ensures clauses are syntactically valid)
    sig_line = method_sig.strip()
    sig_line = re.sub(r"\s+returns\s*\(.*\)\s*$", "", sig_line)
    if not sig_line:
        sig_line = "method __dummy__()"

    parts = [sig_line]
    for pre in preconditions:
        parts.append(f"    // requires(*{pre}*);")
    for post in postconditions:
        parts.append(f"    // ensures(*{post}*);")
    parts.append("{")
    parts.append("}")
    return "\n".join(parts) + "\n"  # trailing newline for bash read


# ============================================================================
# Code manipulation
# ============================================================================
def strip_requires_ensures(code):
    """
    Remove all requires and ensures clauses (and their preceding single-line
    comments) from Dafny code.  Keeps modifies, decreases, reads, invariants,
    and everything else.

    Returns (stripped_code, list_of_removed_clauses).
    """
    lines = code.split("\n")
    result = []
    removed = []
    pending_comments = []

    for line in lines:
        stripped = line.strip()

        # ---- collect consecutive comment lines ----
        if stripped.startswith("//") and not _is_nl_annotation(stripped):
            pending_comments.append(line)
            continue

        # ---- requires / ensures ----
        if re.match(r"^(requires|ensures)\s+", stripped):
            # discard any pending comments (they belong to this clause)
            pending_comments = []
            removed.append(stripped)
            # consume continuation lines (no clause keyword, not opening brace)
            continue

        # ---- flush pending comments (they are standalone) ----
        result.extend(pending_comments)
        pending_comments = []
        result.append(line)

    return "\n".join(result), removed


def _is_nl_annotation(line):
    """True if the line is a mearc NL annotation comment."""
    return bool(
        re.search(r"//\s*(requires|ensures|assert)\(\*", line)
    )


def insert_clauses_after_signatures(code, requires_list, ensures_list):
    """
    Insert translated requires/ensures clauses after every method signature
    in *code*.  Returns the new code.
    """
    if not requires_list and not ensures_list:
        return code

    clause_lines = []
    for r in requires_list:
        clause_lines.append(f"    requires {r}")
    for e in ensures_list:
        clause_lines.append(f"    ensures {e}")

    lines = code.split("\n")
    result = []
    for line in lines:
        result.append(line)
        if re.match(r"^\s*method\s+\w+", line):
            for cl in clause_lines:
                result.append(cl)

    return "\n".join(result)


# ============================================================================
# mearc interaction
# ============================================================================
def run_mearc(annotated_dfy_path, rel_path=None):
    """
    Run the mearc tool on *annotated_dfy_path*.
    *rel_path* is the path relative to HAFIS_ROOT (used for the mearc command).
    Returns (success: bool, translated_path: str, stderr: str).
    """
    filename = os.path.basename(annotated_dfy_path)
    index = os.path.splitext(filename)[0]
    folder = os.path.dirname(annotated_dfy_path)
    translated_path = os.path.join(folder, f"{index}.translated.dfy")

    input_arg = rel_path if rel_path else filename
    cmd = f"{MEARC_CMD} {input_arg}"
    rc, stdout, stderr = run_cmd(cmd, cwd=HAFIS_ROOT, timeout=300)

    # mearc may return non-zero but still produce output; check file
    if os.path.exists(translated_path):
        return True, translated_path, stderr
    return False, "", stderr


def extract_translated_clauses(translated_path):
    """
    Read the mearc-translated .dfy file and return
    (requires_list, ensures_list) of Dafny expression strings.
    """
    requires = []
    ensures = []
    with open(translated_path, "r", encoding="utf-8") as f:
        for line in f:
            s = line.strip()
            m = re.match(r"^requires\s+(.+)", s)
            if m:
                requires.append(m.group(1).strip())
                continue
            m = re.match(r"^ensures\s+(.+)", s)
            if m:
                ensures.append(m.group(1).strip())
    return requires, ensures


# ============================================================================
# Dafny verification
# ============================================================================
def verify_with_dafny(dfy_path):
    """
    Run Dafny verification on *dfy_path*.
    Returns (verified: bool, output: str).
    """
    dfy_wsl = to_wsl_path(dfy_path)
    cmd = f"{DAFYNY_BIN} verify {dfy_wsl}"
    rc, stdout, stderr = run_cmd(cmd, cwd=HAFIS_ROOT, timeout=120)
    output = stdout + stderr
    verified = rc == 0
    return verified, output


# ============================================================================
# Main
# ============================================================================
def main():
    parser = argparse.ArgumentParser(
        description="Batch-translate NL specs to Dafny via mearc and verify."
    )
    parser.add_argument(
        "--limit", type=int, default=0,
        help="Process only the first N tasks (0 = all)",
    )
    parser.add_argument(
        "--task-id", type=str, default=None,
        help="Process only the task with this ID",
    )
    args = parser.parse_args()

    input_json = os.path.join(SCRIPT_DIR, "test.json")
    if not os.path.exists(input_json):
        print(f"[ERROR] test.json not found at {input_json}")
        sys.exit(1)

    with open(input_json, "r", encoding="utf-8") as f:
        data = json.load(f)

    # Output locations
    orig_cond_path = os.path.join(SCRIPT_DIR, "original_conditions.json")
    code_no_cond_dir = os.path.join(SCRIPT_DIR, "code_without_conditions")
    results_path = os.path.join(SCRIPT_DIR, "verification_results.json")

    os.makedirs(code_no_cond_dir, exist_ok=True)

    # ------------------------------------------------------------------
    results = {}
    original_conditions = {}

    task_ids = list(data.keys())
    if args.task_id:
        task_ids = [t for t in task_ids if t == args.task_id]
    if args.limit > 0:
        task_ids = task_ids[: args.limit]

    total = len(task_ids)
    print(f"Processing {total} task(s)...")

    for idx, task_id in enumerate(task_ids, 1):
        task = data[task_id]
        code = task["code"]
        spec = task.get("specification", {})
        pre_nl = spec.get("preconditions", "")
        post_nl = spec.get("postconditions", "")
        method_sig = spec.get("method_signature", "")

        print(f"\n[{idx}/{total}] Task {task_id}")

        # ---- 1. Parse NL conditions ----
        preconditions = parse_nl_conditions(pre_nl)
        postconditions = parse_nl_conditions(post_nl)
        print(f"  Preconditions : {len(preconditions)}")
        print(f"  Postconditions: {len(postconditions)}")

        original_conditions[task_id] = {
            "preconditions": preconditions,
            "postconditions": postconditions,
        }

        # ---- 2. Generate annotated .dfy (NL annotations in method skeleton) ----
        annotated_content = generate_annotations(method_sig, preconditions, postconditions)
        if not preconditions and not postconditions:
            print("  [SKIP] No NL conditions to translate")
            results[task_id] = {
                "task_id": task_id,
                "status": "skipped",
                "reason": "no NL conditions",
                "verified": False,
                "translated_requires": [],
                "translated_ensures": [],
                "dafny_output": "",
            }
            continue

        tmp_dir = os.path.join(SCRIPT_DIR, "tmp", task_id)
        os.makedirs(tmp_dir, exist_ok=True)
        annotated_path = os.path.join(tmp_dir, f"{task_id}.dfy")
        with open(annotated_path, "w", encoding="utf-8") as f:
            f.write(annotated_content)
        
        # mearc needs the path relative to HAFIS_ROOT so it can derive folder/index
        rel_path = os.path.relpath(annotated_path, HAFIS_ROOT).replace("\\", "/")

        # ---- 3. Run mearc ----
        print("  Running mearc ...")
        success, translated_path, mearc_stderr = run_mearc(annotated_path, rel_path)

        if not success:
            print(f"  [FAIL] mearc failed")
            print(f"         {mearc_stderr[:300]}")
            results[task_id] = {
                "task_id": task_id,
                "status": "translation_failed",
                "verified": False,
                "translated_requires": [],
                "translated_ensures": [],
                "dafny_output": mearc_stderr,
            }
            continue

        # ---- 4. Extract translated clauses ----
        trans_req, trans_ens = extract_translated_clauses(translated_path)
        print(f"  Translated requires: {trans_req}")
        print(f"  Translated ensures : {trans_ens}")

        # ---- 5. Strip original requires/ensures from code ----
        stripped_code, removed = strip_requires_ensures(code)

        # Save stripped code
        stripped_path = os.path.join(code_no_cond_dir, f"{task_id}_stripped.dfy")
        with open(stripped_path, "w", encoding="utf-8") as f:
            f.write(stripped_code)

        # Save removed conditions
        removed_path = os.path.join(code_no_cond_dir, f"{task_id}_removed.json")
        with open(removed_path, "w", encoding="utf-8") as f:
            json.dump({"removed_clauses": removed}, f, indent=2)

        # ---- 6. Combine stripped code + translated clauses ----
        combined = insert_clauses_after_signatures(
            stripped_code, trans_req, trans_ens
        )

        combined_path = os.path.join(code_no_cond_dir, f"{task_id}_combined.dfy")
        with open(combined_path, "w", encoding="utf-8") as f:
            f.write(combined)

        # ---- 7. Verify with Dafny ----
        print("  Verifying with Dafny ...")
        verified, dafny_output = verify_with_dafny(combined_path)

        status = "verified" if verified else "verification_failed"
        tag = "PASS" if verified else "FAIL"
        print(f"  [{tag}] {status}")
        if not verified:
            # Print first few lines of dafny output for quick diagnosis
            for ln in dafny_output.strip().split("\n")[:5]:
                print(f"         {ln}")

        results[task_id] = {
            "task_id": task_id,
            "status": status,
            "verified": verified,
            "translated_requires": trans_req,
            "translated_ensures": trans_ens,
            "removed_clauses": removed,
            "dafny_output": dafny_output,
        }

    # ------------------------------------------------------------------
    # Write outputs
    # ------------------------------------------------------------------
    with open(orig_cond_path, "w", encoding="utf-8") as f:
        json.dump(original_conditions, f, indent=2)
    print(f"\nOriginal conditions -> {orig_cond_path}")

    with open(results_path, "w", encoding="utf-8") as f:
        json.dump(results, f, indent=2)
    print(f"Verification results  -> {results_path}")

    # Summary
    passed = sum(1 for r in results.values() if r.get("verified"))
    failed = sum(
        1 for r in results.values() if r.get("status") == "verification_failed"
    )
    trans_err = sum(
        1 for r in results.values() if r.get("status") == "translation_failed"
    )
    skipped = sum(1 for r in results.values() if r.get("status") == "skipped")

    print(f"\n{'=' * 50}")
    print(f"  Total    : {total}")
    print(f"  Verified : {passed}")
    print(f"  Failed   : {failed}")
    print(f"  Trans.Err: {trans_err}")
    print(f"  Skipped  : {skipped}")
    print(f"{'=' * 50}")


if __name__ == "__main__":
    main()
