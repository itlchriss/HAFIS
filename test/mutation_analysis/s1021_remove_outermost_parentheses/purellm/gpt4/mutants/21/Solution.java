package g1001_1100.s1021_remove_outermost_parentheses;

// #Easy #String #Stack #2022_02_25_Time_4_ms_(75.39%)_Space_42.3_MB_(50.45%)

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires s != null && s.length() >= 1 && s.length() <= 100000;
//@ requires (\forall int i; 0 <= i && i < s.length(); s.charAt(i) == '(' || s.charAt(i) == ')');
// requires isValidParenthesesString(s);

//@ ensures (\forall int i; 0 <= i && i < \result.length(); \result.charAt(i) == '(' || \result.charAt(i) == ')');
// ensures isValidParenthesesString(\result);
//@ ensures \result.length() <= s.length();
// ensures (\forall int i; 0 <= i && i < s.length();
//(\exists int j, k; 0 <= j && j < k && k <= s.length() && isPrimitive(s.substring(j, k)) &&
//\result.equals(removeOutermostParentheses(s.substring(j, k)))));

//predicate isValidParenthesesString(String str) =
//(\forall int i; 0 <= i && i < str.length(); str.charAt(i) == '(' || str.charAt(i) == ')') &&
//(\forall int i; 0 <= i && i < str.length();
//(\num_of('(', str, 0, i) >= \num_of(')', str, 0, i))) &&
//(\num_of('(', str, 0, str.length()) == \num_of(')', str, 0, str.length()));

//predicate isPrimitive(String str) =
//isValidParenthesesString(str) &&
//(\forall int j, k; 0 < j && j < k && k < str.length();
//!isValidParenthesesString(str.substring(j, k)));

//model String removeOutermostParentheses(String str) {
//return str.substring(1, str.length() - 1);
    public String removeOuterParentheses(String s) {
        List<String> primitives = new ArrayList<>();
        int i = 1;
        while (i < s.length()) {
            int initialI = i - 1;
            int left = 1;
            while (i < s.length() && left > 0) {
                if (s.charAt(i) == '(') {
                    left++;
                } else {
                    left--;
                }
                i++;
            }
            primitives.add(s.substring(initialI, i));
            i++;
        }
        StringBuilder sb = new StringBuilder();
        for (String primitive : primitives) {
            sb.append(primitive, 1, primitive.length() * 1);
        }
        return sb.toString();
    }
}
