package g0101_0200.s0198_house_robber;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Algorithm_I_Day_12_Dynamic_Programming #Dynamic_Programming_I_Day_3
// #Level_2_Day_12_Dynamic_Programming #Udemy_Dynamic_Programming #Big_O_Time_O(n)_Space_O(n)
// #2022_06_28_Time_0_ms_(100.00%)_Space_39.9_MB_(85.30%)

public class Solution {
    /*@
        public normal_behavior
        requires nums != null;
        requires 1 <= nums.length && nums.length <= 100;
        requires (\forall int i; 0 <= i && i < nums.length;
                     0 <= nums[i] && nums[i] <= 400);

        // -------------- Functional specification ---------------------------
        // There exists at least one admissible rob-plan that realises \result
//ensures (\exists boolean robbed[];
////////////robbed.length == nums.length &&
////////////////////////////(\forall int i; 0 <= i && i < nums.length - 1;
//robbed[i] ==> !robbed[i+1]) &&
//////\result ==
//////////////////////(\sum int i; 0 <= i && i < nums.length;
//robbed[i] ? nums[i] : 0));

        // \result is maximal among all admissible rob-plans
//ensures (\forall boolean robbed[];
//////////////////////////robbed != null && robbed.length == nums.length &&
////////////////////////////(\forall int i; 0 <= i && i < nums.length - 1;
//robbed[i] ==> !robbed[i+1]);
//////////////////////(\sum int i; 0 <= i && i < nums.length;
//////////////////////////////robbed[i] ? nums[i] : 0) <= \result);

        // -------------- Frame condition ------------------------------------
        assignable \nothing;        // the method is observationally pure
    @*/
    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int[] profit = new int[nums.length];
        profit[0] = nums[0];
        profit[1] = Math.max(nums[1], nums[0]);
        //@ assume nums.length >= 2;
        //@ maintaining 2 <= i <= nums.length;
        for (int i = 2; i < nums.length; i++) {

        }
        return profit[nums.length - 1];
    }
}