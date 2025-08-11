package g0301_0400.s0331_verify_preorder_serialization_of_a_binary_tree;

// #Medium #String #Tree #Binary_Tree #Stack #2022_07_10_Time_2_ms_(99.12%)_Space_42.5_MB_(79.33%)

public class Solution {
//@ requires(*The string parameter `preorder` uses the comma character as delimiter and every token is either the character '#' or a decimal string representation of an integer that is greater than or equal to 0 and is less than or equal to 100.*);
//@ requires(*The string parameter `preorder` does not contain two consecutive comma characters.*);
//@ ensures(*The boolean result is equal to the true literal or the boolean result is equal to the false literal.*);
//@ ensures(*If boolean result is equal to the true literal, the string parameter `preorder` can be interpreted as a valid preorder traversal serialization of a binary tree that uses the character '#' to denote null nodes.*);
//@ ensures(*If boolean result is equal to the false literal, the string parameter `preorder` cannot be interpreted as a valid preorder traversal serialization of a binary tree that uses the character '#' to denote null nodes.*);
//@ ensures(*If the string parameter `preorder` is equal to the string literal '9,3,4,#,#,1,#,#,2,#,6,#,#', the boolean result is equal to the true literal.*);
//@ ensures(*If the string parameter `preorder` is equal to the string literal '1,#', the boolean result is equal to the false literal.*);
//@ ensures(*If the string parameter `preorder` is equal to the string literal '9,#,#,1', the boolean result is equal to the false literal.*);
    public boolean isValidSerialization(String preorder) {
        int count = 1;
        int length = preorder.length();
        for (int i = 1; i <= length; i++) {
            if (i == length || preorder.charAt(i) == ',') {
                --count;
                if (count < 0) {
                    return false;
                }
                count += preorder.charAt(i - 1) == '#' ? 0 : 2;
            }
        }
        return count == 0;
    }
}