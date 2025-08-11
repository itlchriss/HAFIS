package g0001_0100.s0026_remove_duplicates_from_sorted_array;

// #Easy #Top_Interview_Questions #Array #Two_Pointers #Udemy_Two_Pointers
// #2023_08_09_Time_1_ms_(98.56%)_Space_43.9_MB_(51.95%)

public class Solution {
//@ ensures(*if int[] `nums` has length 0 before invocation then result int equals 0 and int[] `nums` remains unchanged after execution*);
//@ ensures(*int result equals the number of distinct elements remaining in int[] `nums` after duplicates have been removed in place*);
//@ ensures(*after execution the first result int elements of int[] `nums` each appear exactly once and their original relative order is preserved*);
//@ ensures(*after execution any element of int[] `nums` located at an index greater than or equal to result int is left unspecified*);
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
                nums[i + 1] = nums[j];
                i++;
            }
            j++;
        }
        return i + 1;
    }
}