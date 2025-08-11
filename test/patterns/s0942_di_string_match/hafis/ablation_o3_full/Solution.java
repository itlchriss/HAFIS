package g0901_1000.s0942_di_string_match;

// #Easy #Array #String #Math #Greedy #Two_Pointers
// #2022_03_30_Time_4_ms_(33.74%)_Space_48.7_MB_(20.18%)

public class Solution {
//@ ensures(*int[] result ensures length equals length of String `s` plus one*);
//@ ensures(*int[] result ensures it contains each integer from zero to length of String `s` exactly once*);
//@ ensures(*int[] result ensures that for every index i with i greater than or equal to zero and i less than length of String `s` when the character at index i in String `s` is uppercase I then the element at index i in int[] result is less than the element at index i plus one in int[] result*);
//@ ensures(*int[] result ensures that for every index i with i greater than or equal to zero and i less than length of String `s` when the character at index i in String `s` is uppercase D then the element at index i in int[] result is greater than the element at index i plus one in int[] result*);
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