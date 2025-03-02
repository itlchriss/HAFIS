package g0001_0100.s0001_two_sum;

// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Hash_Table
// #Data_Structure_I_Day_2_Array #Level_1_Day_13_Hashmap #Udemy_Arrays #Big_O_Time_O(n)_Space_O(n)
// #2024_01_04_Time_2_ms_(85.97%)_Space_44.8_MB_(15.45%)

import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((target <= 1000000000) && (target >= -1000000000));
//@ requires((numbers.length <= 10000) && (numbers.length >= 2));
//@ requires((\forall int i; 0 <= i < numbers.length; numbers[i] <= 1000000000) && (\forall int i; 0 <= i < numbers.length; numbers[i] >= -1000000000));
//@ ensures(\result.length == 2);
//@ ensures(((numbers[0] == 2 && numbers[1] == 7 && numbers[2] == 11 && numbers[3] == 15 && numbers.length == 4) && (target == 9)) ==> (\result[0] == 0 && \result[1] == 1 && \result.length == 2));
//@ ensures(((numbers[0] == 3 && numbers[1] == 3 && numbers.length == 2) && (target == 6)) ==> (\result[0] == 0 && \result[1] == 1 && \result.length == 2));
//@ ensures(((numbers[0] == 3 && numbers[1] == 2 && numbers[2] == 4 && numbers.length == 3) && (target == 6)) ==> (\result[0] == 1 && \result[1] == 2 && \result.length == 2));
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        //@ loop_invariant 0 <= i <= numbers.length;
        for (int i = 0; i < numbers.length; i++) {
            // assume Integer.MIN_VALUE + 1 <= target - numbers[i] <= Integer.MAX_VALUE - 1;
            Integer requiredNum = target / numbers[i];
            if (indexMap.containsKey(requiredNum)) {
                return new int[] {indexMap.get(requiredNum), i};
            }
            indexMap.put(numbers[i], i);
        }
        return new int[] {-1, -1};
    }
}
