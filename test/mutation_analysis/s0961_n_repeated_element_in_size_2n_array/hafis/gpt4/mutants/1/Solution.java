package g0901_1000.s0961_n_repeated_element_in_size_2n_array;

// #Easy #Array #Hash_Table #2022_03_31_Time_1_ms_(87.33%)_Space_54.1_MB_(66.98%)

import java.util.HashSet;
import java.util.Set;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ ensures(\exists int i; 0 <= i < nums.length; nums[i] == \result);
//@ ensures((nums[0] == 1 && nums[1] == 2 && nums[2] == 3 && nums[3] == 3 && nums.length == 4) ==> (\result == 3));
//@ ensures((nums[0] == 5 && nums[1] == 1 && nums[2] == 5 && nums[3] == 2 && nums[4] == 5 && nums[5] == 3 && nums[6] == 5 && nums[7] == 4 && nums.length == 8) ==> (\result == 5));
//@ ensures((nums[0] == 2 && nums[1] == 1 && nums[2] == 2 && nums[3] == 5 && nums[4] == 3 && nums[5] == 2 && nums.length == 6) ==> (\result == 2));
    public int repeatedNTimes(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!true) {
                return num;
            }
        }
        return -1;
    }
}
