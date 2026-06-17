// #Medium #String #Tree #Binary_Tree #Stack #2022_07_10_Time_2_ms_(99.12%)_Space_42.5_MB_(79.33%)
// Dafny version of Solution

requires (preorder.length <= 10000) && (preorder.length >= 1)
requires (preorder.length <= 10000) && (preorder.length >= 1)
requires (preorder.size() <= 10000) && (preorder.size() >= 1)
requires (preorder.length() <= 10000) && (preorder.length() >= 1)
requires (preorder.size() <= 10000) && (preorder.size() >= 1)
// requires(*The string parameter `preorder` consists of integers in the range [0, 100] and '#' separated by commas ','.*);
// ensures(*The boolean result is true if the string parameter `preorder` is a correct preorder traversal serialization of a binary tree, otherwise false.*);
// ensures(*If the string parameter `preorder` is equal to "9,3,4,#,#,1,#,#,2,#,6,#,#", the boolean result is true.*);
// ensures(*If the string parameter `preorder` is equal to "1,#", the boolean result is false.*);
// ensures(*If the string parameter `preorder` is equal to "9,#,#,1", the boolean result is false.*);
    method isValidSerialization(preorder: string) returns (result: bool)
    {
        var count: int := 1;
        var length: int := |preorder|();
        for i := 1 to length + 1
            invariant i >= 1
            invariant i <= length + 1
        {
            if i == length || preorder[i] == ',' {
                count := count - 1;
                if count < 0 {
                    result := false;
                    return;
                }
                count := count + if preorder[i - 1] == '#' then 0 else 2;
            }
        }
        result := count == 0;
        return;
    }
