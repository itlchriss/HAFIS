#!/usr/bin/env python3
"""
Convert Java Solution files to Dafny format.
Provides complete translation of method signatures, annotations, and method bodies.
"""

import re
import sys
from pathlib import Path
from typing import List, Tuple, Optional

# Java to Dafny type mapping
TYPE_MAP = {
    'int': 'int', 'long': 'int', 'short': 'int', 'byte': 'int',
    'boolean': 'bool', 'char': 'char', 'String': 'string',
    'double': 'real', 'float': 'real', 'void': 'void',
}

BOXED_MAP = {
    'Integer': 'int', 'Long': 'int', 'Boolean': 'bool',
    'Character': 'char', 'Double': 'real', 'Float': 'real',
}


def java_type_to_dafny(java_type: str) -> str:
    """Convert Java type to Dafny type."""
    java_type = java_type.strip()
    if java_type.endswith('[]'):
        return f"array<{java_type_to_dafny(java_type[:-2])}>"
    generic_match = re.match(r'(\w+)<(.+)>', java_type)
    if generic_match:
        base, params = generic_match.group(1), generic_match.group(2)
        if base in ('List', 'ArrayList', 'LinkedList'):
            return f"seq<{java_type_to_dafny(params)}>"
        elif base in ('Set', 'HashSet', 'TreeSet'):
            return f"set<{java_type_to_dafny(params)}>"
        elif base in ('Map', 'HashMap', 'TreeMap'):
            parts = _split_generic_params(params)
            return f"map<{','.join(java_type_to_dafny(p) for p in parts)}>"
        elif base in ('Stack',):
            return f"seq<{java_type_to_dafny(params)}>"
    if java_type in BOXED_MAP:
        return BOXED_MAP[java_type]
    if java_type in TYPE_MAP:
        return TYPE_MAP[java_type]
    return java_type


def _split_generic_params(params: str) -> List[str]:
    result, depth, current = [], 0, ''
    for ch in params:
        if ch == '<': depth += 1
        elif ch == '>': depth -= 1
        elif ch == ',' and depth == 0:
            result.append(current.strip()); current = ''; continue
        current += ch
    if current.strip():
        result.append(current.strip())
    return result


def parse_parameters(params_str: str) -> List[Tuple[str, str]]:
    if not params_str or not params_str.strip():
        return []
    params, depth, current = [], 0, ''
    for ch in params_str:
        if ch == '<': depth += 1
        elif ch == '>': depth -= 1
        elif ch == ',' and depth == 0:
            params.append(current.strip()); current = ''; continue
        current += ch
    if current.strip():
        params.append(current.strip())
    result = []
    for param in params:
        param = param.replace('final ', '')
        parts = param.rsplit(' ', 1)
        if len(parts) == 2:
            result.append((parts[0].strip(), parts[1].strip()))
    return result


def _extract_paren_cond(text: str, after_prefix: int) -> Optional[str]:
    """Extract the condition inside balanced parentheses.
    after_prefix should be the index right after the opening '('.
    Returns the condition string, or None if parens are unbalanced."""
    depth = 1
    j = after_prefix
    while j < len(text) and depth > 0:
        if text[j] == '(': depth += 1
        elif text[j] == ')': depth -= 1
        if depth == 0:
            return text[after_prefix:j]
        j += 1
    return None


