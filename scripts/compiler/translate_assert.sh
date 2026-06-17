#!/bin/bash
#
# translate_assert.sh - Translate natural language assertions in Dafny code
#
# Usage: translate_assert.sh [OPTIONS] <dafny_file>
#
# This script processes special assert comments in Dafny files:
#   // assert(*The value of n should be equal to y*);
#
# The natural language between * markers is translated to Dafny syntax
# using the HAFIS NLP pipeline.
#
# Options:
#   -o, --output <file>    Output file (default: <input>.translated.dfy)
#   -i, --in-place         Modify the file in place
#   -n, --dry-run          Show what would be done without making changes
#   -v, --verbose          Show detailed translation process
#   -s, --simple           Use only simple pattern matching (no NLP pipeline)
#   -h, --help             Show this help message
#

# Exit on error disabled for better error handling
set +e

# ============================================================================
# Configuration
# ============================================================================
ROOT=./
NLP=${NLP_PATH:-../NLP/ccg2lambda}
STD_SI=./specs/si/typed_si.yml,./specs/si/java_datatypes.yml
PYCMD=${PYTHON:-python3}
TMP_DIR=$(mktemp -d)

# Check if NLP tools are available
check_nlp_tools() {
    if [[ ! -d "$NLP" ]]; then
        log_error "NLP tools not found at: $NLP"
        log_error "Set NLP_PATH environment variable to the ccg2lambda directory"
        return 1
    fi
    if ! command -v "$PYCMD" &> /dev/null; then
        log_error "Python not found. Set PYTHON environment variable (e.g., PYTHON=python3)"
        return 1
    fi
    return 0
}

# Cleanup on exit
cleanup() {
    rm -rf "$TMP_DIR"
}
trap cleanup EXIT

# ============================================================================
# Helper Functions
# ============================================================================
usage() {
    cat << EOF
Translate natural language assertions in Dafny code

Usage: $(basename "$0") [OPTIONS] <dafny_file>

Special Assert Syntax:
    // assert(*natural language description*);
    
    The text between * markers is translated to Dafny assert syntax.

Options:
    -o, --output <file>    Output file (default: <input>.translated.dfy)
    -i, --in-place         Modify the file in place
    -n, --dry-run          Show what would be done without making changes
    -v, --verbose          Show detailed translation process
    -s, --simple           Use only simple pattern matching (no NLP pipeline)
    -h, --help             Show this help message

Examples:
    # Translate assertions in a Dafny file
    $(basename "$0") test/example.dfy
    
    # In-place modification
    $(basename "$0") -i test/example.dfy
    
    # Preview changes
    $(basename "$0") -n test/example.dfy

Input Example:
    method Foo(n: int, y: int)
        requires n >= 0
    {
        // assert(*The value of n should be equal to y*);
        var x := n;
    }

Output Example:
    method Foo(n: int, y: int)
        requires n >= 0
    {
        assert n == y;
        var x := n;
    }
EOF
    exit 0
}

log_info() {
    echo "[INFO] $1"
}

log_verbose() {
    [[ "$VERBOSE" == "true" ]] && echo "[DEBUG] $1"
}

log_error() {
    echo "[ERROR] $1" >&2
}

