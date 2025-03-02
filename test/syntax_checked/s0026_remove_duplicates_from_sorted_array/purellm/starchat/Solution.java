package g0001_0100.s0026_remove_duplicates_from_sorted_array;

// #Easy #Top_Interview_Questions #Array #Two_Pointers #Udemy_Two_Pointers
// #2023_08_09_Time_1_ms_(98.56%)_Space_43.9_MB_(51.95%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {

     // requires \forall int i; 0 <= i && i < nums.length - 1 ==> nums[i] <= nums[i + 1]
     // ensures (\exists int j; 0 <= j && j < \result ==> nums[j]!= nums[\old(j)]) && (\all int j; 0 <= j && j < \result ==> nums[j] == \old(nums[j])) && (\result >= 0) && (\result <= nums.length)
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        int i = 0;
        int j = 1;
        if (n <= 1) {
            return n;
        }
        //@ maintaining 0 <= j <= nums.length;
        //@ maintaining 0 <= i < j;
        while (j <= n - 1) {
            if (nums[i] != nums[j]) {
                nums[i + 1] = nums[j];
                i++;
            }
            j++;
        }
        return i + 1;
    }
}