def convert_expr(expr: str) -> str:
    """Convert a Java expression to Dafny."""
    expr = expr.strip()
    # .length / .size()
    expr = re.sub(r'(\w+)\.length\b', r'|\1|', expr)
    expr = re.sub(r'(\w+)\.length\(\)', r'|\1|', expr)
    expr = re.sub(r'(\w+)\.size\(\)', r'|\1|', expr)
    # Math
    expr = re.sub(r'Math\.max\(([^,]+),\s*([^)]+)\)', r'max(\1, \2)', expr)
    expr = re.sub(r'Math\.min\(([^,]+),\s*([^)]+)\)', r'min(\1, \2)', expr)
    expr = re.sub(r'Math\.abs\(([^)]+)\)', r'if \1 >= 0 then \1 else -\1', expr)
    # Constants
    expr = expr.replace('Integer.MIN_VALUE', '-2147483648')
    expr = expr.replace('Integer.MAX_VALUE', '2147483647')
    expr = expr.replace('Long.MIN_VALUE', '-9223372036854775808')
    expr = expr.replace('Long.MAX_VALUE', '9223372036854775807')
    # .charAt(i) -> [i]
    expr = re.sub(r'(\w+)\.charAt\(([^)]+)\)', r'\1[\2]', expr)
    # .equals()
    expr = re.sub(r'(\w+)\.equals\(([^)]+)\)', r'\1 == \2', expr)
    # .contains()
    expr = re.sub(r'(\w+)\.containsKey\(([^)]+)\)', r'\2 in \1', expr)
    expr = re.sub(r'(\w+)\.contains\(([^)]+)\)', r'\2 in \1', expr)
    # .isEmpty()
    expr = re.sub(r'(\w+)\.isEmpty\(\)', r'|\1| == 0', expr)
    # .get()
    expr = re.sub(r'(\w+)\.get\(([^)]+)\)', r'\1[\2]', expr)
    # .peek()
    expr = re.sub(r'(\w+)\.peek\(\)', r'\1[|\1|-1]', expr)
    # Integer.toString(x) -> "" + x  (approximate)
    expr = re.sub(r'Integer\.toString\(([^)]+)\)', r'"" + \1', expr)
    # String.valueOf(x)
    expr = re.sub(r'String\.valueOf\(([^)]+)\)', r'"" + \1', expr)
    # ternary
    ternary = re.match(r'^(.+?)\s*\?\s*(.+?)\s*:\s*(.+)$', expr)
    if ternary:
        c = convert_expr(ternary.group(1))
        t = convert_expr(ternary.group(2))
        f = convert_expr(ternary.group(3))
        return f'if {c} then {t} else {f}'
    # negation
    expr = re.sub(r'!(\w+)\.isEmpty\(\)', r'|\1| > 0', expr)
    return expr


def _find_matching_brace(lines: List[str], start: int) -> int:
    """Find the index of the line with the matching closing brace.
    start should point to the line AFTER the opening brace."""
    depth = 1
    i = start
    while i < len(lines):
        stripped = lines[i].strip()
        # Count braces not inside strings/comments (simplified)
        for ch in stripped:
            if ch == '{': depth += 1
            elif ch == '}':
                depth -= 1
                if depth == 0:
                    return i
        i += 1
    return len(lines) - 1


