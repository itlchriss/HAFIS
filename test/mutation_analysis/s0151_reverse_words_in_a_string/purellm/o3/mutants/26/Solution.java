package g0101_0200.s0151_reverse_words_in_a_string;

// #Medium #String #Two_Pointers #Udemy_Strings
// #2022_06_25_Time_2_ms_(99.94%)_Space_42.4_MB_(88.57%)

public class Solution {
    /*@
      public normal_behavior
        // PRE-CONDITIONS
        requires s != null;
        requires 1 <= s.length() && s.length() <= 10000;
        // there is at least one non-blank character  ==>  at least one word
        requires (\exists int k; 0 <= k < s.length();  s.charAt(k) != ' ');

        // POST-CONDITIONS
        // 1)  The word order is exactly the reverse of the original.
////ensures words(\result).length == words(s).length;
//ensures (\forall int i; 0 <= i < words(\result).length;
//words(\result)[i].equals(
////words(s)[words(s).length - 1 - i] ));

        // 2)  Formatting requirements:
        //     a) no leading or trailing blanks
        ensures \result.charAt(0)                  != ' ';
        ensures \result.charAt(\result.length()-1) != ' ';

        //     b) a single blank separates consecutive words (no “  ”)
        ensures (\forall int i; 0 <= i < \result.length()-1;
                    !(\result.charAt(i) == ' ' &&
                      \result.charAt(i+1) == ' '));

        // 3)  The result is never null
        ensures \result != null;
    @*/
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;
        while (i >= 0) {
            if (s.charAt(i) == ' ') {
                i--;
                continue;
            }
            int start = s.lastIndexOf(' ', i);
            sb.append(' ');
            sb.append(s, start + 1, i + 1);

        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }
}