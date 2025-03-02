package g0201_0300.s0238_product_of_array_except_self;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Prefix_Sum
// #Data_Structure_II_Day_5_Array #Udemy_Arrays #Big_O_Time_O(n^2)_Space_O(n)
// #2022_07_04_Time_1_ms_(100.00%)_Space_50.8_MB_(85.60%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums != null;
//@ requires 2 <= nums.length && nums.length <= 100000;
//@ requires (\forall int i; 0 <= i < nums.length; -30 <= nums[i] && nums[i] <= 30);
//@ ensures \result.length == nums.length;
//@ ensures (\forall int i; 0 <= i < nums.length;
@             \result[i] == (\product int j; 0 <= j < nums.length && j != i; nums[j]));
@*/
int n = nums.length;
int[] answer = new int[n];

//@ loop_invariant 0 <= i <= n;
//@ loop_invariant (\forall int j; 0 <= j < i; answer[j] == 1);
for (int i = 0; i < n; i++) {
answer[i] = 1;

int leftProduct = 1;
//@ loop_invariant 0 <= i <= n;
//@ loop_invariant leftProduct == (\product int k; 0 <= k < i; nums[k]);
//@ loop_invariant (\forall int j; 0 <= j < i; answer[j] == leftProduct * (\product int k; j+1 <= k < n; nums[k]));
for (int i = 0; i < n; i++) {
answer[i] *= leftProduct;
leftProduct *= nums[i];

int rightProduct = 1;
//@ loop_invariant 0 <= i <= n;
//@ loop_invariant rightProduct == (\product int k; n-i <= k < n; nums[k]);
//@ loop_invariant (\forall int j; n-i <= j < n; answer[j] == rightProduct * (\product int k; 0 <= k < j; nums[k]));
for (int i = n - 1; i >= 0; i--) {
answer[i] *= rightProduct;
rightProduct *= nums[i];

return answer;
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
                ans[i] = p;
            }
        }
        return ans;
    }
}
