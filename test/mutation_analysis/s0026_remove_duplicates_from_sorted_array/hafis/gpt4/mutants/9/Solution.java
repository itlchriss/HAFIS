package g0001_0100.s0026_remove_duplicates_from_sorted_array;

// #Easy #Top_Interview_Questions #Array #Two_Pointers #Udemy_Two_Pointers
// #2023_08_09_Time_1_ms_(98.56%)_Space_43.9_MB_(51.95%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires(\forall int i; 0 <= i < nums.length-1; nums[i] <= nums[i+1]);
//@ requires((nums.length <= 30000) && (nums.length >= 0));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 100) && (\forall int i; 0 <= i < nums.length; nums[i] >= -100));
//@ ensures((\result <= nums.length) && (\result >= 0));
//@ ensures(((\result == 2) && (nums[0] == 1 && nums[1] == 1 && nums[2] == 2 && nums.length == 3)) ==> (nums[0] == 1 && nums[1] == 2));
//@ ensures(((\result == 5) && (nums[0] == 0 && nums[1] == 0 && nums[2] == 1 && nums[3] == 1 && nums[4] == 1 && nums[5] == 2 && nums[6] == 2 && nums[7] == 3 && nums[8] == 3 && nums[9] == 4 && nums.length == 10)) ==> (nums[0] == 0 && nums[1] == 1 && nums[2] == 2 && nums[3] == 3 && nums[4] == 4));
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        int i = 0;
        int j = 1;
        if (n <= 1) {
            return n;
        }
        //@ maintaining 0 <= j <= nums.length;
        //@ maintaining 0 <= i < j;
        while (true) {
            if (nums[i] != nums[j]) {
                nums[i + 1] = nums[j];
                i++;
            }
            j++;
        }
        return i + 1;
    }
}
