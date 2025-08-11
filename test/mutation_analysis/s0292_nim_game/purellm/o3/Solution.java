package g0201_0300.s0292_nim_game;

// #Easy #Math #Game_Theory #Brainteaser #2022_07_06_Time_0_ms_(100.00%)_Space_40.6_MB_(71.00%)

public class Solution {
    /*@  // 292. Nim Game
  @  public normal_behavior
  @    // 1 ≤ n ≤ 2^31 − 1
  @    requires 1 <= n && n <= Integer.MAX_VALUE;
  @
  @    // The first player can win  ⇔  n mod 4 ≠ 0
  @    ensures  \result == ((n % 4) != 0);
  @
  @    // The method is pure (no visible state change)
  @    assignable \nothing;
  @*/
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }
}