def convert_body(lines: List[str], indent: str) -> List[str]:
    """Convert Java method body lines to Dafny."""
    result = []
    i = 0
    max_iterations = len(lines) * 3 + 100  # Safety limit
    iteration_count = 0
    
    while i < len(lines):
        iteration_count += 1
        if iteration_count > max_iterations:
            result.append(f"{indent}// ERROR: Conversion exceeded iteration limit")
            break
        
        line = lines[i]
        stripped = line.strip()

        # Skip empty
        if not stripped:
            i += 1
            continue

        # Skip pure JML ghost/set
        if stripped.startswith('//@ ghost') or stripped.startswith('//@ set'):
            i += 1
            continue

        # JML loop invariant -> Dafny comment for now (will be placed inside loop)
        if stripped.startswith('//@ loop_invariant') or stripped.startswith('//@ maintaining'):
            inv_text = re.sub(r'^//@\s*(?:loop_invariant|maintenance)\s*', '', stripped)
            result.append(f"{indent}// invariant {inv_text}")
            i += 1
            continue

        # Other JML -> skip
        if stripped.startswith('//@'):
            i += 1
            continue

        # Comments
        if stripped.startswith('//'):
            # assume -> keep as comment
            result.append(f"{indent}{stripped}")
            i += 1
            continue

        # ---- if / else if / else (chained) ----
        if_match = re.match(r'^if\s*\(', stripped)
        if if_match:
            first_pass = True
            while True:
                if first_pass:
                    first_pass = False
                    cond = convert_expr(_extract_paren_cond(stripped, 4) or '')
                    has_brace = '{' in stripped
                    result.append(f"{indent}if {cond} {{")
                    if has_brace:
                        body_lines, end_i = _collect_block(lines, i)
                    else:
                        next_stripped = lines[i+1].strip() if i+1 < len(lines) else ''
                        if next_stripped == '{':
                            body_lines, end_i = _collect_block(lines, i+1)
                        else:
                            body_lines = [lines[i+1]] if i+1 < len(lines) else []
                            end_i = i + 1
                    result.extend(convert_body(body_lines, indent + "    "))
                    result.append(f"{indent}}}")
                else:
                    # else-if
                    elif_m = re.match(r'^}?\s*else\s+if\s*\(', stripped)
                    paren_start = elif_m.end() if elif_m else -1
                    cond = convert_expr(_extract_paren_cond(stripped, paren_start) or '') if paren_start > 0 else ''
                    if result and result[-1].strip() == '}':
                        result.pop()
                    result.append(f"{indent}}} else if {cond} {{")
                    has_brace = '{' in stripped
                    if has_brace:
                        body_lines, end_i = _collect_block(lines, i)
                    else:
                        next_stripped = lines[i+1].strip() if i+1 < len(lines) else ''
                        if next_stripped == '{':
                            body_lines, end_i = _collect_block(lines, i+1)
                        else:
                            body_lines = [lines[i+1]] if i+1 < len(lines) else []
                            end_i = i + 1
                    result.extend(convert_body(body_lines, indent + "    "))
                    result.append(f"{indent}}}")
                # Check if closing brace line continues with else-if or else
                closing_line = lines[end_i].strip() if end_i < len(lines) else ''
                if_match = None
                elif_next = re.match(r'^}?\s*else\s+if\s*\(', closing_line)
                else_block = re.match(r'^}?\s*else\s*\{?\s*$', closing_line)
                if elif_next:
                    if_match = elif_next
                    i = end_i
                    stripped = closing_line
                    continue
                elif else_block:
                    # Handle else block
                    if result and result[-1].strip() == '}':
                        result.pop()
                    result.append(f"{indent}}} else {{")
                    has_brace = '{' in closing_line
                    if has_brace:
                        body_lines, end_i = _collect_block(lines, end_i)
                    else:
                        next_stripped = lines[end_i+1].strip() if end_i+1 < len(lines) else ''
                        if next_stripped == '{':
                            body_lines, end_i = _collect_block(lines, end_i+1)
                        else:
                            body_lines = [lines[end_i+1]] if end_i+1 < len(lines) else []
                            end_i = end_i + 1
                    result.extend(convert_body(body_lines, indent + "    "))
                    result.append(f"{indent}}}")
                    i = end_i + 1
                    break
                else:
                    i = end_i + 1
                    break
            continue

        # ---- for loop ----
        for_match = re.match(r'^for\s*\((.+?);\s*(.+?);\s*(.+?)\)\s*\{?\s*$', stripped)
        if for_match:
            init_s = for_match.group(1).strip()
            cond_s = for_match.group(2).strip()
            update_s = for_match.group(3).strip()

            init_m = re.match(r'(?:int\s+|var\s+)?(\w+)\s*=\s*(.+)', init_s)
            cond_m = re.match(r'(\w+)\s*([<>=!]+)\s*(.+)', cond_s)

            body_lines, end_i = _collect_block(lines, i)

            if init_m and cond_m:
                loop_var = init_m.group(1)
                init_val = convert_expr(init_m.group(2))
                cond_op = cond_m.group(2)
                bound = convert_expr(cond_m.group(3))

                if cond_op in ('<', '<='):
                    upper = bound if cond_op == '<=' else bound
                    to_expr = bound if cond_op == '<' else f"{bound} + 1"
                    result.append(f"{indent}for {loop_var} := {init_val} to {to_expr}")
                    result.append(f"{indent}    invariant {loop_var} >= {init_val}")
                    result.append(f"{indent}    invariant {loop_var} <= {to_expr}")
                    result.append(f"{indent}{{")
                    result.extend(convert_body(body_lines, indent + "    "))
                    result.append(f"{indent}}}")
                else:
                    # while loop fallback
                    result.append(f"{indent}var {loop_var} := {init_val};")
                    result.append(f"{indent}while {convert_expr(cond_s)}")
                    result.append(f"{indent}    invariant true")
                    result.append(f"{indent}{{")
                    result.extend(convert_body(body_lines, indent + "    "))
                    for s in _convert_simple_stmt(update_s, indent + "    "):
                        result.append(s)
                    result.append(f"{indent}}}")
            else:
                result.append(f"{indent}// for ({init_s}; {cond_s}; {update_s})")
                result.extend(convert_body(body_lines, indent + "    "))

            i = end_i + 1
            continue

        # ---- for-each loop ----
        foreach_match = re.match(r'^for\s*\((\w+)\s+(\w+)\s*:\s*(.+?)\)\s*\{?\s*$', stripped)
        if foreach_match:
            item_type = foreach_match.group(1)
            item_name = foreach_match.group(2)
            collection = convert_expr(foreach_match.group(3))
            body_lines, end_i = _collect_block(lines, i)
            result.append(f"{indent}for {item_name} in {collection}")
            result.append(f"{indent}{{")
            result.extend(convert_body(body_lines, indent + "    "))
            result.append(f"{indent}}}")
            i = end_i + 1
            continue

        # ---- while loop ----
        while_match = re.match(r'^while\s*\(', stripped)
        if while_match:
            cond = convert_expr(_extract_paren_cond(stripped, 7) or '')
            body_lines, end_i = _collect_block(lines, i)
            result.append(f"{indent}while {cond}")
            result.append(f"{indent}    invariant true")
            result.append(f"{indent}{{")
            result.extend(convert_body(body_lines, indent + "    "))
            result.append(f"{indent}}}")
            i = end_i + 1
            continue

        # ---- Closing brace alone ----
        if stripped == '}':
            i += 1
            continue

        # ---- break / continue ----
        if stripped in ('break;', 'break'):
            result.append(f"{indent}break;")
            i += 1
            continue
        if stripped in ('continue;', 'continue'):
            result.append(f"{indent}// continue")
            i += 1
            continue

        # ---- Regular statements ----
        for s in _convert_simple_stmt(stripped, indent):
            result.append(s)
        i += 1

    return result


