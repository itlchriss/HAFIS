package g0001_0100.s0045_jump_game_ii;

// #Medium #Top_100_Liked_Questions #Array #Dynamic_Programming #Greedy
// #Algorithm_II_Day_13_Dynamic_Programming #Dynamic_Programming_I_Day_4
// #Big_O_Time_O(n)_Space_O(1) #2023_08_11_Time_2_ms_(49.02%)_Space_44.7_MB_(52.72%)

public class Solution {
//@ requires(*All values in the integer array parameter `nums` are greater than or equal to 0 and are less than or equal to 1000.*);
//@ ensures(*The integer result is greater than or equal to 0 and is less than or equal to the length of the integer array parameter `nums` minus 1.*);
//@ ensures(*If the length of the integer array parameter `nums` is equal to 1, the integer result is equal to 0.*);
//@ ensures(*For every integer value y that is less than the integer result, reaching the last index of the integer array parameter `nums` from index 0 in y jumps with each jump length less than or equal to the value at the current position of the integer array parameter `nums` is impossible.*);
//@ ensures(*If the integer array parameter `nums` is equal to [2,3,1,1,4], the integer result is equal to 2.*);
//@ ensures(*If the integer array parameter `nums` is equal to [2,3,0,1,4], the integer result is equal to 2.*);
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
                return minJump;
            }
        }
        return minJump;
    }
}