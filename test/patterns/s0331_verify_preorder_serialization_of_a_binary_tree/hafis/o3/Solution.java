package g0301_0400.s0331_verify_preorder_serialization_of_a_binary_tree;

// #Medium #String #Tree #Binary_Tree #Stack #2022_07_10_Time_2_ms_(99.12%)_Space_42.5_MB_(79.33%)

public class Solution {
//@ requires(*The string parameter `preorder` contains only digits commas and the character '#'.*);
//@ requires(*Each comma separated value in the string parameter `preorder` is either the character '#' or the string representation of an integer that is greater than or equal to 0 and is less than or equal to 100.*);
//@ ensures(*If boolean result is equal to the true literal, there exists at least one binary tree whose preorder traversal serialization that uses the character '#' for null nodes is equal to the string parameter `preorder`.*);
//@ ensures(*If boolean result is equal to the false literal, there does not exist any binary tree whose preorder traversal serialization that uses the character '#' for null nodes is equal to the string parameter `preorder`.*);
//@ ensures(*If the string parameter `preorder` is equal to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", the boolean result is equal to the true literal.*);
//@ ensures(*If the string parameter `preorder` is equal to the string "1,#", the boolean result is equal to the false literal.*);
//@ ensures(*If the string parameter `preorder` is equal to the string "9,#,#,1", the boolean result is equal to the false literal.*);
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