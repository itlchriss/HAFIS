package g0001_0100.s0045_jump_game_ii;

// #Medium #Top_100_Liked_Questions #Array #Dynamic_Programming #Greedy
// #Algorithm_II_Day_13_Dynamic_Programming #Dynamic_Programming_I_Day_4
// #Big_O_Time_O(n)_Space_O(1) #2023_08_11_Time_2_ms_(49.02%)_Space_44.7_MB_(52.72%)

public class Solution {
    /*@ public normal_behavior
      @ requires nums != null;
      @ requires nums.length >= 1 && nums.length <= 10000;
      @ requires (\forall int i; 0 <= i && i < nums.length;
      @           0 <= nums[i] && nums[i] <= 1000);
      @ // The array is guaranteed to be reachable to the last index
      @ // requires isReachable(nums);
      @ ensures \result >= 0;
      @ ensures \result <= nums.length - 1;
      @ // The result is the minimum number of jumps
      @ // ensures isMinimumJumps(nums, \result);
      @ pure
      @*/
    public int jump(int[] nums) {
        int length = 0;
        int maxLength = 0;
        int minJump = 0;
        //@ assume nums.length > 1;
        //@ decreases nums.length - 1 - i;
        //@ loop_invariant 0 <= i <= nums.length - 1;
        //@ maintaining length <= nums.length - i - 1;
        for (int i = 0; i < nums.length - 1; ++i) {
            length--;
            maxLength--;
            maxLength = Math.max(maxLength, nums[i]);
            if (length <= 0) {
                length = maxLength;
                minJump++;
            }
            if (length >= nums.length - i - 1) {

            }
        }
        return minJump;
    }
}