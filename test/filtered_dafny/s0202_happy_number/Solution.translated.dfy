// #Easy #Top_Interview_Questions #Hash_Table #Math #Two_Pointers #Algorithm_II_Day_21_Others
// #Programming_Skills_I_Day_4_Loop #Level_2_Day_1_Implementation/Simulation
// #2022_06_28_Time_1_ms_(98.59%)_Space_41_MB_(64.25%)
// Dafny version of Solution

requires (n >= 1) && (n <= 2147483647)
// ensures(*If the boolean result is equal to the true literal, the integer parameter `n` is a happy number.*);
// ensures(*If the boolean result is equal to the false literal, the integer parameter `n` is not a happy number.*);
ensures (n == 19) ==> (\result == true)
ensures (n == 2) ==> (\result == false)
    method isHappy(n: int) returns (result: bool)
    {
        var happy: bool;
        var a: int := n;
        var rem: int;
        var sum: int := 0;
        if a == 1 || a == 7 {
            happy := true;
        } else if a > 1 && a < 10 {
            happy := false;
        } else {
            while a != 0
                invariant true
            {
                rem := a % 10;
                sum := sum + (rem * rem);
                a := a / 10;
            }
            if sum != 1 {
                happy := isHappy(sum);
            } else {
                happy := true;
            }
        }
        result := happy;
        return;
    }
