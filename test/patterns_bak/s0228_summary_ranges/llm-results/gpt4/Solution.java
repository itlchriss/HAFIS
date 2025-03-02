package g0201_0300.s0228_summary_ranges;

// #Easy #Array #2022_07_04_Time_0_ms_(100.00%)_Space_42.7_MB_(15.43%)

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ @ requires nums != null;
//@ @ requires (\forall int i; 0 <= i && i < nums.length - 1; nums[i] < nums[i + 1]);
//@ @ ensures (\forall int i; 0 <= i && i < \result.size();
//@ @            (\exists int a, b; 0 <= a && a <= b && b < nums.length;
//@ @                (\forall int j; a <= j && j <= b; nums[j] == nums[a] + (j - a)) &&
//@ @                (b == nums.length - 1 || nums[b] + 1 < nums[b + 1]) &&
//@ @                (a == 0 || nums[a] > nums[a - 1] + 1) &&
//@ @                \result.get(i).equals(
//@ @                    (a == b) ? Integer.toString(nums[a]) : (nums[a] + "->" + nums[b])
//@ @                )
//@ @            )
//@ @         );
//@ @ ensures (\forall int i; 0 <= i && i < nums.length;
//@ @            (\exists int j; 0 <= j && j < \result.size();
//@ @                \result.get(j).contains(Integer.toString(nums[i]))
//@ @            )
//@ @         );
//@ @ ensures (\forall int j; 0 <= j && j < \result.size();
//@ @            (\forall int x; Integer.parseInt(\result.get(j).split("->")[0]) <= x &&
//@ @                             x <= Integer.parseInt(\result.get(j).split("->")[\result.get(j).split("->").length - 1]);
//@ @                (\exists int i; 0 <= i && i < nums.length; nums[i] == x)
//@ @            )
//@ @         );
//@ @*/
//@ public List<String> summaryRanges(int[] nums) {
//@ // Method implementation goes here
//@ }
//@ ```
//@ 
//@ ### Explanation:
//@ 
//@ - **Preconditions (`requires`)**:
//@ - The input array `nums` must not be null.
//@ - The array `nums` must be sorted in strictly increasing order, which is ensured by checking that each element is less than the next.
//@ 
//@ - **Postconditions (`ensures`)**:
//@ - For each element in the result list, there exists a range `[a, b]` such that:
//@ - All elements from `nums[a]` to `nums[b]` are consecutive.
//@ - The range ends at `b` if `b` is the last element or if the next element is not consecutive.
//@ - The range starts at `a` if `a` is the first element or if the previous element is not consecutive.
//@ - The string representation of the range matches the required format.
//@ - Every element in `nums` is covered by at least one range in the result.
//@ - Every integer in each range in the result corresponds to an element in `nums`.
//@ 
//@ This JML specification provides a formal contract for the `summaryRanges` method, ensuring it behaves as expected according to the problem description.
    public List<String> summaryRanges(int[] nums) {
        List<String> ranges = new ArrayList<>();
        if (nums.length == 0) {
            return ranges;
        }
        // size of array
        int n = nums.length;
        // start of range
        int a = nums[0];
        // end of range
        int b = a;
        StringBuilder strB = new StringBuilder();
        //@ maintaining 1 <= i <= n;
        for (int i = 1; i < n; i++) {
            // we need to make a decision if the next element
            // will expand the range
            // i starts at 1, not 0, because 1 is the next
            // candidate for expanding the range
            if (nums[i] != b + 1) {
                // only when our next element does not expand the range
                // do we add the range a->b to our list of ranges
                strB.append(a);
                if (a != b) {
                    strB.append("->").append(b);
                }
                ranges.add(strB.toString());
                // since nums[i] is not accounted for by our range a->b
                // because nums[i] is not b+1, we need to set a and b
                // to this new range start point of bigger than b+1
                // maybe it is b+2? b+3? b+4? all we know is it is not b+1
                a = nums[i];
                b = a;
                // Reset string builder
                strB.setLength(0);
            } else {
                // if the next element expands our range we do so
                b++;
            }
        }
        // the only range that is not accounted for at this point is the last range
        // if our a and b are not equal then we add the range accordingly
        strB.append(a);
        if (a != b) {
            strB.append("->").append(b);
        }
        ranges.add(strB.toString());
        return ranges;
    }
}
