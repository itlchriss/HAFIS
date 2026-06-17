#!/bin/bash
#
# instrument_dafny.sh - Instrument Dafny contracts into Dafny source files
#
# Usage: instrument_dafny.sh [OPTIONS] <dafny_file> [contract_files...]
#
# Options:
#   -o, --output <file>    Output file (default: <input>.instrumented.dfy)
#   -m, --method <name>    Target method name (required unless using -p)
#   -p, --position <line>  Insert at specific line number
#   -i, --in-place         Modify the file in place
#   -n, --dry-run          Show what would be done without making changes
#   -h, --help             Show this help message
#
# This script reads generated Dafny contracts and inserts them into
# the appropriate method declaration in a Dafny source file.
#

set -e

# ============================================================================
# Helper Functions
# ============================================================================
usage() {
    cat << EOF
Instrument Dafny contracts into Dafny source files

Usage: $(basename "$0") [OPTIONS] <dafny_file> [contract_files...]

Arguments:
    dafny_file          Path to the Dafny source file (.dfy)
    contract_files      One or more contract files to insert (.dfy)
                        If none specified, reads from stdin or uses -c

Options:
    -o, --output <file>    Output file (default: <input>.instrumented.dfy)
    -m, --method <name>    Target method name to insert contracts into
    -p, --position <line>  Insert at specific line number (1-based)
    -c, --contracts <str>  Contract strings (newline-separated)
    -i, --in-place         Modify the file in place
    -n, --dry-run          Show what would be done without making changes
    -h, --help             Show this help message

Examples:
    # Insert contracts into Sort method
    $(basename "$0") -m Sort test/example.dfy test/dafny/pre.0.dfy
    
    # Insert at specific line
    $(basename "$0") -p 5 test/example.dfy test/dafny/pre.0.dfy
    
    # Use inline contracts
    $(basename "$0") -m Sort -c "requires arr != null" test/example.dfy
    
    # Multiple contract files
    $(basename "$0") -m Sort test/example.dfy test/dafny/*.dfy
EOF
    exit 0
}

log_info() {
    echo "[INFO] $1"
}

log_warn() {
    echo "[WARN] $1" >&2
}

log_error() {
    echo "[ERROR] $1" >&2
}

# Find the line number where contracts should be inserted for a method
# Returns the line number just before the opening brace of the method
find_method_insert_position() {
    local file="$1"
    local method_name="$2"
    
    local line_num=0
    local found_method=false
    local in_signature=false
    
    while IFS= read -r line; do
        line_num=$((line_num + 1))
        
        # Look for method declaration
        if [[ "$line" =~ ^[[:space:]]*method[[:space:]]+${method_name}[[:space:]]*[\(\<] ]] || \
           [[ "$line" =~ ^[[:space:]]*method[[:space:]]+${method_name}[[:space:]]*$ ]]; then
            found_method=true
            in_signature=true
        fi
        
        if [[ "$found_method" == "true" && "$in_signature" == "true" ]]; then
            # Check if this line has the opening brace
            if [[ "$line" =~ \{[[:space:]]*$ ]] || [[ "$line" =~ ^[[:space:]]*\{ ]]; then
                # Insert before this line
                echo "$line_num"
                return 0
            fi
        fi
    done < "$file"
    
    return 1
}

# Find the indentation for contracts in a method (look for existing requires/ensures)
find_contract_indent() {
    local file="$1"
    local method_name="$2"
    
    local found_method=false
    local indent="    "  # default: 4 spaces
    
    while IFS= read -r line; do
        # Look for method declaration
        if [[ "$line" =~ ^[[:space:]]*method[[:space:]]+${method_name}[[:space:]]*[\(\<] ]] || \
           [[ "$line" =~ ^[[:space:]]*method[[:space:]]+${method_name}[[:space:]]*$ ]]; then
            found_method=true
            continue
        fi
        
        if [[ "$found_method" == "true" ]]; then
            # Check if this line has requires or ensures
            if [[ "$line" =~ ^([[:space:]]*)(requires|ensures) ]]; then
                indent="${BASH_REMATCH[1]}"
                echo "$indent"
                return 0
            fi
            # If we hit the opening brace without finding any contract, use default
            if [[ "$line" =~ \{ ]]; then
                echo "$indent"
                return 0
            fi
        fi
    done < "$file"
    
    echo "$indent"
}

# Read contracts from files
read_contracts_from_files() {
    local contracts=""
    for file in "$@"; do
        if [[ ! -f "$file" ]]; then
            log_warn "Contract file not found: $file"
            continue
        fi
        while IFS= read -r line; do
            # Skip empty lines and comments
            [[ -z "$line" || "$line" =~ ^[[:space:]]*// ]] && continue
            contracts+="$line"$'\n'
        done < "$file"
    done
    echo -n "$contracts"
}

