package g0301_0400.s0390_elimination_game;

// #Medium #Math #Recursion #2022_07_13_Time_4_ms_(81.16%)_Space_42_MB_(71.14%)

public class Solution {
    /*@ 
  @  public pure model static int lastRemainingSpec(int n) {
  @  return (n == 1)
  @              ? 1
  @                : 2 * ( 1 + n/2 - lastRemainingSpec(n/2) );
  @  }
  @*/

/*@ 
  @  public normal_behavior
  @    requires 1 <= n && n <= 1000000000;
  @    ensures  \result == lastRemainingSpec(n);
  @    assignable \nothing;
  @*/
  public int lastRemaining(int n) {
    return n == 1 ? 1 : 2 * (n % 2 - lastRemaining(n % 2) + 1);
  }
}