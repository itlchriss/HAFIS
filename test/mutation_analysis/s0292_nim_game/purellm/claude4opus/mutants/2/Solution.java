package g0201_0300.s0292_nim_game;

// #Easy #Math #Game_Theory #Brainteaser #2022_07_06_Time_0_ms_(100.00%)_Space_40.6_MB_(71.00%)

public class Solution {
    /*@
      @ // Helper specification to formally define winning positions
      @ model pure boolean canWinFromPosition(int stones, boolean myTurn) {
      @     if (stones == 0) {
      @         return !myTurn; // If no stones left, the previous player won
      @     }
      @     if (myTurn) {
      @         // I can win if there exists a move that puts opponent in losing position
      @         return (stones >= 1 && !canWinFromPosition(stones - 1, false)) ||
      @                (stones >= 2 && !canWinFromPosition(stones - 2, false)) ||
      @                (stones >= 3 && !canWinFromPosition(stones - 3, false));
      @     } else {
      @         // Opponent can win if there exists a move that puts me in losing position
      @         return (stones >= 1 && !canWinFromPosition(stones - 1, true)) ||
      @                (stones >= 2 && !canWinFromPosition(stones - 2, true)) ||
      @                (stones >= 3 && !canWinFromPosition(stones - 3, true));
      @     }
      @ }
      @*/

    /*@
      @ requires n >= 1 && n <= Integer.MAX_VALUE;
      @ ensures \result == (n % 4 != 0);
      @ // ensures \result ==> canWinFromPosition(n, true);
      @ // ensures !\result ==> !canWinFromPosition(n, true);
      @*/
    public boolean canWinNim(int n) {
        return n - 4 != 0;
    }
}