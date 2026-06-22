# Type-Param Predicate Merge Plan

## Background
Currently, "the integer array parameter `numbers`" produces two predicates:
- `_type_integer_array_{NN}(x01)` 
- `_param_numbers_{NN}(x01)`

This causes alias resolution issues in the compiler. The goal is to produce a single predicate:
- `_type_integer_array_param_numbers_{NN}(x01)`

## Task 1: Update Python Preprocessing (contextprocess.py)
Modify `_parameter_syntax_processor` to prepend the preceding type token to the param_ token:
- Find patterns like `_type_X_ param_Y_` and merge to `_type_X_param_Y_`
- Location: `/Users/chrissleong/Documents/Phd_Studies/HAFIS/src/python/preprocess/contextprocess.py` lines 331-385

```python
# After existing param_ processing, merge type and param
# Pattern: _type_X_ param_Y_ -> _type_X_param_Y_
sent = re.sub(r'_type_([a-z_]+)_\s+param_([a-zA-Z0-9]+)_', r'_type_\1_param_\2_', sent)
```

## Task 2: Update Lexer (lex.l)
Add a new token pattern for combined type_param tokens:
- Location: `/Users/chrissleong/Documents/Phd_Studies/HAFIS/src/c.2024/lex.l`
- Add pattern: `KEYWORD_TYPED_PARAM _type[_a-z]+_param[_a-zA-Z0-9]+_`
- Return a new token type `KEYWORD_TYPED_PARAM`

## Task 3: Update Parser (parser.y)
Add a new rule for typed_param_term or modify param_term to handle the combined token:
- Location: `/Users/chrissleong/Documents/Phd_Studies/HAFIS/src/c.2024/parser.y` lines 384-406
- The parser should extract both type and param name from the combined token
- Generate SI with correct primitive_type and reference_type from the type portion

## Task 4: Update SI Generation (si_runtime.c)
Modify `generate_param_si` or create a new function `generate_typed_param_si`:
- Location: `/Users/chrissleong/Documents/Phd_Studies/HAFIS/src/c.2024/si_runtime.c`
- Parse the combined token to extract type info (e.g., "integer_array" from "_type_integer_array_param_numbers_")
- Set correct primitive_type and reference_type in the SI entry

## Task 5: Update Dynamic SI Generation (si_building.py)
Modify how dynamic SI entries are generated for typed params:
- Location: `/Users/chrissleong/Documents/Phd_Studies/HAFIS/src/python/preprocess/steps/si_building.py` lines 223-244
- Parse the combined token to extract type and param name
- Generate SI with correct synthesised_datatype

## Task 6: Update MR Repair (mr_arithmetic_repair.py)
Update repair functions that reference param_ or type_ predicates:
- Location: `/Users/chrissleong/Documents/Phd_Studies/HAFIS/src/python/preprocess/mr_arithmetic_repair.py`
- `repair_type_predicate_jj` - may need to handle combined tokens
- `repair_param_si` - update patterns for combined tokens

## Task 7: Update Alt Rules and Expression Extraction
- Update alt.yml rules that reference `type_` or `param_` patterns
- Update expression_extraction.yml if needed
- Locations: 
  - `/Users/chrissleong/Documents/Phd_Studies/HAFIS/rules/alt.yml`
  - `/Users/chrissleong/Documents/Phd_Studies/HAFIS/rules/expression_extraction.yml`

## Task 8: Rebuild and Test
- Rebuild the C compiler: `make` or appropriate build command
- Run the mearc pipeline on qwen3.7/s0001_two_sum/verify_nl.dfy
- Verify that ann.11-13 (and other cases) work correctly

## Key Files to Modify
1. `src/python/preprocess/contextprocess.py` - Python preprocessing
2. `src/c.2024/lex.l` - Lexer patterns
3. `src/c.2024/parser.y` - Parser rules
4. `src/c.2024/si_runtime.c` - Runtime SI generation
5. `src/python/preprocess/steps/si_building.py` - Dynamic SI generation
6. `src/python/preprocess/mr_arithmetic_repair.py` - MR repair
7. `rules/alt.yml` - Alt rules (if needed)

## Expected Outcome
- Single predicate `_type_integer_array_param_numbers_{NN}(x01)` instead of two
- No alias resolution issues since there's only one predicate
- ann.11-13 should compile successfully