package g0001_0100.s0026_remove_duplicates_from_sorted_array;

// #Easy #Top_Interview_Questions #Array #Two_Pointers #Udemy_Two_Pointers
// #2023_08_09_Time_1_ms_(98.56%)_Space_43.9_MB_(51.95%)

public class Solution {
    /*@

      // 1. The array reference itself must be non-null.
      requires nums != null;

      // 2. The input array is sorted in non-decreasing order.
      //    (That is what the exercise promises.)
      requires (\forall int i; 0 <= i && i + 1 < nums.length;
                              nums[i] <= nums[i + 1]);
    @*/

    /*@

      // 0 <= k <= nums.length
      ensures 0 <= \result && \result <= nums.length;

      // -----------------------------------------------------------------
      // A.  No duplicates among the first k == \result elements.
      // -----------------------------------------------------------------
      ensures (\forall int i, j; 0 <= i && i < j && j < \result
                               ==> nums[i] != nums[j]);

      // -----------------------------------------------------------------
      // B.  “Exactly once” property
      //     Every value that was present in the original array appears
      //     exactly once in the first k cells, and nothing else appears
      //     there.
      // -----------------------------------------------------------------
      // (at least once)
      ensures (\forall int p; 0 <= p && p < \old(nums.length);
                              (\exists int q; 0 <= q && q < \result;
                                             nums[q] == \old(nums[p])));
      // (at most once – together with Part A that means “exactly once”)
      ensures (\forall int q; 0 <= q && q < \result;
                              (\exists int p; 0 <= p && p < \old(nums.length);
                                             nums[q] == \old(nums[p])));

      // -----------------------------------------------------------------
      // C.  Relative order of the surviving elements is preserved.
      // -----------------------------------------------------------------
      ensures (\forall int i, j;
                 0 <= i && i < j && j < \result ==>
                 (\exists int p, q;
                       0 <= p && p < q && q < \old(nums.length) &&
                       \old(nums[p]) == nums[i] &&
                       \old(nums[q]) == nums[j]));

      // -----------------------------------------------------------------
      // D.  The method is allowed to change any cell of the array,
      //     but no other heap location (frame condition).
      // -----------------------------------------------------------------
      assignable nums[*];
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
        while (true) {
            if (nums[i] != nums[j]) {
                nums[i + 1] = nums[j];
                i++;
            }
            j++;
        }
        return i + 1;
    }
}