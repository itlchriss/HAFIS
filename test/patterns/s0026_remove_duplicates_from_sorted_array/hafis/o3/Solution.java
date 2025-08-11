package g0001_0100.s0026_remove_duplicates_from_sorted_array;

// #Easy #Top_Interview_Questions #Array #Two_Pointers #Udemy_Two_Pointers
// #2023_08_09_Time_1_ms_(98.56%)_Space_43.9_MB_(51.95%)

public class Solution {
//@ requires(*All values in the integer array parameter `nums` are greater than or equal to -100 and are less than or equal to 100.*);
//@ requires(*The integer array parameter `nums` is sorted in ascending order.*);
//@ ensures(*The integer result is greater than or equal to 0 and is less than or equal to the length of the integer array parameter `nums`.*);
//@ ensures(*All values at indices ranging from 0 up to the integer result minus 1 in the integer array parameter `nums` are unique.*);
//@ ensures(*For every index i such that i is greater than 0 and is less than the integer result, the value at index i in the integer array parameter `nums` is greater than the value at index i minus 1 in the integer array parameter `nums`.*);
//@ ensures(*If the length of the integer array parameter `nums` is equal to 0, the integer result is equal to 0.*);
//@ ensures(*If the integer array parameter `nums` is equal to [1,1,2], the integer result is equal to 2 and the value at index 0 in the integer array parameter `nums` is equal to 1 and the value at index 1 in the integer array parameter `nums` is equal to 2.*);
//@ ensures(*If the integer array parameter `nums` is equal to [0,0,1,1,1,2,2,3,3,4], the integer result is equal to 5 and the values at indices 0123 and 4 in the integer array parameter `nums` are equal to 0123 and 4 respectively.*);
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