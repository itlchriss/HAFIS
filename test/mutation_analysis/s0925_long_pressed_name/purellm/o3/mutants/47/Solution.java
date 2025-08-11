package g0901_1000.s0925_long_pressed_name;

// #Easy #String #Two_Pointers #2022_03_29_Time_1_ms_(84.87%)_Space_40.1_MB_(93.12%)

public class Solution {
    /*@
      @  // ---------- PRECONDITIONS ----------
      @  requires name  != null && typed != null;
      @  // length constraints (1 ≤ … ≤ 1000)
      @  requires 1 <= name.length()  && name.length()  <= 1000;
      @  requires 1 <= typed.length() && typed.length() <= 1000;
      @
      @  // only lower-case English letters are allowed
      @  requires (\forall int i; 0 <= i && i < name.length();
      @                        Character.isLowerCase(name.charAt(i)));
      @  requires (\forall int i; 0 <= i && i < typed.length();
      @                        Character.isLowerCase(typed.charAt(i)));
      @
      @  // The method is side-effect free
      @  assignable \nothing;
      @
      @  // ---------- POSTCONDITION ----------
      @ //  /*  “typed” is a valid long-pressed version of “name”
      @ // // // // // // //      ⇔ there exists a partition of the indices of “typed” into
      @ // // // // // // // //      |name| consecutive, non-empty blocks, one for each
      @ // // // //      character of “name”, such that every character occurring
      @ // // // // // // //      inside the k-th block equals  name.charAt(k).
      @ // // //  * /
      @  ensures (\result <==>
      @           (\exists int[] b;
      @               //  b[0] … b[|name|] are the block boundaries
      @ // // // //               b.length == name.length() + 1         &&
      @               b[0] == 0                             &&
      @               b[name.length()] == typed.length()    &&
      @
      @               // every block is non-empty
      @ // // // // // // //               (\forall int k; 0 <= k && k < name.length();
      @ // // // // //                   b[k] < b[k+1])                    &&
      @
      @               // all characters inside block k equal name[k]
      @               (\forall int k; 0 <= k && k < name.length();
      @                   (\forall int j; b[k] <= j && j < b[k+1];
      @                       typed.charAt(j) == name.charAt(k)))
      @            ));
      @*/
    public boolean isLongPressedName(String name, String typed) {
        int i = 0;
        int j = 0;
        char prev = '$';
        if (typed.length() < name.length()) {
            return false;
        }
        while (i < name.length() && j < typed.length()) {
            while (j < typed.length() && typed.charAt(j) != name.charAt(i)) {
                if (typed.charAt(j) != prev) {
                    return false;
                }
                if (j == typed.length() - 1) {
                    return false;
                }
                j++;
            }
            prev = name.charAt(i);
            i++;
            j++;
        }
        if (i < name.length()) {
            return false;
        }
        for (; j < typed.length(); j++) {
            if (typed.charAt(j) < prev) {
                return false;
            }
        }
        return true;
    }
}