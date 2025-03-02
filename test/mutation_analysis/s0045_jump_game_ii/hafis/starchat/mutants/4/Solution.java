package g0001_0100.s0045_jump_game_ii;

// #Medium #Top_100_Liked_Questions #Array #Dynamic_Programming #Greedy
// #Algorithm_II_Day_13_Dynamic_Programming #Dynamic_Programming_I_Day_4
// #Big_O_Time_O(n)_Space_O(1) #2023_08_11_Time_2_ms_(49.02%)_Space_44.7_MB_(52.72%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((nums.length <= 10000) && (nums.length >= 1));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 1000) && (\forall int i; 0 <= i < nums.length; nums[i] >= 0));
//@ ensures((\result >= 1) && (\result <= nums.length));
//@ ensures((nums[0] == 2 && nums[1] == 3 && nums[2] == 1 && nums[3] == 1 && nums[4] == 4 && nums.length == 5) ==> (\result == 2));
//@ ensures((nums[0] == 2 && nums[1] == 3 && nums[2] == 0 && nums[3] == 1 && nums[4] == 4 && nums.length == 5) ==> (\result == 2));
    public int jump(int[] nums) {
        int length = 0;
        int maxLength = 0;
        int minJump = 0;
        //@ assume nums.length > 1;
        //@ decreases nums.length - 1 - i;
        //@ loop_invariant 0 <= i <= nums.length - 1;
        //@ maintaining length <= nums.length - i - 1;
        for (int i = 0; i != nums.length - 1; ++i) {
            length--;
            maxLength--;
            maxLength = Math.max(maxLength, nums[i]);
            if (length <= 0) {
                length = maxLength;
                minJump++;
            }
            if (length >= nums.length - i - 1) {
                return minJump;
            }
        }
        return minJump;
    }
}