# Extract natural language text from assert comment
# Pattern: // assert(*NL text*);
extract_nl_text() {
    local line="$1"
    # Match: // assert(*...*);
    if [[ "$line" =~ //[[:space:]]*assert\(\*([^*]+)\*\)[[:space:]]*\; ]]; then
        echo "${BASH_REMATCH[1]}"
        return 0
    fi
    return 1
}

# Simple pattern-based translation fallback for basic assertions
# This handles common patterns without requiring the full NLP pipeline
simple_translate() {
    local nl_text="$1"
    local result=""
    
    # Normalize text (lowercase, trim)
    local text=$(echo "$nl_text" | tr '[:upper:]' '[:lower:]' | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')
    
    # Pattern: X is not null / X should not be null
    if [[ "$text" =~ ^([a-zA-Z_][a-zA-Z0-9_]*)[[:space:]]+(is|should be)[[:space:]]+not[[:space:]]+null ]]; then
        result="assert ${BASH_REMATCH[1]} != null;"
    # Pattern: X is null / X should be null  
    elif [[ "$text" =~ ^([a-zA-Z_][a-zA-Z0-9_]*)[[:space:]]+(is|should be)[[:space:]]+null ]]; then
        result="assert ${BASH_REMATCH[1]} == null;"
    # Pattern: the value of X is equal to Y / the value of X should be equal to Y
    elif [[ "$text" =~ ^the[[:space:]]+value[[:space:]]+of[[:space:]]+([a-zA-Z_][a-zA-Z0-9_]*)[[:space:]]+(is|should be)[[:space:]]+equal[[:space:]]+to[[:space:]]+([a-zA-Z_][a-zA-Z0-9_]*) ]]; then
        result="assert ${BASH_REMATCH[1]} == ${BASH_REMATCH[3]};"
    # Pattern: X is equal to Y / X should be equal to Y
    elif [[ "$text" =~ ^([a-zA-Z_][a-zA-Z0-9_]*)[[:space:]]+(is|should be)[[:space:]]+equal[[:space:]]+to[[:space:]]+([a-zA-Z_][a-zA-Z0-9_]*) ]]; then
        result="assert ${BASH_REMATCH[1]} == ${BASH_REMATCH[3]};"
    # Pattern: X is greater than or equal to Y (must come before simple "greater than")
    elif [[ "$text" =~ ^([a-zA-Z_][a-zA-Z0-9_]*)[[:space:]]+(is|should be)[[:space:]]+greater[[:space:]]+than[[:space:]]+or[[:space:]]+equal[[:space:]]+to[[:space:]]+([a-zA-Z0-9_]+) ]]; then
        result="assert ${BASH_REMATCH[1]} >= ${BASH_REMATCH[3]};"
    # Pattern: X is less than or equal to Y (must come before simple "less than")
    elif [[ "$text" =~ ^([a-zA-Z_][a-zA-Z0-9_]*)[[:space:]]+(is|should be)[[:space:]]+less[[:space:]]+than[[:space:]]+or[[:space:]]+equal[[:space:]]+to[[:space:]]+([a-zA-Z0-9_]+) ]]; then
        result="assert ${BASH_REMATCH[1]} <= ${BASH_REMATCH[3]};"
    # Pattern: X is greater than Y / X should be greater than Y
    elif [[ "$text" =~ ^([a-zA-Z_][a-zA-Z0-9_]*)[[:space:]]+(is|should be)[[:space:]]+greater[[:space:]]+than[[:space:]]+([a-zA-Z0-9_]+) ]]; then
        result="assert ${BASH_REMATCH[1]} > ${BASH_REMATCH[3]};"
    # Pattern: X is less than Y / X should be less than Y
    elif [[ "$text" =~ ^([a-zA-Z_][a-zA-Z0-9_]*)[[:space:]]+(is|should be)[[:space:]]+less[[:space:]]+than[[:space:]]+([a-zA-Z0-9_]+) ]]; then
        result="assert ${BASH_REMATCH[1]} < ${BASH_REMATCH[3]};"
    # Pattern: X is positive / X should be positive
    elif [[ "$text" =~ ^([a-zA-Z_][a-zA-Z0-9_]*)[[:space:]]+(is|should be)[[:space:]]+positive ]]; then
        result="assert ${BASH_REMATCH[1]} > 0;"
    # Pattern: X is negative / X should be negative
    elif [[ "$text" =~ ^([a-zA-Z_][a-zA-Z0-9_]*)[[:space:]]+(is|should be)[[:space:]]+negative ]]; then
        result="assert ${BASH_REMATCH[1]} < 0;"
    fi
    
    echo "$result"
}

# Translate NL text to Dafny assertion using HAFIS pipeline
translate_to_dafny() {
    local nl_text="$1"
    local index="$2"
    
    log_verbose "Translating: '$nl_text'"
    
    # Step 1: Tokenize
    printf '%s\n' "$nl_text" | sed -f "$NLP/en/tokenizer.sed" > "$TMP_DIR/assert.$index.tok"
    log_verbose "Tokenized: $(cat "$TMP_DIR/assert.$index.tok")"
    
    # Step 2: C&C parsing
    $NLP/candc-1.00/bin/candc --models "$NLP/candc-1.00/models" \
        --candc-printer xml \
        --candc-maxwords 2048 \
        --input "$TMP_DIR/assert.$index.tok" > "$TMP_DIR/assert.$index.candc.xml" 2>/dev/null
    log_verbose "C&C output generated"
    
    # Step 3: Convert to CCG
    $PYCMD "$NLP/en/candc2transccg_wsc.py" "$TMP_DIR/assert.$index.candc.xml" > "$TMP_DIR/assert.$index.ccg.xml"
    log_verbose "CCG output generated"
    
    # Step 4: Semantic parsing to MR
    $PYCMD "$NLP/scripts/semparse_wsc.py" "$TMP_DIR/assert.$index.ccg.xml" \
        "$NLP/en/semantic_templates_en_event_flat_mearc.yaml" > "$TMP_DIR/assert.$index.mr"
    log_verbose "MR: $(cat "$TMP_DIR/assert.$index.mr")"
    
    # Step 5: Normalize MR
    $PYCMD ./src/python/depccg/nltk2normal.py "$TMP_DIR/assert.$index.mr" 2>/dev/null
    log_verbose "MR normalized"
    
    # Step 6: Check if MR has quantifiers (exists/all)
    if grep -qE 'exists|all' "$TMP_DIR/assert.$index.mr"; then
        # Compile through HAFIS
        local result
        result=$(./bin/main -f "$TMP_DIR/assert.$index.mr" -s "$STD_SI" -b dafny 2>&1)
        
        if [[ "$result" == *"error"* || "$result" == *"Lexer:"* || "$result" == *"Parser:"* ]]; then
            log_error "Translation failed for: '$nl_text'"
            log_error "Compiler output: $result"
            return 1
        fi
        
        # Parse the output
        IFS=$'\n' read -rd '' -a lines <<< "$result" || true
        local line1="${lines[0]}"
        
        if [[ "$line1" =~ pcount:([0-9]+) ]]; then
            local pcount="${BASH_REMATCH[1]}"
            local asserts=""
            
            for i in $(seq 1 "$pcount"); do
                local body="${lines[$i]}"
                if [[ -n "$body" && "$body" != "()" ]]; then
                    asserts+="assert ${body};"$'\n'
                fi
            done
            
            if [[ -n "$asserts" ]]; then
                echo -n "$asserts"
                return 0
            fi
        fi
    else
        # Simple expression without quantifiers - use directly
        local mr_content
        mr_content=$(cat "$TMP_DIR/assert.$index.mr" | tr -d '\n')
        
        # Try to compile anyway for consistency
        local result
        result=$(./bin/main -f "$TMP_DIR/assert.$index.mr" -s "$STD_SI" -b dafny 2>&1)
        
        if [[ "$result" == *"pcount:"* ]]; then
            IFS=$'\n' read -rd '' -a lines <<< "$result" || true
            local line1="${lines[0]}"
            
            if [[ "$line1" =~ pcount:([0-9]+) ]]; then
                local pcount="${BASH_REMATCH[1]}"
                local asserts=""
                
                for i in $(seq 1 "$pcount"); do
                    local body="${lines[$i]}"
                    if [[ -n "$body" && "$body" != "()" ]]; then
                        asserts+="assert ${body};"$'\n'
                    fi
                done
                
                if [[ -n "$asserts" ]]; then
                    echo -n "$asserts"
                    return 0
                fi
            fi
        fi
    fi
    
    log_error "No valid assertion generated for: '$nl_text'"
    
    # Try simple pattern-based translation as fallback
    local simple_result
    simple_result=$(simple_translate "$nl_text")
    if [[ -n "$simple_result" ]]; then
        log_info "Using pattern-based fallback: '$simple_result'"
        echo -n "$simple_result"
        return 0
    fi
    
    return 1
}

# ============================================================================
# Argument Parsing
# ============================================================================
DRY_RUN=false
IN_PLACE=false
VERBOSE=false
SIMPLE_MODE=false
OUTPUT_FILE=""

while [[ $# -gt 0 ]]; do
    case "$1" in
        -o|--output)
            if [[ -z "$2" || "$2" == -* ]]; then
                log_error "Output option requires a file argument"
                exit 1
            fi
            OUTPUT_FILE="$2"
            shift 2
            ;;
        -i|--in-place)
            IN_PLACE=true
            shift
            ;;
        -n|--dry-run)
            DRY_RUN=true
            shift
            ;;
        -v|--verbose)
            VERBOSE=true
            shift
            ;;
        -s|--simple)
            SIMPLE_MODE=true
            shift
            ;;
        -h|--help)
            usage
            ;;
        -*)
            log_error "Unknown option: $1"
            usage
            ;;
        *)
            if [[ -z "$DAFNY_FILE" ]]; then
                DAFNY_FILE="$1"
            else
                log_error "Too many arguments"
                usage
            fi
            shift
            ;;
    esac
