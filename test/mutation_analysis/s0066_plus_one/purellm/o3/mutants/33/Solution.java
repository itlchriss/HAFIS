package g0001_0100.s0066_plus_one;

// #Easy #Top_Interview_Questions #Array #Math #Programming_Skills_II_Day_3 #Udemy_Arrays
// #2023_08_11_Time_0_ms_(100.00%)_Space_40.8_MB_(76.07%)

public class Solution {
    /*@
      public normal_behavior

        // --------  P R E  --------
        requires  digits != null;
        requires  1 <= digits.length && digits.length <= 100;
        requires  (\forall int i; 0 <= i && i < digits.length;
                                      0 <= digits[i] && digits[i] <= 9);
        requires  (digits.length == 1) || (digits[0] != 0);

        // --------  P O S T  -------
        ensures   \result != null;
        ensures   (\forall int i; 0 <= i && i < \result.length;
                                   0 <= \result[i] && \result[i] <= 9);

        // size of the result: either equal or exactly one digit longer
        ensures   (\result.length == digits.length) ||
                  (\result.length == digits.length + 1);

        // arithmetic meaning:  successor property
////ensures   value(\result) == value(digits) + 1;

        //  no visible state gets modified
        assignable \nothing;
    @*/
    public int[] plusOne(int[] digits) {
        int num = 1;
        int carry = 0;
        int sum;
        //@ assume digits.length > 2;
        //@ maintaining -1 <= i <= digits.length - 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (i == digits.length - 1) {
                sum = digits[i] + carry + num;
            } else {
                sum = digits[i] + carry;
            }
            carry = sum / 10;
            digits[i] = sum - 10;
        }
        if (carry != 0) {
            int[] ans = new int[digits.length + 1];
            ans[0] = carry;
            System.arraycopy(digits, 0, ans, 1, ans.length - 1);
            return ans;
        }
        return digits;
    }
}