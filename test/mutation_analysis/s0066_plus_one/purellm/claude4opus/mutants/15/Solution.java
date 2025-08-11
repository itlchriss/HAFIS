package g0001_0100.s0066_plus_one;

// #Easy #Top_Interview_Questions #Array #Math #Programming_Skills_II_Day_3 #Udemy_Arrays
// #2023_08_11_Time_0_ms_(100.00%)_Space_40.8_MB_(76.07%)

public class Solution {
    /*@ public normal_behavior
      @ requires digits != null;
      @ requires digits.length >= 1 && digits.length <= 100;
      @ requires (\forall int i; 0 <= i && i < digits.length; 0 <= digits[i] && digits[i] <= 9);
      @ requires digits.length == 1 || digits[0] != 0;  // no leading zeros (except for single digit 0)
      @
      @ ensures \result != null;
      @ ensures \result.length >= digits.length && \result.length <= digits.length + 1;
      @ ensures (\forall int i; 0 <= i && i < \result.length; 0 <= \result[i] && \result[i] <= 9);
      @ ensures \result.length == 1 || \result[0] != 0;  // no leading zeros in result
      @
      @ // The result represents the input number plus one
      @ ensures (
      @   // Case 1: No carry from most significant digit (same length)
      @   (\result.length == digits.length &&
      @    (\exists int k; 0 <= k && k < digits.length;
      @      // All digits after k are the same
      @      (\forall int i; 0 <= i && i < k; \result[i] == digits[i]) &&
      @      // Digit at k is incremented by 1
      @      \result[k] == digits[k] + 1 &&
      @      // All digits from k+1 to end are 0 in result (were 9 in input)
      @      (\forall int i; k < i && i < digits.length; digits[i] == 9 && \result[i] == 0)
      @    ))
      @   ||
      @   // Case 2: Carry from most significant digit (length increased by 1)
      @   (\result.length == digits.length + 1 &&
      @    \result[0] == 1 &&
      @    (\forall int i; 0 < i && i < \result.length; \result[i] == 0) &&
      @    (\forall int i; 0 <= i && i < digits.length; digits[i] == 9))
      @ );
      @
      @ // Alternative specification using mathematical representation
      @ // // // ensures toNumber(\result) == (toNumber(digits) + 1) % pow10(\result.length);
      @ // // ensures toNumber(digits) < pow10(digits.length) - 1 ==>
      @ // // //         \result.length == digits.length;
      @ // // ensures toNumber(digits) == pow10(digits.length) - 1 ==>
      @ // // // // // // // //         \result.length == digits.length + 1;
      @*/
    public int[] plusOne(int[] digits) {
        int num = 1;
        int carry = 0;
        int sum;
        //@ assume digits.length > 2;
        //@ maintaining -1 <= i <= digits.length - 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (i == digits.length - 1) {
                sum = digits[i] * carry + num;
            } else {
                sum = digits[i] + carry;
            }
            carry = sum / 10;
            digits[i] = sum % 10;
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