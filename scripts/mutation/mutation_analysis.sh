#!/bin/bash
OPENJML="../openjml/openjml "
ESC_FLAG="-esc"
BIGINT_FLAGS="--spec-math bigint --code-math bigint"
TIMEOUT_FLAG="--timeout 180"
SRC_PATH=./test/mutation_analysis
methods=("hafis" "purellm")
models=("gpt35" "gpt4" "starchat")

# this gets the max number of processes for the user
# max_num_processes=$(ulimit -u)
# in case I want to run something else
# limiting_factor=50
# num_processes=$((max_num_processes/limiting_factor))
# commands=()
# num_processes=10
# while IFS= read -r entry; do
#     # printf "case: %s....\n" $SRC_PATH/$entry
#     for method in ${methods[@]}
#     do
#         for model in ${models[@]}
#         do
#             path=$SRC_PATH/$entry/$method/$model
#             # printf "do test on path: %s...\n" $path
#             # commands+=("$OPENJML $ESC_FLAG $path/Solution.java $BIGINT_FLAGS $TIMEOUT_FLAG > $path/org_result.txt")
#             commands+=("${OPENJML} ${ESC_FLAG} ${path}/Solution.java ${BIGINT_FLAGS} ${TIMEOUT_FLAG} > ${path}/org_result.txt")
#             list=`find $path/mutants/* -maxdepth 1 -type f -name 'Solution.java'`
#             for mutant in $list
#             do
#                 # ((i=i%num_processes)); ((i++==0)) && wait
#                 folder=$(dirname "$mutant")
#                 # commands+=("$OPENJML $ESC_FLAG $mutant  $BIGINT_FLAGS $TIMEOUT_FLAG > $folder/result.txt")
#                 commands+=("${OPENJML} ${ESC_FLAG} ${mutant}  ${BIGINT_FLAGS} ${TIMEOUT_FLAG} > ${folder}/result.txt")
#             done
#         done
#     done
# done < "./test/cases"

# echo '' > ./test/fmcheck_cmds
# for cmd in "${commands[@]}"
# do
#     # ((i=i%num_processes)); ((i++==0)) && wait
#     echo $cmd >> ./test/fmcheck_cmds
# done

# for cmd in "${commands[@]}"
# do
#     echo $cmd
#     eval "${cmd}"
# done

while IFS= read -r line; do
    echo "$line"
    eval "${line}"
done < $1