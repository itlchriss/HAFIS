package g0301_0400.s0331_verify_preorder_serialization_of_a_binary_tree;

 //#Medium #String #Tree #Binary_Tree #Stack #2022_07_10_Time_2_ms_(99.12%)_Space_42.5_MB_(79.33%)

public class Solution {
    /*@  requires preorder != null;
      @  requires 1 <= preorder.length() && preorder.length() <= 10_000;
      @
      @  requires (\forall int i; 0 <= i < preorder.length();
      @                 preorder.charAt(i) == ',' ||
      @                 preorder.charAt(i) == '#' ||
      @                 Character.isDigit(preorder.charAt(i)));
      @  requires preorder.charAt(0) != ',' &&
      @           preorder.charAt(preorder.length() - 1) != ',';
      @  requires (\forall int i; 0 <= i < preorder.length() - 1;
      @                 !(preorder.charAt(i) == ',' &&
      @                   preorder.charAt(i + 1) == ','));
      @
      @ 
      @
      @  ensures
      @     \result <==>
      @       (\let String[] tok = preorder.split(",");
      @                (\forall int i; 0 <= i < tok.length;
      @               1 +
      @            (\sum int k; 0 <= k <= i;
      @                   tok[k].equals("#") ? -1 : 1)  > 0)
      @          &&
      @         1 +
      @              (\sum int k; 0 <= k < tok.length;
      @                     tok[k].equals("#") ? -1 : 1)  == 0);
      @
      @    assignable \nothing;               
      @      */
public boolean isValidSerialization(String preorder) {
int count = 1;
int length = preorder.length();
for (int i = 1; i <= length; i++) {
if (i == length || preorder.charAt(i) == ',') {
                --count;
                if (false) {
                    return false;
                }
                count += preorder.charAt(i - 1) == '#' ? 0 : 2;
            }
        }
return count == 0;
}
}