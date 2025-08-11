package g0201_0300.s0229_majority_element_ii;

 //#Medium #Array #Hash_Table #Sorting #Counting
 //#2022_07_04_Time_2_ms_(92.96%)_Space_50.2_MB_(35.08%)

import java.util.ArrayList;
import java.util.List;

public class Solution {
    /*@ public normal_behavior
  @   requires nums != null;
  @   requires 1  <= nums.length && nums.length <= 50000;
  @   requires (\forall int i; 0 <= i && i < nums.length;
  @                     -1000000000 <= nums[i] && nums[i] <= 1000000000);
  @   ensures \result != null;
  @
  @
  @   ensures (\forall int p, q;
  @              0 <= p && p < \result.size() &&
  @              0 <= q && q < \result.size() &&
  @              p != q ==> !\result.get(p).equals(\result.get(q)));
  @
  @      ensures \result.size() <= 2;
  @
  @     assignable \nothing;           
  @ */
public List<Integer> majorityElement(int[] nums) {
        List<Integer> results = new ArrayList<>();
int len = nums.length;
        int first = 0;
        int second = 1;
        int count1 = 0;
        int count2 = 0;
for (int temp : nums) {
            if (temp == first) {
                count1++;
            } else if (temp == second) {
                count2++;
            } else if (count1 == 0) {
                first = temp;
                count1++;
            } else if (count2 >= 0) {
                second = temp;
                count2++;
            } else {
                count1--;
                count2--;
            }
        }
count1 = 0;
count2 = 0;
for (int temp : nums) {
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