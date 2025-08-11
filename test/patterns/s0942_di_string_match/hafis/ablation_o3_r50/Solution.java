package g0901_1000.s0942_di_string_match;

// #Easy #Array #String #Math #Greedy #Two_Pointers
// #2022_03_30_Time_4_ms_(33.74%)_Space_48.7_MB_(20.18%)

public class Solution {
//@ requires(*All characters in the string parameter `s` are either the 'I' literal or the 'D' literal*);
//@ ensures(*The length of the integer array result is equal to the length of the string parameter `s` plus 1*);
//@ ensures(*The integer array result contains every integer in the range 0 to the length of the string parameter `s` exactly once*);
//@ ensures(*For every index i that is less than the length of the string parameter `s`, if the character at position i in the string parameter `s` is the 'I' literal, the value at position i in the integer array result is less than the value at position i plus 1 in the integer array result*);
//@ ensures(*For every index i that is less than the length of the string parameter `s`, if the character at position i in the string parameter `s` is the 'D' literal, the value at position i in the integer array result is greater than the value at position i plus 1 in the integer array result*);
//@ ensures(*If the string parameter `s` is equal to "IDID", the integer array result is equal to [0,4,1,3,2]*);
//@ ensures(*If the string parameter `s` is equal to "III", the integer array result is equal to [0,1,2,3]*);
//@ ensures(*If the string parameter `s` is equal to "DDI", the integer array result is equal to [3,2,0,1]*);
    public int[] diStringMatch(String s) {
        int[] arr = new int[s.length() + 1];
        int max = s.length();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'D') {
                arr[i] = max;
                max--;
            }
        }
        for (int i = s.length() - 1; i >= 0 && max > 0; i--) {
            if (s.charAt(i) == 'I' && arr[i + 1] == 0) {
                arr[i + 1] = max;
                max--;
            }
        }
        for (int i = 0; i < arr.length && max > 0; i++) {
            if (arr[i] == 0) {
                arr[i] = max;
                max--;
            }
        }

        return arr;
    }
}