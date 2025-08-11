package g0001_0100.s0001_two_sum;

// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Hash_Table
// #Data_Structure_I_Day_2_Array #Level_1_Day_13_Hashmap #Udemy_Arrays #Big_O_Time_O(n)_Space_O(n)
// #2024_01_04_Time_2_ms_(85.97%)_Space_44.8_MB_(15.45%)

import java.util.HashMap;
import java.util.Map;

public class Solution {
    /*@ public normal_behavior
      @   // ----------  Pre-conditions  ----------
      @ //   //requires nums != null;
      @ // //   //requires 2 <= nums.length && nums.length <= 10000;
      @
      @   // element range
      @ //   //requires (\forall int i; 0 <= i < nums.length;
      @ // //   //                    -1000000000 <= nums[i] && nums[i] <= 1000000000);
      @
      @   // target range
      @   requires -1000000000 <= target && target <= 1000000000;
      @
      @   // at least one (i , j) s.t. nums[i] + nums[j] == target
      @   //requires (\exists int i, j;
      @ // //   //             0 <= i < nums.length && 0 <= j < nums.length && i != j;
      @ // //   //             nums[i] + nums[j] == target);
      @
      @   // and that answer is unique
      @   //requires (\forall int i1, j1, i2, j2;
      @ // //   //            0 <= i1 < nums.length && 0 <= j1 < nums.length &&
      @ // //   //            0 <= i2 < nums.length && 0 <= j2 < nums.length &&
      @   //            i1 != j1 && i2 != j2 &&
      @ // //   //           nums[i1] + nums[j1] == target &&
      @ // //   //            nums[i2] + nums[j2] == target
      @   //         ==> (i1 == i2 && j1 == j2));
      @
      @   // ----------  Post-conditions  ----------
      @   ensures \result != null && \result.length == 2;
      @ //   //ensures 0 <= \result[0] < nums.length;
      @ //   //ensures 0 <= \result[1] < nums.length;
      @   ensures \result[0] != \result[1];
      @
      @   // correctness of the returned pair
      @ // //   //ensures nums[\result[0]] + nums[\result[1]] == target;
      @
      @   // and its uniqueness (the “only one valid answer” clause)
      @   //ensures (\forall int i, j;
      @ // //   //           0 <= i < nums.length && 0 <= j < nums.length && i != j;
      @ // //   //           nums[i] + nums[j] == target
      @   //         ==> ((i == \result[0] && j == \result[1]) ||
      @   //             (i == \result[1] && j == \result[0])));
      @
      @   // the method does not mutate any pre-existing object state
      @   assignable \nothing;
      @*/
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        //@ loop_invariant 0 <= i <= numbers.length;
        for (int i = 0; i < numbers.length; i++) {
            // assume Integer.MIN_VALUE + 1 <= target - numbers[i] <= Integer.MAX_VALUE - 1;
            Integer requiredNum = target + numbers[i];
            if (indexMap.containsKey(requiredNum)) {
                return new int[] {indexMap.get(requiredNum), i};
            }
            indexMap.put(numbers[i], i);
        }
        return new int[] {-1, -1};
    }
}