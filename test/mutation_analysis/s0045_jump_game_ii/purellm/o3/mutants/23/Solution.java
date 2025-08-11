package g0001_0100.s0045_jump_game_ii;

// #Medium #Top_100_Liked_Questions #Array #Dynamic_Programming #Greedy
// #Algorithm_II_Day_13_Dynamic_Programming #Dynamic_Programming_I_Day_4
// #Big_O_Time_O(n)_Space_O(1) #2023_08_11_Time_2_ms_(49.02%)_Space_44.7_MB_(52.72%)

public class Solution {
    /*@ public model pure boolean reachableInK(int[] a, int k) {
      @ //     /*  There exists a strictly increasing path p[0..k]
      @ // // // // // //         starting at 0 and finishing at a.length-1, such that every
      @ // // // // //         step is feasible w.r.t. the jump length stored at the
      @ //         departure index. * /
      @     return (\exists int[] p ;
      @                p != null &&
      @                p.length == k + 1 &&
      @                p[0] == 0 &&
      @                p[k] == a.length - 1 &&
      @                (\forall int t ; 0 <= t && t < k ;
      @                     p[t] < p[t+1] &&
      @                     p[t+1] - p[t] <= a[p[t]]));
      @ }
      @*/

    /*@ // // public normal_behavior
      @   requires nums != null;
      @   requires 1 <= nums.length && nums.length <= 10000;
      @   requires (\forall int i ; 0 <= i && i < nums.length ;
      @                0 <= nums[i] && nums[i] <= 1000);
      @   requires reachableInK(nums , nums.length - 1);   // “always reachable”
      @
      @ //   ensures  \result == minJumps(nums);              // minimal # of jumps
      @
      @   assignable \nothing;                             // must be pure
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
            if (length == nums.length - i - 1) {
                return minJump;
            }
        }
        return minJump;
    }
}