package g0101_0200.s0152_maximum_product_subarray;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Dynamic_Programming_I_Day_6 #Level_2_Day_13_Dynamic_Programming #Udemy_Dynamic_Programming
// #Big_O_Time_O(N)_Space_O(1) #2022_06_25_Time_0_ms_(100.00%)_Space_42.7_MB_(82.46%)

public class Solution {
     /*@  public model pure static int prod(int[] a, int lo, int hi);
      @    //  Meaning of the model function (informal, because JML has no
      @    //  built-in product operator):  prod(a,lo,hi) is
      @    //      a[lo] * a[lo+1] * … * a[hi]
      @    //  for 0 ≤ lo ≤ hi < a.length.
      @*/

    /*@  public normal_behavior
      @    //  ------------  Pre-conditions  -------------
      @ //    requires nums != null;
      @ // //    requires 1 <= nums.length && nums.length <= 20000;
      @    // individual element bounds given in the statement
      @ //    requires (\forall int k; 0 <= k && k < nums.length;
      @ // //                       -10 <= nums[k] && nums[k] <= 10);
      @
      @    //  ------------  Post-conditions -------------
      @
      @    // 1)  The returned value is the product of a
      @    //     non-empty contiguous sub-array
      @ //    ensures (\exists int i; 0 <= i && i < nums.length;
      @ //             (\exists int j; i <= j && j < nums.length;
      @ //                    \result == prod(nums,i,j)));
      @
      @    // 2)  … and no other contiguous sub-array has a larger product
      @ //    ensures (\forall int i; 0 <= i && i < nums.length;
      @ //             (\forall int j; i <= j && j < nums.length;
      @ //                    prod(nums,i,j) <= \result));
      @
      @    // 3)  Nothing outside the method is modified
      @    assignable \nothing;
      @*/
    public int maxProduct(int[] arr) {
        int ans = Integer.MIN_VALUE;
        int cprod = 1;
        for (int j : arr) {
            cprod = cprod * j;
            ans = Math.max(ans, cprod);
            if (cprod == 0) {
                cprod = 1;
            }
        }

        //@ maintaining 0 <= i <= arr.length - 1 || i == -1;
        for (int i = arr.length - 1; i >= 0; i--) {
            cprod = cprod * arr[i];
            ans = Math.max(ans, cprod);
            if (cprod == 0) {
                cprod = 1;
            }
        }
        return ans;
    }
}