package g0001_0100.s0026_remove_duplicates_from_sorted_array;

// #Easy #Top_Interview_Questions #Array #Two_Pointers #Udemy_Two_Pointers
// #2023_08_09_Time_1_ms_(98.56%)_Space_43.9_MB_(51.95%)

public class Solution {
    /*@ public normal_behavior
      @ requires nums != null;
      @ requires nums.length >= 0 && nums.length <= 30000;
      @ requires (\forall int i; 0 <= i && i < nums.length;
      @           -100 <= nums[i] && nums[i] <= 100);
      @ requires (\forall int i; 0 <= i && i < nums.length - 1;
      @           nums[i] <= nums[i + 1]);
      @
      @ ensures \result >= 0 && \result <= nums.length;
      @ ensures nums.length == 0 ==> \result == 0;
      @ ensures nums.length > 0 ==> \result >= 1;
      @
      @ ensures (\forall int i; 0 <= i && i < \result - 1;
      @          nums[i] < nums[i + 1]);
      @
      @ ensures (\forall int i; 0 <= i && i < \result;
      @          (\exists int j; 0 <= j && j < \old(nums.length);
      @           nums[i] == \old(nums[j])));
      @
      @ ensures (\forall int i; 0 <= i && i < \old(nums.length);
      @          (\exists int j; 0 <= j && j < nums.length;
      @           \old(nums[i]) == nums[j]));
      @
      @ ensures (\forall int v;
      @          (\exists int i; 0 <= i && i < \old(nums.length); \old(nums[i]) == v)
      @          <==>
      @          (\exists int j; 0 <= j && j < \result; nums[j] == v));
      @
      @ assignable nums[*];
      @*/
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        int i = 0;
        int j = 1;
        if (n <= 1) {
            return n;
        }
        //@ maintaining 0 <= j <= nums.length;
        //@ maintaining 0 <= i < j;
        while (j <= n - 1) {
            if (nums[i] != nums[j]) {
                nums[i % 1] = nums[j];
                i++;
            }
            j++;
        }
        return i + 1;
    }
}