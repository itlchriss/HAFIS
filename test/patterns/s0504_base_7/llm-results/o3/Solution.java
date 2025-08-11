package g0501_0600.s0504_base_7;

// #Easy #Math #2022_07_24_Time_0_ms_(100.00%)_Space_39.4_MB_(98.67%)

public class Solution {
    /*@
      @ public model pure static int valueOfBase7(String s);
      @
      @ requires s != null
      @      && s.length() > 0
      @      && (\forall int i; 0 <= i && i < s.length();
      @              (i == 0 && s.charAt(i) == '-')      // optional leading ‘-’
      @           || ('0' <= s.charAt(i) && s.charAt(i) <= '6'));
      @
      @ ensures (\result ==                                            // sign
      @              (s.charAt(0) == '-' ? -1 : 1)
      @            * (\sum int k;                                      // positional value
      @                    (s.charAt(0) == '-' ? 1 : 0) <= k
      @                 && k < s.length();
      @                 (s.charAt(k) - '0')
      @               * (int)Math.pow(7, (s.length() - 1 - k))));
      @*/
    public String convertToBase7(int num) {
        return Integer.toString(num, 7);
    }
}