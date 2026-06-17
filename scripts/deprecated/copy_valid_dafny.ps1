# Script to copy valid Dafny files (imperative programs without Java-specific APIs)

$sourceDir = "e:\Post_Phd_work\Software\HAFIS\test\patterns"
$targetDir = "e:\Post_Phd_work\Software\HAFIS\test\filtered_dafny"

# Files to EXCLUDE - Java-specific APIs without Dafny equivalents
$excludeFolders = @(
    # StringBuilder (Java API, no Dafny equivalent)
    "s0006_zigzag_conversion",
    "s0151_reverse_words_in_a_string",
    "s0405_convert_a_number_to_hexadecimal",
    "s0415_add_strings",
    "s0451_sort_characters_by_frequency",
    "s1021_remove_outermost_parentheses",
    
    # Character class methods (Java API)
    "s0227_basic_calculator_ii",      # Character.isDigit
    "s0299_bulls_and_cows",           # Character.getNumericValue
    "s0520_detect_capital",           # Character.isUpperCase, Character.isLowerCase
    
    # String methods not in Dafny
    "s0387_first_unique_character_in_a_string",  # indexOf, lastIndexOf
    "s0709_to_lower_case",            # toCharArray, new String
    
    # Integer class methods (Java API)
    "s0461_hamming_distance",         # Integer.bitCount
    "s0476_number_complement",        # Integer.highestOneBit
    
    # Math class methods (Java API)
    "s0319_bulb_switcher",            # Math.sqrt
    
    # Map methods not translated to Dafny
    "s0454_4sum_ii",                  # map.getOrDefault
    
    # Non-imperative (recursive/functional programs)
    "s0390_elimination_game",         # purely recursive
    "s0165_compare_version_numbers"   # recursive call
)

# Create target directory if it doesn't exist
if (-not (Test-Path $targetDir)) {
    New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
    Write-Host "Created target directory: $targetDir"
}

# Get all subdirectories in source
$folders = Get-ChildItem -Path $sourceDir -Directory

$copiedCount = 0
$skippedCount = 0

foreach ($folder in $folders) {
    $folderName = $folder.Name
    
    if ($excludeFolders -contains $folderName) {
        Write-Host "SKIPPING: $folderName" -ForegroundColor Yellow
        $skippedCount++
    } else {
        # Create target folder
        $targetFolder = Join-Path $targetDir $folderName
        if (-not (Test-Path $targetFolder)) {
            New-Item -ItemType Directory -Path $targetFolder -Force | Out-Null
        }
        
        # Copy Solution.dfy file
        $sourceFile = Join-Path $folder.FullName "Solution.dfy"
        if (Test-Path $sourceFile) {
            Copy-Item $sourceFile $targetFolder -Force
            Write-Host "COPIED: $folderName/Solution.dfy" -ForegroundColor Green
            $copiedCount++
        } else {
            Write-Host "WARNING: No Solution.dfy in $folderName" -ForegroundColor Red
        }
    }
}

Write-Host ""
Write-Host "============================================"
Write-Host "Summary:"
Write-Host "  Copied: $copiedCount files"
Write-Host "  Skipped: $skippedCount files"
Write-Host "============================================"