# Read contracts from string
read_contracts_from_string() {
    local str="$1"
    echo -n "$str"$'\n'
}

# ============================================================================
# Argument Parsing
# ============================================================================
DRY_RUN=false
IN_PLACE=false
OUTPUT_FILE=""
TARGET_METHOD=""
POSITION=""
CONTRACTS_STRING=""
CONTRACT_FILES=()

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
        -m|--method)
            if [[ -z "$2" || "$2" == -* ]]; then
                log_error "Method option requires a name argument"
                exit 1
            fi
            TARGET_METHOD="$2"
            shift 2
            ;;
        -p|--position)
            if [[ -z "$2" || "$2" == -* ]]; then
                log_error "Position option requires a line number argument"
                exit 1
            fi
            POSITION="$2"
            shift 2
            ;;
        -c|--contracts)
            if [[ -z "$2" ]]; then
                log_error "Contracts option requires a string argument"
                exit 1
            fi
            CONTRACTS_STRING="$2"
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
                CONTRACT_FILES+=("$1")
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

if [[ -z "$TARGET_METHOD" && -z "$POSITION" ]]; then
    log_error "Must specify either --method or --position"
    exit 1
fi

# Determine insert position and indentation
INDENT="    "  # default
if [[ -n "$TARGET_METHOD" ]]; then
    POSITION=$(find_method_insert_position "$DAFNY_FILE" "$TARGET_METHOD") || {
        log_error "Method '$TARGET_METHOD' not found in $DAFNY_FILE"
        exit 1
    }
    INDENT=$(find_contract_indent "$DAFNY_FILE" "$TARGET_METHOD")
    log_info "Found method '$TARGET_METHOD' at line $POSITION (indent: '${INDENT}')"
fi

# Read contracts
CONTRACTS=""
if [[ -n "$CONTRACTS_STRING" ]]; then
    CONTRACTS=$(read_contracts_from_string "$CONTRACTS_STRING")
elif [[ ${#CONTRACT_FILES[@]} -gt 0 ]]; then
    CONTRACTS=$(read_contracts_from_files "${CONTRACT_FILES[@]}")
else
    # Read from stdin
    CONTRACTS=$(cat)
fi

if [[ -z "$CONTRACTS" ]]; then
    log_error "No contracts to insert"
    exit 1
fi

# Set output file
if [[ "$IN_PLACE" == "true" ]]; then
    OUTPUT_FILE="$DAFNY_FILE"
elif [[ -z "$OUTPUT_FILE" ]]; then
    base="${DAFNY_FILE%.dfy}"
    OUTPUT_FILE="${base}.instrumented.dfy"
fi

# ============================================================================
# Main Processing
# ============================================================================
log_info "Dafny file: $DAFNY_FILE"
log_info "Insert position: line $POSITION"
log_info "Output file: $OUTPUT_FILE"
[[ "$DRY_RUN" == "true" ]] && log_info "DRY RUN MODE"

log_info "Contracts to insert:"
echo "$CONTRACTS" | while IFS= read -r line; do
    [[ -n "$line" ]] && echo "  $line"
done

if [[ "$DRY_RUN" == "true" ]]; then
    log_info "Dry run complete - no changes made"
    exit 0
fi

# Create the output file
line_num=0
{
    while IFS= read -r line || [[ -n "$line" ]]; do
        line_num=$((line_num + 1))
        
        # Insert contracts before the specified position
        if [[ $line_num -eq $POSITION ]]; then
            # Output the contracts with proper indentation
            echo "$CONTRACTS" | while IFS= read -r contract; do
                if [[ -n "$contract" ]]; then
                    # Strip any existing leading whitespace from contract
                    contract_trimmed="${contract#"${contract%%[![:space:]]*}"}"
                    echo "${INDENT}${contract_trimmed}"
                fi
            done
        fi
        
        echo "$line"
    done < "$DAFNY_FILE"
} > "${OUTPUT_FILE}.tmp"

# Move temp file to output
mv "${OUTPUT_FILE}.tmp" "$OUTPUT_FILE"

log_info "Output written to: $OUTPUT_FILE"
