package g0001_0100.s0001_two_sum;

// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Hash_Table
// #Data_Structure_I_Day_2_Array #Level_1_Day_13_Hashmap #Udemy_Arrays #Big_O_Time_O(n)_Space_O(n)
// #2024_01_04_Time_2_ms_(85.97%)_Space_44.8_MB_(15.45%)

import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
// requires nums != null;
// requires nums.length >= 2 && nums.length <= 10000;
// requires (\forall int i; 0 <= i < nums.length; -1000000000 <= nums[i] <= 1000000000);
//@ requires -1000000000 <= target <= 1000000000;
//@ ensures \result.length == 2;
// ensures 0 <= \result[0] < nums.length && 0 <= \result[1] < nums.length;
//@ ensures \result[0] != \result[1];
// ensures nums[\result[0]] + nums[\result[1]] == target;
// ensures (\forall int i, j; 0 <= i < j < nums.length; nums[i] + nums[j] == target ==> (i == \result[0] && j == \result[1]) || (i == \result[1] && j == \result[0]));
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        //@ loop_invariant 0 <= i <= numbers.length;
        for (int i = 0; i < numbers.length; i++) {
            // assume Integer.MIN_VALUE + 1 <= target - numbers[i] <= Integer.MAX_VALUE - 1;
            Integer requiredNum = target - numbers[i];
            if (indexMap.containsKey(requiredNum)) {

            }
            indexMap.put(numbers[i], i);
        }
        return new int[] {-1, -1};
    }
}