def _collect_block(lines: List[str], start: int) -> Tuple[List[str], int]:
    """Collect lines inside a { ... } block. start points to the line containing
    the opening brace (which may also contain a closing brace, e.g. '} else if (...) {').
    Returns (body_lines, index_of_closing_brace_line)."""
    # Phase 1: find the line where we first enter the block (depth becomes > 0)
    i = start
    depth = 0
    while i < len(lines):
        line = lines[i]
        open_count = line.count('{')
        close_count = line.count('}')
        net = open_count - close_count
        has_open = open_count > 0
        depth += net
        if has_open and net > 0:
            # Opening brace dominates - we entered the block on this line
            break
        elif has_open and net <= 0:
            # Balanced braces (e.g. '} else if (...) {') or more closes.
            # The { opens a new block even though it is balanced by }.
            # Set depth to 1 to enter Phase 2 from the next line.
            depth = 1
            break
        elif depth <= 0 and not has_open:
            # No braces at all and not inside a block
            return [], i
        i += 1

    if depth <= 0:
        # Braces balanced on start line (e.g. '{ stmt; }') or no opening brace found
        content = lines[start] if start < len(lines) else ''
        brace_content = re.sub(r'^[^{]*\{', '', content)
        brace_content = re.sub(r'\}[^}]*$', '', brace_content).strip()
        if brace_content:
            return [brace_content], start
        return [], start

    # Phase 2: collect body lines until depth returns to 0
    init_depth = depth
    body_lines = []
    i += 1
    while i < len(lines):
        line = lines[i]
        d = line.count('{') - line.count('}')
        depth += d
        # Detect closing brace for else-if/else chain:
        # A '} else...' line with balanced braces (d = 0) brings depth back
        # to init_depth. This is the current block's closing brace.
        # Standalone '}' is NOT checked here (it would falsely match nested
        # block closers); it is caught by the depth <= 0 check below.
        if (depth <= init_depth and d == 0 and
                line.strip().startswith('}') and
                re.match(r'^}\s*else\b', line.strip())):
            return body_lines, i
        if depth <= 0:
            return body_lines, i
        body_lines.append(line)
        i += 1
    return body_lines, len(lines) - 1


