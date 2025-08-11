package g0001_0100.s0001_two_sum;

// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Hash_Table
// #Data_Structure_I_Day_2_Array #Level_1_Day_13_Hashmap #Udemy_Arrays #Big_O_Time_O(n)_Space_O(n)
// #2024_01_04_Time_2_ms_(85.97%)_Space_44.8_MB_(15.45%)

import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((target >= -1000000000) && (target <= 1000000000));
//@ requires((numbers.length >= 2) && (numbers.length <= 10000));
//@ requires((\forall int i; 0 <= i < numbers.length; numbers[i] >= -1000000000) && (\forall int i; 0 <= i < numbers.length; numbers[i] <= 1000000000));
//@ ensures(\result.length == 2);
//@ ensures((\result[0] >= 0) && (\result[0] < numbers.length));
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        //@ loop_invariant 0 <= i <= numbers.length;
        for (int i = 0; i < numbers.length; i++) {
            // assume Integer.MIN_VALUE + 1 <= target - numbers[i] <= Integer.MAX_VALUE - 1;
            Integer requiredNum = target - numbers[i];
            if (indexMap.containsKey(requiredNum)) {
                return new int[] {indexMap.get(requiredNum), i};
            }

        }
        return new int[] {-1, -1};
    }
}
