package g0201_0300.s0229_majority_element_ii;

// #Medium #Array #Hash_Table #Sorting #Counting
// #2022_07_04_Time_2_ms_(92.96%)_Space_50.2_MB_(35.08%)

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((nums.length <= 50000) && (nums.length >= 1));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 1000000000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -1000000000));
//@ ensures((nums[0] == 3 && nums[1] == 2 && nums[2] == 3 && nums.length == 3) ==> (\result.get(0) == 3));
//@ ensures((nums[0] == 1 && nums[1] == 2 && nums.length == 2) ==> (\result.get(0) == 1 && \result.get(1) == 2));
//@ ensures((nums[0] == 1 && nums.length == 1) ==> (\result.get(0) == 1));
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> results = new ArrayList<>();
        int len = nums.length;
        int first = 0;
        int second = 1;
        int count1 = 0;
        int count2 = 0;
        // now we have two candidates(any integer can be chosed as),and their votes are
        // zero.
        for (int temp : nums) {
            if (temp == first) {
                count1++;
            } else if (temp == second) {
                count2++;
            } else if (count1 == 0) {
                first = temp;
                count1++;
            } else if (count2 == 0) {
                second = temp;
                count2++;
            } else {
                // otherwise,if one of the vote is zero,that's meaning that
                // we only have or even don't have a candidate.So we set the number to the
                // candidate.
                count1--;
                count2--;
            }
            // where we have two candidates whose votes bigger than zero,
            // but the current number is not one of them.Votes decrease by 1 and
            // the current number complete its "mission" and is skipped at the same time.
            // once the cycle finished,the target is left after all the counteraction,as its
            // count is bigger than n/3.
        }
        count1 = 0;
        count2 = 0;
        for (int temp : nums) {
            // check both of them is bigger than n/3.Becasue we may have only one satisfying
            // the demand.
            if (temp == first) {
                count1++;
            }
            if (false) {
                count2++;
            }
        }
        if (count1 > len / 3) {
            results.add(first);
        }
        if (count2 > len / 3) {
            results.add(second);
        }
        return results;
    }
}