def _convert_simple_stmt(stripped: str, indent: str) -> List[str]:
    """Convert a simple (non-block) Java statement."""
    result = []
    s = stripped.rstrip(';').strip()
    if not s:
        return []

    # ---- return ----
    if s == 'return':
        result.append(f"{indent}return;")
        return result
    m = re.match(r'^return\s+(.+)$', s)
    if m:
        val = convert_expr(m.group(1))
        result.append(f"{indent}result := {val};")
        result.append(f"{indent}return;")
        return result

    # ---- Increment/decrement (check before assignment) ----
    m = re.match(r'^(\w+)\+\+$', s)
    if m:
        result.append(f"{indent}{m.group(1)} := {m.group(1)} + 1;")
        return result
    m = re.match(r'^(\w+)--$', s)
    if m:
        result.append(f"{indent}{m.group(1)} := {m.group(1)} - 1;")
        return result
    m = re.match(r'^\+\+(\w+)$', s)
    if m:
        result.append(f"{indent}{m.group(1)} := {m.group(1)} + 1;")
        return result
    m = re.match(r'^--(\w+)$', s)
    if m:
        result.append(f"{indent}{m.group(1)} := {m.group(1)} - 1;")
        return result

    # Variable declaration with init: Type name = expr
    # Must NOT match keywords as type names
    JAVA_KEYWORDS = {'return', 'if', 'else', 'while', 'for', 'do', 'break', 'continue',
                     'true', 'false', 'null', 'new', 'this', 'super', 'throw', 'try',
                     'catch', 'finally', 'switch', 'case', 'default', 'assert'}
    m = re.match(r'^((?:final\s+)?[\w<>\[\]]+)\s+(\w+)\s*=\s*(.+)$', s)
    if m:
        vtype, vname, vvalue = m.group(1).strip(), m.group(2), m.group(3).strip()
        vtype_clean = vtype.replace('final ', '')
        # Ensure the type is not a keyword
        base_type = re.match(r'\w+', vtype_clean)
        if base_type and base_type.group(0) not in JAVA_KEYWORDS and vname not in JAVA_KEYWORDS:

            # new Type[size]
            arr_new = re.match(r'new\s+(\w+)\[([^\]]*)\]', vvalue)
            if arr_new:
                et = java_type_to_dafny(arr_new.group(1))
                sz = convert_expr(arr_new.group(2))
                result.append(f"{indent}var {vname} := new {et}[{sz}];")
                return result

            # new int[]{1,2,3}
            arr_init = re.match(r'new\s+\w+\[\]\s*\{(.+)\}', vvalue)
            if arr_init:
                vals = arr_init.group(1)
                result.append(f"{indent}var {vname} := [{convert_expr(vals)}];")
                return result

            # new Collection<>()
            coll_new = re.match(r'new\s+(\w+)<([^>]*)>\s*\(\)', vvalue)
            if coll_new:
                ct = coll_new.group(1)
                tp = coll_new.group(2).strip() if coll_new.group(2).strip() else None
                # Infer type from variable declaration if <> is empty
                if not tp:
                    decl_match = re.match(r'(?:List|ArrayList|Set|HashSet|Stack)<(.+?)>', vtype_clean)
                    if decl_match:
                        tp = decl_match.group(1)
                    else:
                        tp = 'int'
                if ct in ('ArrayList', 'List', 'LinkedList'):
                    result.append(f"{indent}var {vname}: seq<{java_type_to_dafny(tp)}> := [];")
                elif ct in ('HashSet', 'Set', 'TreeSet'):
                    result.append(f"{indent}var {vname}: set<{java_type_to_dafny(tp)}> := {{}};")
                elif ct in ('HashMap', 'Map', 'TreeMap'):
                    parts = tp.split(',')
                    if len(parts) == 2:
                        result.append(f"{indent}var {vname}: map<{java_type_to_dafny(parts[0].strip())}, {java_type_to_dafny(parts[1].strip())}> := map[];")
                elif ct == 'Stack':
                    result.append(f"{indent}var {vname}: seq<{java_type_to_dafny(tp)}> := [];")
                else:
                    result.append(f"{indent}var {vname} := {convert_expr(vvalue)};")
                return result

            dt = java_type_to_dafny(vtype_clean)
            result.append(f"{indent}var {vname}: {dt} := {convert_expr(vvalue)};")
            return result

    # Variable declaration without init
    m = re.match(r'^([\w<>\[\]]+)\s+(\w+)$', s)
    if m:
        base_type = re.match(r'\w+', m.group(1))
        if base_type and base_type.group(0) not in JAVA_KEYWORDS and m.group(2) not in JAVA_KEYWORDS:
            dt = java_type_to_dafny(m.group(1))
            result.append(f"{indent}var {m.group(2)}: {dt};")
            return result

    # Compound assignment: x += expr, x -= expr, etc.
    m = re.match(r'^(\w+(?:\[[^\]]+\])?)\s*([+\-*/%&|^])=\s*(.+)$', s)
    if m:
        target = m.group(1)
        op = m.group(2)
        val = convert_expr(m.group(3))
        # Handle array element compound assign
        result.append(f"{indent}{target} := {target} {op} {val};")
        return result

    # Array element assignment: arr[idx] = val
    m = re.match(r'^(\w+)\[([^\]]+)\]\s*=\s*(.+)$', s)
    if m:
        result.append(f"{indent}{m.group(1)}[{convert_expr(m.group(2))}] := {convert_expr(m.group(3))};")
        return result

    # Simple assignment: x = expr
    m = re.match(r'^(\w+)\s*=\s*(.+)$', s)
    if m:
        result.append(f"{indent}{m.group(1)} := {convert_expr(m.group(2))};")
        return result

    # Method call as statement: obj.method(args)
    m = re.match(r'^(\w+)\.(\w+)\((.*)?\)$', s)
    if m:
        obj, method, args = m.group(1), m.group(2), m.group(3) or ''
        args = args.strip()
        if method in ('add', 'push'):
            result.append(f"{indent}{obj} := {obj} + [{convert_expr(args)}];")
        elif method == 'put':
            kv = args.split(',', 1)
            if len(kv) == 2:
                result.append(f"{indent}{obj} := {obj}[{convert_expr(kv[0])} := {convert_expr(kv[1])}];")
            else:
                result.append(f"{indent}// {obj}.{method}({args})")
        elif method == 'pop':
            result.append(f"{indent}{obj} := {obj}[0..|{obj}|-1];")
        elif method == 'clear':
            result.append(f"{indent}{obj} := [];")
        elif method == 'sort':
            result.append(f"{indent}// {obj}.sort()")
        elif method == 'reverse':
            result.append(f"{indent}// reverse {obj}")
        else:
            result.append(f"{indent}// {obj}.{method}({args})")
        return result

    # Standalone expression (e.g. method call without obj)
    # Default: comment
    result.append(f"{indent}// {stripped}")
    return result


