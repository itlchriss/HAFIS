package g0901_1000.s0925_long_pressed_name;

// #Easy #String #Two_Pointers #2022_03_29_Time_1_ms_(84.87%)_Space_40.1_MB_(93.12%)

public class Solution {
//@ requires(*The length of the string parameter `typed` is less than or equal to 1000 and is greater than or equal to 1.*);
//@ requires(*The string parameter `name` consists of only English lowercase letters.*);
//@ requires(*The string parameter `typed` consists of only English lowercase letters.*);
//@ ensures(*If the boolean result is equal to the true literal, the string parameter `typed` contains the characters of the string parameter `name` in the same order and each contiguous repetition of a character in the string parameter `typed` has a length that is greater than or equal to one and is greater than or equal to the length of the corresponding contiguous repetition in the string parameter `name`.*);
//@ ensures(*If the boolean result is equal to the false literal, the string parameter `typed` does not satisfy the ordering or repetition condition described for the true literal with respect to the string parameter `name`.*);
//@ ensures(*If the string parameter `name` is equal to the literal "alex" and the string parameter `typed` is equal to the literal "aaleex", the boolean result is equal to the true literal.*);
//@ ensures(*If the string parameter `name` is equal to the literal "saeed" and the string parameter `typed` is equal to the literal "ssaaedd", the boolean result is equal to the false literal.*);
    public boolean isLongPressedName(String name, String typed) {
        int i = 0;
        int j = 0;
        char prev = '$';
        if (typed.length() < name.length()) {
            return false;
        }
        while (i < name.length() && j < typed.length()) {
            while (j < typed.length() && typed.charAt(j) != name.charAt(i)) {
                if (typed.charAt(j) != prev) {
                    return false;
                }
                if (j == typed.length() - 1) {
                    return false;
                }
                j++;
            }
            prev = name.charAt(i);
            i++;
            j++;
        }
        if (i < name.length()) {
            return false;
        }
        for (; j < typed.length(); j++) {
            if (typed.charAt(j) != prev) {
                return false;
            }
        }
        return true;
    }
}