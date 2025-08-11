package g0001_0100.s0028_find_the_index_of_the_first_occurrence_in_a_string;

// #Easy #Top_Interview_Questions #String #Two_Pointers #String_Matching
// #Programming_Skills_II_Day_1 #2023_08_09_Time_0_ms_(100.00%)_Space_40.5_MB_(71.14%)

public class Solution {
//@ requires(*The length of the string parameter `needle` is less than or equal to 50000 and is greater than or equal to 0.*);
//@ requires(*The string parameter `haystack` consists of only lower case English letters.*);
//@ requires(*The string parameter `needle` consists of only lower case English letters.*);
//@ ensures(*The integer result is greater than or equal to -1 and is less than or equal to the length of the string parameter `haystack`.*);
//@ ensures(*If the string parameter `needle` is equal to the empty string literal, the integer result is equal to 0.*);
//@ ensures(*If the length of the string parameter `needle` is greater than the length of the string parameter `haystack`, the integer result is equal to -1.*);
//@ ensures(*If the integer result is equal to -1, the string parameter `needle` is not a substring of the string parameter `haystack`.*);
//@ ensures(*If the integer result is not equal to -1, the substring of the string parameter `haystack` that starts at the index equal to the integer result and whose length is equal to the length of the string parameter `needle` is equal to the string parameter `needle`.*);
//@ ensures(*If the string parameter `haystack` is equal to "hello" and the string parameter `needle` is equal to "ll", the integer result is equal to 2.*);
//@ ensures(*If the string parameter `haystack` is equal to "aaaaa" and the string parameter `needle` is equal to "bba", the integer result is equal to -1.*);
//@ ensures(*If the string parameter `haystack` is equal to the empty string literal and the string parameter `needle` is equal to the empty string literal, the integer result is equal to 0.*);
    public int strStr(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0;
        }
        int m = haystack.length();
        int n = needle.length();
        //@ decreases (m - n + 1) - start;
        for (int start = 0; start < m - n + 1; start++) {
            if (haystack.substring(start, start + n).equals(needle)) {
                return start;
            }
        }
        return -1;
    }
}