def convert_java_to_dafny(java_content: str) -> str:
    """Convert Java Solution file content to Dafny with complete translation."""
    lines = java_content.split('\n')
    output = []
    pending_annotations = []
    in_class = False
    i = 0

    while i < len(lines):
        line = lines[i]
        stripped = line.strip()

        # Skip package/import
        if stripped.startswith('package ') or stripped.startswith('import '):
            i += 1
            continue

        # Skip Java annotations (but keep //@ JML)
        if stripped.startswith('@') and not stripped.startswith('//@'):
            i += 1
            continue

        # Class declaration
        if re.match(r'public\s+class\s+\w+', stripped):
            in_class = True
            output.append("// Dafny version of Solution")
            output.append("")
            i += 1
            continue

        # JML annotation
        jml_match = re.match(r'^(\s*)//@\s*(requires|ensures|assert)\(\*(.+?)\*\);(.*)$', line)
        if jml_match:
            indent = jml_match.group(1)
            keyword = jml_match.group(2)
            nl_text = jml_match.group(3)
            suffix = jml_match.group(4)
            pending_annotations.append(f"{indent}// {keyword}(*{nl_text}*);{suffix}")
            i += 1
            continue

        # Method declaration
        method_match = re.match(
            r'^(\s*)public\s+(?:static\s+)?([\w<>\[\],\s]+?)\s+(\w+)\s*\(([^)]*)\)\s*\{?\s*$',
            line
        )
        if method_match and in_class:
            method_indent = method_match.group(1)
            return_type = method_match.group(2).strip()
            method_name = method_match.group(3)
            params_str = method_match.group(4)

            params = parse_parameters(params_str)
            dafny_params = []
            for ptype, pname in params:
                dafny_params.append(f"{pname}: {java_type_to_dafny(ptype)}")
            dafny_return = java_type_to_dafny(return_type)

            # Add annotations
            for ann in pending_annotations:
                output.append(ann)
            pending_annotations = []

            # Method signature
            if dafny_return == 'void':
                output.append(f"{method_indent}method {method_name}({', '.join(dafny_params)})")
            else:
                output.append(f"{method_indent}method {method_name}({', '.join(dafny_params)}) returns (result: {dafny_return})")

            # Collect method body
            body_lines, end_i = _collect_block(lines, i)
            output.append(f"{method_indent}{{")
            output.extend(convert_body(body_lines, method_indent + "    "))
            output.append(f"{method_indent}}}")
            output.append("")

            i = end_i + 1
            continue

        # Skip class closing brace
        if stripped == '}' and in_class:
            i += 1
            continue

        # Keep other comments
        if stripped.startswith('//') and not in_class:
            output.append(line)

        i += 1

    return '\n'.join(output)


def process_folder(folder_path: Path) -> bool:
    java_file = folder_path / 'Solution.java'
    dfy_file = folder_path / 'Solution.dfy'
    if not java_file.exists():
        return False
    try:
        with open(java_file, 'r', encoding='utf-8') as f:
            java_content = f.read()
        dafny_content = convert_java_to_dafny(java_content)
        with open(dfy_file, 'w', encoding='utf-8') as f:
            f.write(dafny_content)
        return True
    except Exception as e:
        print(f"Error processing {folder_path}: {e}", file=sys.stderr)
        import traceback; traceback.print_exc()
        return False


def main():
    patterns_dir = Path('test/patterns')
    if not patterns_dir.exists():
        print(f"Directory not found: {patterns_dir}", file=sys.stderr)
        sys.exit(1)
    success_count = 0
    error_count = 0
    for folder in sorted(patterns_dir.iterdir()):
        if folder.is_dir():
            sys.stdout.write(f"Processing {folder.name}... ")
            sys.stdout.flush()
            if process_folder(folder):
                print(f"[OK]")
                success_count += 1
            else:
                print(f"[FAIL]")
                error_count += 1
    print(f"\nProcessed {success_count} folders, {error_count} errors")


if __name__ == '__main__':
    main()
