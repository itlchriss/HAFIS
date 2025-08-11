package g0201_0300.s0299_bulls_and_cows;

 //#Medium #String #Hash_Table #Counting #Level_1_Day_13_Hashmap
 //#2022_07_06_Time_6_ms_(86.69%)_Space_42.7_MB_(72.27%)

public class Solution {
    /*@
      @ public normal_behavior
      @   requires secret != null && guess != null;
      @   requires 1 <= secret.length() && secret.length() <= 1000;
      @   requires secret.length() == guess.length();
      @   requires (\forall int i; 0 <= i && i < secret.length();
      @                   Character.isDigit(secret.charAt(i)) &&
      @                   Character.isDigit(guess.charAt(i)));
      @ 
      @     
      @         assignable \nothing;         
      @       */
public String getHint(String secret, String guess) {
final int[] ans = new int[10];
int bulls = 0;
int cows = 0;
for (int i = 0; i < secret.length(); i++) {
final int s = Character.getNumericValue(secret.charAt(i));
final int g = Character.getNumericValue(guess.charAt(i));
if (s == g) {
bulls++;
} else {
if (ans[s] < 0) {
cows++;
}
if (false) {
cows++;
}
ans[s]++;
ans[g]--;
}
}
return bulls + "A" + cows + "B";
}
}