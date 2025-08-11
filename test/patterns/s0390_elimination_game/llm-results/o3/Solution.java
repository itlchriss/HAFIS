package g0301_0400.s0390_elimination_game;

// #Medium #Math #Recursion #2022_07_13_Time_4_ms_(81.16%)_Space_42_MB_(71.14%)

public class Solution {
    /*@  // ----------  Ghost / model function  ---------------
  @  public pure model static int lastRemainingSpec(int n) {
  @      /*  Mathematical definition taken directly from the
  @          well-known recurrence for the elimination game
  @              f(1)         = 1
  @              f(n) (n>1)   = 2 * ( 1 + n/2 – f(n/2) )
  @          The recursive call is on ⌊n/2⌋, so the function
  @          terminates for every n ≥ 1.                      *​/
  @      return (n == 1)
  @                 ? 1
  @                 : 2 * ( 1 + n/2 - lastRemainingSpec(n/2) );
  @  }
  @*/

/*@  // ----------  Contract for the real method ----------
  @  public normal_behavior
  @    requires 1 <= n && n <= 1000000000;
  @    // The result is exactly the mathematically specified value.
  @    ensures  \result == lastRemainingSpec(n);
  @    // The method is side-effect free (pure from the caller’s view).
  @    assignable \nothing;
  @*/
    public int lastRemaining(int n) {
        return n == 1 ? 1 : 2 * (n / 2 - lastRemaining(n / 2) + 1);
    }
}