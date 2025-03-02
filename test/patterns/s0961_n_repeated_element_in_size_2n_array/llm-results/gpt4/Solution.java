package g0901_1000.s0961_n_repeated_element_in_size_2n_array;

// #Easy #Array #Hash_Table #2022_03_31_Time_1_ms_(87.33%)_Space_54.1_MB_(66.98%)

import java.util.HashSet;
import java.util.Set;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums != null;
//@ requires nums.length % 2 == 0;
//@ requires (\exists int n; 2 <= n && nums.length == 2 * n);
//@ requires (\exists int uniqueCount; uniqueCount == n + 1);
//@ requires (\exists int repeatedElement;
//@ (\exists int count; count == n &&
//@ (\forall int i; 0 <= i < nums.length;
//@ (nums[i] == repeatedElement ==> count-- > 0))));
//@ ensures (\result == repeatedElement);
    public int repeatedNTimes(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return num;
            }
        }
        return -1;
    }
}
