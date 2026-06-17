#!/bin/bash

SRC_PATH=./test/patterns
SYNTAXCHECK_PATH=./test/syntax_check
MUTATECHECK_PATH=./test/syntax_checked
OPENJML=../openjml/openjml
models=("gpt35" "gpt4" "starchat")

# for entry in `find $SRC_PATH -type f -name "Solution.java.no_annotation" | sort`
# do
#     printf "%s....\n" $entry
#     folder=""$(dirname $entry)""
#     base=`basename $folder`
#     mkdir -p $SYNTAXCHECK_PATH/$base
#     # mkdir -p $SYNTAXCHECK_PATH/$base/hafis
#     mkdir -p $SYNTAXCHECK_PATH/$base/purellm
#     # cp $folder/Solution.java.no_annotation $SYNTAXCHECK_PATH/$base/Solution.java.no_annotation
#     for model in "${models[@]}"
#     do
#         # mkdir -p $SYNTAXCHECK_PATH/$base/hafis/$model
#         mkdir -p $SYNTAXCHECK_PATH/$base/purellm/$model
#         # cp -r $folder/hafis/$model/jml $SYNTAXCHECK_PATH/$base/hafis/$model
#         # cp -r $folder/llm-results/$model/jml.txt $SYNTAXCHECK_PATH/$base/purellm/$model
#         # python ./scripts/syntax_checking/insertjml.py $entry $SYNTAXCHECK_PATH/$base/hafis/$model 1 > $SYNTAXCHECK_PATH/$base/hafis/$model/Solution.java
#         # $OPENJML $SYNTAXCHECK_PATH/$base/hafis/$model/Solution.java > $SYNTAXCHECK_PATH/$base/hafis/$model/result.txt

#         python ./scripts/syntax_checking/insertjml.py $entry $SYNTAXCHECK_PATH/$base/purellm/$model/jml.txt 0 > $SYNTAXCHECK_PATH/$base/purellm/$model/Solution.java
#         $OPENJML $SYNTAXCHECK_PATH/$base/purellm/$model/Solution.java > $SYNTAXCHECK_PATH/$base/purellm/$model/result.txt
#     done
# done


# for entry in `find $MUTATECHECK_PATH -type f -name "Solution.java" | sort`
# do
#     printf "%s....\n" $entry
#     folder=""$(dirname $entry)""
#     base=`basename $folder`

#     echo "$OPENJML $entry > $folder/result.txt"
#     $OPENJML $entry > $folder/result.txt
# done

while IFS= read -r entry; do
    printf "%s....\n" $entry
    folder=""$(dirname $entry)""
    base=`basename $folder`

    echo "$OPENJML $entry > $folder/result.txt"
    $OPENJML $entry > $folder/result.txt
done < "$1"