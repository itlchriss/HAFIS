package g0001_0100.s0001_two_sum;

// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Hash_Table
// #Data_Structure_I_Day_2_Array #Level_1_Day_13_Hashmap #Udemy_Arrays #Big_O_Time_O(n)_Space_O(n)
// #2024_01_04_Time_2_ms_(85.97%)_Space_44.8_MB_(15.45%)

import java.util.HashMap;
import java.util.Map;

public class Solution {
//@ requires(*All values in the integer array parameter `numbers` are greater than or equal to -1000000000 and are less than or equal to 1000000000.*);
//@ requires(*The integer parameter `target` is greater than or equal to -1000000000 and is less than or equal to 1000000000.*);
//@ requires(*Exactly one pair of distinct indices exists in the integer array parameter `numbers` whose corresponding values sum to the integer parameter `target`.*);
//@ ensures(*The length of the integer array result is equal to 2.*);
//@ ensures(*The first value of the integer array result is not equal to the second value of the integer array result.*);
//@ ensures(*Both values in the integer array result are greater than or equal to 0 and are less than the length of the integer array parameter `numbers`.*);
//@ ensures(*The sum between the value at the index equal to the first value of the integer array result of the integer array parameter `numbers` and the value at the index equal to the second value of the integer array result of the integer array parameter `numbers` is equal to the integer parameter `target`.*);
//@ ensures(*If the integer array parameter `numbers` is equal to [2,7,11,15] and the integer parameter `target` is equal to 9, the integer array result is equal to [0,1].*);
//@ ensures(*If the integer array parameter `numbers` is equal to [3,2,4] and the integer parameter `target` is equal to 6, the integer array result is equal to [1,2].*);
//@ ensures(*If the integer array parameter `numbers` is equal to [3,3] and the integer parameter `target` is equal to 6, the integer array result is equal to [0,1].*);
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        //@ loop_invariant 0 <= i <= numbers.length;
        for (int i = 0; i < numbers.length; i++) {
            // assume Integer.MIN_VALUE + 1 <= target - numbers[i] <= Integer.MAX_VALUE - 1;
            Integer requiredNum = target - numbers[i];
            if (indexMap.containsKey(requiredNum)) {
                return new int[] {indexMap.get(requiredNum), i};
            }
            indexMap.put(numbers[i], i);
        }
        return new int[] {-1, -1};
    }
}