done

# Validate arguments
if [[ -z "$DAFNY_FILE" ]]; then
    log_error "No Dafny file specified"
    usage
fi

if [[ ! -f "$DAFNY_FILE" ]]; then
    log_error "Dafny file not found: $DAFNY_FILE"
    exit 1
fi

# Set output file
if [[ "$IN_PLACE" == "true" ]]; then
    OUTPUT_FILE="$DAFNY_FILE"
elif [[ -z "$OUTPUT_FILE" ]]; then
    base="${DAFNY_FILE%.dfy}"
    OUTPUT_FILE="${base}.translated.dfy"
fi

# ============================================================================
# Main Processing
# ============================================================================
log_info "Processing: $DAFNY_FILE"
log_info "Output: $OUTPUT_FILE"
[[ "$DRY_RUN" == "true" ]] && log_info "DRY RUN MODE"

# Check NLP tools availability (skip for dry-run or simple mode)
if [[ "$DRY_RUN" == "false" && "$SIMPLE_MODE" == "false" ]]; then
    if ! check_nlp_tools; then
        log_info "Falling back to simple pattern matching"
        SIMPLE_MODE=true
    fi
fi

[[ "$SIMPLE_MODE" == "true" ]] && log_info "Using simple pattern matching mode"

# First pass: find all assert comments
declare -a ASSERT_LINES
declare -a ASSERT_TEXTS
line_num=0

