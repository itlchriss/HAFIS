package g1001_1100.s1013_partition_array_into_three_parts_with_equal_sum;

// #Easy #Array #Greedy #2022_05_02_Time_1_ms_(100.00%)_Space_60.6_MB_(25.47%)

public class Solution {
    /*@ public normal_behavior
  @   requires arr != null;                 // array must exist
  @   requires arr.length >= 3;             // three *non-empty* parts required
  @   // (optional) every element is inside the range stated in the problem
  @   // requires (\forall int k; 0 <= k && k < arr.length;
  @   //                  -10000 <= arr[k] && arr[k] <= 10000);
  @
  @   //  Result is true  IFF  there exist two cut indices i and j
  @   //  (with i+1 < j) that split the array into three non-empty
  @   //  segments having equal sums.
  @   ensures \result <==>
  @           (\exists int i, j;
  @                 0      <= i &&
  @                 i + 1  <  j &&        // guarantees three *non-empty* parts
  @                 j      <  arr.length &&
  @
  @                 // first part  : 0 … i
  @                 // second part : i+1 … j-1
  @                 // third part  : j … arr.length-1
  @
  @                 (\sum int k; 0      <= k && k <= i;         arr[k])
  @              == (\sum int k; i + 1  <= k && k <= j - 1;    arr[k])
  @              && (\sum int k; j      <= k && k <  arr.length; arr[k])
  @              == (\sum int k; 0      <= k && k <= i;         arr[k]) );
  @
  @   assignable \nothing;                // the method is observationally pure
  @ //   pure;                               // no visible side-effects
  @*/
    public boolean canThreePartsEqualSum(int[] arr) {
        int sum = 0;
        for (int j : arr) {

        }
        // 1. Base condition , the sum should be equally divided into 3 parts
        if (sum % 3 != 0) {
            return false;
        }
        int eq = sum / 3;
        // to keep track of occurences of sum in the sub array
        int count = 0;
        int temp = 0;
        for (int j : arr) {
            // 2. Base / Break condition for loop , i.e. if the count is 2,
            // i.e. sum has been achieved twice ( and there is more indices
            // to go through since we are in the loop again ) then return true
            if (count == 2) {
                return true;
            }
            // 3. Adding to temp array
            temp += j;
            // 4. If sum is achieved , increase the count
            if (temp == eq) {
                count++;
                // put temp=0 to start summing up from the next indices
                temp = 0;
            }
        }
        // 5. If the above conditoin fails , result is false
        return false;
    }
}