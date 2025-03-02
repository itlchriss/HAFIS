package g0001_0100.s0026_remove_duplicates_from_sorted_array;

// #Easy #Top_Interview_Questions #Array #Two_Pointers #Udemy_Two_Pointers
// #2023_08_09_Time_1_ms_(98.56%)_Space_43.9_MB_(51.95%)

public class Solution {
//@ requires(*The integer array parameter `nums` is sorted in non-decreasing order.*);
//@ requires(*The relative order of the elements in the integer array parameter `nums` should be kept the same.*);
//@ ensures(*The integer result is equal to the number of unique elements in the integer array parameter `nums`.*);
//@ ensures(*The first `k` elements of the integer array parameter `nums` hold the final result.*);
//@ ensures(*The integer result is less than or equal to 30000 and is greater than or equal to 0.*);
//@ ensures(*If the integer array parameter `nums` is equal to [1,1,2], the integer result is equal to 2 and the first two elements of the integer array parameter `nums` are 1 and 2 respectively.*);
//@ ensures(*If the integer array parameter `nums` is equal to [0,0,1,1,1,2,2,3,3,4], the integer result is equal to 5 and the first five elements of the integer array parameter `nums` are 0, 1, 2, 3, and 4 respectively.*);
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