while IFS= read -r line; do
    line_num=$((line_num + 1))
    
    if nl_text=$(extract_nl_text "$line"); then
        ASSERT_LINES+=("$line_num")
        ASSERT_TEXTS+=("$nl_text")
        log_verbose "Found assert at line $line_num: '$nl_text'"
    fi
done < "$DAFNY_FILE"

if [[ ${#ASSERT_LINES[@]} -eq 0 ]]; then
    log_info "No natural language assertions found"
    if [[ "$DRY_RUN" == "false" ]]; then
        cp "$DAFNY_FILE" "$OUTPUT_FILE"
    fi
    exit 0
fi

log_info "Found ${#ASSERT_LINES[@]} assertion(s) to translate"

# Second pass: translate each assertion
declare -A TRANSLATED_ASSERTS

for i in "${!ASSERT_LINES[@]}"; do
    line_num="${ASSERT_LINES[$i]}"
    nl_text="${ASSERT_TEXTS[$i]}"
    
    log_info "Translating assertion at line $line_num..."
    
    translated=""
    if [[ "$SIMPLE_MODE" == "true" ]]; then
        # Use simple pattern matching
        translated=$(simple_translate "$nl_text")
        if [[ -n "$translated" ]]; then
            TRANSLATED_ASSERTS["$line_num"]="$translated"
            log_info "  -> $translated"
        else
            log_error "  No pattern match for: '$nl_text'"
            TRANSLATED_ASSERTS["$line_num"]="__FAILED__"
        fi
    else
        # Use full NLP pipeline
        if translated=$(translate_to_dafny "$nl_text" "$i"); then
            TRANSLATED_ASSERTS["$line_num"]="$translated"
            log_info "  -> $(echo "$translated" | head -1)"
        else
            log_error "  Failed to translate, keeping original comment"
            TRANSLATED_ASSERTS["$line_num"]="__FAILED__"
        fi
    fi
done

# Third pass: generate output file
if [[ "$DRY_RUN" == "false" ]]; then
    > "${OUTPUT_FILE}.tmp"
fi

line_num=0
while IFS= read -r line; do
    line_num=$((line_num + 1))
    
    if [[ -n "${TRANSLATED_ASSERTS[$line_num]+x}" ]]; then
        translated="${TRANSLATED_ASSERTS[$line_num]}"
        
        if [[ "$translated" == "__FAILED__" ]]; then
            # Keep original line
            if [[ "$DRY_RUN" == "false" ]]; then
                echo "$line" >> "${OUTPUT_FILE}.tmp"
            fi
        else
            # Extract indentation from original line
            indent=""
            if [[ "$line" =~ ^([[:space:]]*) ]]; then
                indent="${BASH_REMATCH[1]}"
            fi
            
            # Output translated assertions with proper indentation
            echo "$translated" | while IFS= read -r assert_line; do
                if [[ -n "$assert_line" ]]; then
                    if [[ "$DRY_RUN" == "true" ]]; then
                        log_info "Would insert at line $line_num: ${indent}${assert_line}"
                    else
                        echo "${indent}${assert_line}" >> "${OUTPUT_FILE}.tmp"
                    fi
                fi
            done
        fi
    else
        # Copy original line
        if [[ "$DRY_RUN" == "false" ]]; then
            echo "$line" >> "${OUTPUT_FILE}.tmp"
        fi
    fi
done < "$DAFNY_FILE"

# Finalize output
if [[ "$DRY_RUN" == "false" ]]; then
    mv "${OUTPUT_FILE}.tmp" "$OUTPUT_FILE"
    log_info "Output written to: $OUTPUT_FILE"
else
    log_info "Dry run complete - no changes made"
fi
