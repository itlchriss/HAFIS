package g0001_0100.s0045_jump_game_ii;

// #Medium #Top_100_Liked_Questions #Array #Dynamic_Programming #Greedy
// #Algorithm_II_Day_13_Dynamic_Programming #Dynamic_Programming_I_Day_4
// #Big_O_Time_O(n)_Space_O(1) #2023_08_11_Time_2_ms_(49.02%)_Space_44.7_MB_(52.72%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums != null;
//@ requires nums.length >= 1 && nums.length <= 10000;
//@ requires (\forall int i; 0 <= i < nums.length; 0 <= nums[i] <= 1000);
//@ ensures \result >= 0;
// ensures \result == (\min int j; 0 <= j < nums.length; canReachLastIndex(nums, j); j);
//@ also
//@ signals (Exception e) false;
// pure model boolean canReachLastIndex(int[] nums, int jumps) {
//   int currentPosition = 0;
//   for (int i = 0; i < jumps; i++) {
//     int maxReach = 0;
//     for (int j = currentPosition; j <= currentPosition + nums[currentPosition] && j < nums.length; j++) {
//       maxReach = Math.max(maxReach, j + nums[j]);
//     }
//     currentPosition = maxReach;
//     if (currentPosition >= nums.length - 1) {
//       return true;
//     }
//   }
//   return false;
// }
    public int jump(int[] nums) {
        int length = 0;
        int maxLength = 0;
        int minJump = 0;
        //@ assume nums.length > 1;
        //@ decreases nums.length - 1 - i;
        //@ loop_invariant 0 <= i <= nums.length - 1;
        //@ maintaining length <= nums.length - i - 1;
        for (int i = 0; false; ++i) {
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
