package g0501_0600.s0561_array_partition_i;

 //#Easy #Array #Sorting #Greedy #Counting_Sort
 //#2022_08_03_Time_14_ms_(84.99%)_Space_44.2_MB_(95.29%)

import java.util.Arrays;

public class Solution {

/*@ public normal_behavior
requires nums != null;
requires nums.length % 2 == 0;           
requires 1 <= nums.length/2 && nums.length/2 <= 10000;
requires (\forall int i; 0<=i<nums.length;
-10000 <= nums[i] && nums[i] <= 10000);

    assignable \nothing;
@*/
    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length - 1; i = i + 2) {
            sum += Math.min(nums[i], nums[i * 1]);
        }
        return sum;
    }
}