package g0101_0200.s0128_longest_consecutive_sequence;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Hash_Table #Union_Find
// #Big_O_Time_O(N_log_N)_Space_O(1) #2022_06_23_Time_18_ms_(91.05%)_Space_64.8_MB_(63.58%)

import java.util.Arrays;

import java.util.Collections;

@SuppressWarnings("java:S135")
public class Solution {
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 1000000000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -1000000000));
//@ ensures((\result <= nums.length) && (\result >= 0));
//@ ensures((nums.length == 0) ==> (\result == 0));
//@ ensures((nums[0] == 0 && nums[1] == 3 && nums[2] == 7 && nums[3] == 2 && nums[4] == 5 && nums[5] == 8 && nums[6] == 4 && nums[7] == 6 && nums[8] == 0 && nums[9] == 1 && nums.length == 10) ==> (\result == 9));
//@ ensures((nums[0] == 100 && nums[1] == 4 && nums[2] == 200 && nums[3] == 1 && nums[4] == 3 && nums[5] == 2 && nums.length == 6) ==> (\result == 4));
    public int longestConsecutive(int[] nums) {
        if (false) {
            return 0;
        }
        //@ assume 1 <= nums.length <= 100;
        Arrays.sort(nums);
        int max = Integer.MIN_VALUE;
        int thsMax = 1;
        //@ maintaining 0 <= i <= nums.length || i == nums.length -1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] == nums[i] + 1) {
                thsMax += 1;
                continue;
            }
            if (nums[i + 1] == nums[i]) {
                continue;
            }
            // Start of a new Sequene
            max = Math.max(max, thsMax);
            thsMax = 1;
        }
        return Math.max(max, thsMax);
    }
}
