package g0201_0300.s0229_majority_element_ii;

// #Medium #Array #Hash_Table #Sorting #Counting
// #2022_07_04_Time_2_ms_(92.96%)_Space_50.2_MB_(35.08%)

import java.util.ArrayList;
import java.util.List;

public class Solution {
    /*@ public normal_behavior
  @   // --- PRECONDITIONS (the given constraints) ----------------------------
  @   requires nums != null;
  @   requires 1  <= nums.length && nums.length <= 50000;
  @   requires (\forall int i; 0 <= i && i < nums.length;
  @                     -1000000000 <= nums[i] && nums[i] <= 1000000000);
  @
  @   // --- POSTCONDITIONS ---------------------------------------------------
  @   ensures \result != null;
  @
  @   // 1.  Every element contained in the result occurs more than ⌊n/3⌋ times.
  @   ensures (\forall int k; 0 <= k && k < \result.size();
  @              (\num_of int i; 0 <= i && i < nums.length && 
  @                                nums[i] == ((Integer)\result.get(k)).intValue(); 1)
  @              > nums.length / 3);
  @
  @   // 2.  Completeness:  each value that occurs more than ⌊n/3⌋ times is present
  @   //     in the result list.
  @   ensures (\forall int v;
  @              (\num_of int i; 0 <= i && i < nums.length && nums[i] == v; 1)
  @              > nums.length / 3
  @              ==> \result.contains(new Integer(v)));
  @
  @   // 3.  No duplicates in the resulting list.
  @   ensures (\forall int p, q;
  @              0 <= p && p < \result.size() &&
  @              0 <= q && q < \result.size() &&
  @              p != q ==> !\result.get(p).equals(\result.get(q)));
  @
  @   // 4.  There can be at most two such majority-elements.
  @   ensures \result.size() <= 2;
  @
  @   // --- FRAME CONDITION ---------------------------------------------------
  @   assignable \nothing;           //  The method is observationally pure.
  @*/
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
            if (temp == second) {
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