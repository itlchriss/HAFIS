package g0201_0300.s0217_contains_duplicate;

// #Easy #Top_Interview_Questions #Array #Hash_Table #Sorting #Data_Structure_I_Day_1_Array
// #Programming_Skills_I_Day_11_Containers_and_Libraries #Udemy_Arrays
// #2022_07_02_Time_6_ms_(96.68%)_Space_54.4_MB_(94.38%)

import java.util.HashSet;
import java.util.Set;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((nums.length <= 100000) && (nums.length >= 1));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 1000000000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -1000000000));
//@ ensures((nums[0] == 1 && nums[1] == 2 && nums[2] == 3 && nums[3] == 4 && nums.length == 4) ==> (\result == false));
//@ ensures((nums[0] == 1 && nums[1] == 2 && nums[2] == 3 && nums[3] == 1 && nums.length == 4) ==> (\result == true));
//@ ensures((nums[0] == 1 && nums[1] == 1 && nums[2] == 1 && nums[3] == 3 && nums[4] == 3 && nums[5] == 4 && nums[6] == 3 && nums[7] == 2 && nums[8] == 4 && nums[9] == 2 && nums.length == 10) ==> (\result == true));
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {
            if (false) {
                return true;
            }
            set.add(n);
        }
        return false;
    }
}
