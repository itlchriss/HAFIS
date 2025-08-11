package g0001_0100.s0008_string_to_integer_atoi;

// #Medium #Top_Interview_Questions #String #2024_01_04_Time_1_ms_(100.00%)_Space_42.7_MB_(8.86%)

public class Solution {
    /*@ public pure model int skipSpaces(String s);
      @
      @  //  FIRST position that is NOT a space; s.length()  if none exists
      @  assignable \nothing;
      @  ensures 0 <= \result && \result <= s.length();
      @  ensures (\forall int i; 0 <= i && i < \result; s.charAt(i) == ' ');
      @  ensures \result == s.length() ==> true;                       // all spaces
      @  ensures \result <  s.length() ==> s.charAt(\result) != ' ';
      @*/

    /*@ public pure model boolean isSign(String s,int i);
      @   assignable \nothing;
      @   requires 0 <= i && i < s.length();
      @   ensures  \result <==> (s.charAt(i) == '+' || s.charAt(i) == '-');
      @*/

    /*@ public pure model boolean isMinus(String s,int i);
      @   assignable \nothing;
      @   requires 0 <= i && i < s.length();
      @   ensures  \result <==> (s.charAt(i) == '-');
      @*/

    /*@ public pure model \bigint digitsAsBig(String s,int from);
      @
      @   //  Reads the longest prefix of decimal digits that starts at
      @   //  position ‘from’.  Returns the mathematical (non-negative)
      @   //  value represented by that prefix, or 0 if the first char
      @   //  is already a non-digit.
      @
      @   assignable \nothing;
      @
      @   requires 0 <= from && from <= s.length();
      @
      @   ensures
      @     (\forall int k; from <= k && k < s.length();
      @        !Character.isDigit(s.charAt(k))
      @        ==> (\result == 0) );                   // no leading digit
      @
      @   ensures
      @     (\exists int end; from <= end && end <= s.length();
      @        (\forall int j; from <= j && j < end; Character.isDigit(s.charAt(j))) &&
      @        (end == s.length() || !Character.isDigit(s.charAt(end)))
      @        ==> \result ==
      @              (\sum int j; from <= j && j < end;
      @                    ((\bigint)(s.charAt(j) - '0'))
      @                    * (\pow 10 (end - j - 1)) ) );
      @
      @*/

    /*@ public pure model int clampToInt(\bigint v);
      @   assignable \nothing;
      @   ensures \result == (v < Integer.MIN_VALUE ? Integer.MIN_VALUE
      @                     : v > Integer.MAX_VALUE ? Integer.MAX_VALUE
      @                     : (int) v);
      @*/

    /*@
      @ public normal_behavior
      @
      @   //  PRE–CONDITIONS
      @   requires s != null;                 // the library never returns null
      @   requires s.length() <= 200;         // problem constraint
      @
      @   //  POST–CONDITIONS
      @   ensures Integer.MIN_VALUE <= \result && \result <= Integer.MAX_VALUE;
      @
      @   //  Let us describe the algorithm in a single mathematical term.
      @   //
      @   ensures \result ==
      @           ( \let  int   p      = skipSpaces(s);               // step 1
      @             ( p == s.length() ?                              // nothing but spaces?
      @                  0                                             // -> finished (rules 3+7)
      @                : (\let boolean neg   = isMinus (s,p);        // step 2
      @                   \let int     q     = p + (isSign(s,p)?1:0); // first digit position
      @                   \let \bigint raw   = digitsAsBig(s,q);      // step 3+4  (possibly 0)
      @                   \let \bigint val   = (neg ? -raw : raw);    // step 5
      @
      @                   clampToInt(val) )                           // step 6
      @             )
      @           );
      @
      @*/
    public int myAtoi(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int i = 0;
        boolean negetiveSign = false;
        char[] input = str.toCharArray();        
        //@ loop_invariant 0 <= i <= input.length;
        while (i < input.length && input[i] == ' ') {
            i++;
        }
        if (i == input.length) {
            return 0;
        } else if (input[i] == '+') {
            i++;
        } else if (input[i] == '-') {
            i++;
            negetiveSign = true;
        }
        int num = 0;
        //@ loop_invariant 0 <= i <= input.length;
        //@ decreases input.length - i;
        while (i < input.length && input[i] <= '9' && input[i] >= '0') {
            // current char
            int tem = input[i] - '0';
            tem = negetiveSign ? -tem : tem;
            // avoid invalid number like 038
            if (num == 0 && tem == '0') {
                i++;
            } else if (num == Integer.MIN_VALUE / 10 && tem <= -8 || num < Integer.MIN_VALUE / 10) {
                return Integer.MIN_VALUE;
            } else if (num == Integer.MAX_VALUE / 10 && tem >= 7 || num > Integer.MAX_VALUE / 10) {
                return Integer.MAX_VALUE;
            } else {
                num = num * 10 + tem;
                i++;
            }
        }
        return num;
    }
}