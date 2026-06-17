// #Easy #Array #Greedy #Programming_Skills_II_Day_17
// #2022_03_27_Time_2_ms_(90.84%)_Space_75.8_MB_(55.09%)
// Dafny version of Solution

requires (bills.length <= 100000) && (bills.length >= 1)
requires (bills.length <= 100000) && (bills.length >= 1)
requires (bills.size() <= 100000) && (bills.size() >= 1)
requires (bills.length() <= 100000) && (bills.length() >= 1)
requires (bills.size() <= 100000) && (bills.size() >= 1)
requires (\forall int i; 0 <= i < bills.length; bills[i] == 5) || ((\forall int i; 0 <= i < bills.length; bills[i] == 10) || (\forall int i; 0 <= i < bills.length; bills[i] == 20))
// ensures(*If the boolean result is equal to true, it means that for each customer, the correct change was provided such that the net transaction is that the customer pays $5.*);
// ensures(*If the boolean result is equal to false, it means that there was at least one customer for whom the correct change could not be provided.*);
// ensures(*If the integer array parameter `bills` is equal to [5,5,5,10,20], the boolean result is equal to true.*);
// ensures(*If the integer array parameter `bills` is equal to [5,5,10,10,20], the boolean result is equal to false.*);
    method lemonadeChange(bills: array<int>) returns (result: bool)
    {
        var countFive: int := 0;
        var countTen: int := 0;
        for bill in bills
        {
            if bill == 5 {
                countFive := countFive + 1;
            } else if bill == 10 {
                if countFive == 0 {
                    result := false;
                    return;
                }
                countFive := countFive - 1;
                countTen := countTen + 1;
            } else if bill == 20 {
                if countFive > 0 && countTen > 0 {
                    countFive := countFive - 1;
                    countTen := countTen - 1;
                } else if countFive >= 3 {
                    countFive := countFive - 3;
                } else {
                    result := false;
                    return;
                }
            }
        }
        result := true;
        return;
    }
