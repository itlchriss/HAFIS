package g0201_0300.s0238_product_of_array_except_self;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Prefix_Sum
// #Data_Structure_II_Day_5_Array #Udemy_Arrays #Big_O_Time_O(n^2)_Space_O(n)
// #2022_07_04_Time_1_ms_(100.00%)_Space_50.8_MB_(85.60%)

public class Solution {
    /*@
      @ public normal_behavior
      @   // -----------  PRE-CONDITIONS  ---------------------------------
      @   requires nums != null;                                 // array must exist
      @   requires 2 <= nums.length && nums.length <= 100_000;   // size constraint
      @   requires (\forall int i; 0 <= i && i < nums.length;    // element range
      @                      -30 <= nums[i] && nums[i] <= 30);
      @
      @   // -----------  POST-CONDITIONS  -------------------------------
      @   ensures \result != null;                               // result exists
      @   ensures \result.length == nums.length;                 // same length
      @
      @   // product of all elements except the current one
      @   ensures (\forall int i; 0 <= i && i < nums.length;
      @              \result[i] == (\product int j;
      @                                   0 <= j && j < nums.length && j != i;
      @                                   nums[j]));
      @
      @   // every returned value still fits into a 32-bit signed int
      @   ensures (\forall int i; 0 <= i && i < \result.length;
      @              Integer.MIN_VALUE <= \result[i] &&
      @              \result[i] <= Integer.MAX_VALUE);
      @
      @   // -----------  FRAME CONDITION  -------------------------------
      @   assignable \nothing;   // the method does not mutate any existing state
      @
      @*/
    public int[] productExceptSelf(int[] nums) {
        int product = 1;
        int[] ans = new int[nums.length];
        for (int num : nums) {
            product = product * num;
        }
        //@ maintaining 0 <= i <= nums.length;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                ans[i] = product / nums[i];
            } else {
                int p = 1;
                //@ maintaining 0 <= j <= nums.length;
                for (int j = 0; j < nums.length; j++) {
                    if (j != i) {
                        p = p * nums[j];
                    }
                }

            }
        }
        return ans;
    }
}