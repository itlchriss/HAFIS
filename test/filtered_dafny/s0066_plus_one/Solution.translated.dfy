// #Easy #Top_Interview_Questions #Array #Math #Programming_Skills_II_Day_3 #Udemy_Arrays
// #2023_08_11_Time_0_ms_(100.00%)_Space_40.8_MB_(76.07%)
// Dafny version of Solution

requires (digits.length <= 100) && (digits.length >= 1)
requires (digits.length <= 100) && (digits.length >= 1)
requires (digits.size() <= 100) && (digits.size() >= 1)
requires (digits.length() <= 100) && (digits.length() >= 1)
requires (digits.size() <= 100) && (digits.size() >= 1)
requires (\forall int i; 0 <= i < digits.length; digits[i] <= 9) && (\forall int i; 0 <= i < digits.length; digits[i] >= 0)
// ensures(*The length of the integer array result is equal to the length of the integer array parameter `digits`.*);
// ensures(*If the last element of the integer array parameter `digits` is less than 9, the last element of the integer array result is equal to the last element of the integer array parameter `digits` plus 1.*);
// ensures(*If the last element of the integer array parameter `digits` is equal to 9, the last element of the integer array result is equal to 0 and the second last element of the integer array result is equal to the second last element of the integer array parameter `digits` plus 1.*);
// ensures(*If all the elements of the integer array parameter `digits` are equal to 9, the first element of the integer array result is equal to 1 and all the other elements are equal to 0.*);
    method plusOne(digits: array<int>) returns (result: array<int>)
    {
        var num: int := 1;
        var carry: int := 0;
        var sum: int;
        // invariant //@ maintaining -1 <= i <= digits.length - 1;
        var i := |digits| - 1;
        while i >= 0
            invariant true
        {
            if i == |digits| - 1 {
                sum := digits[i] + carry + num;
            } else {
                sum := digits[i] + carry;
            }
            carry := sum / 10;
            digits[i] := sum % 10;
            i := i - 1;
        }
        if carry != 0 {
            var ans := new int[|digits| + 1];
            ans[0] := carry;
            // System.arraycopy(digits, 0, ans, 1, ans.length - 1)
            result := ans;
            return;
        }
        result := digits;
        return;
    }
