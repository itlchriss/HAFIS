// #Easy #Top_Interview_Questions #String #Hash_Table #Counting #Queue
// #Data_Structure_I_Day_6_String #2022_07_13_Time_1_ms_(100.00%)_Space_42.9_MB_(86.44%)
// Dafny version of Solution

// requires(*The length of the string parameter `s` is less than or equal to 100000 and is greater than or equal to 1.*);
// requires(*The string parameter `s` consists of only lowercase English letters.*);
// ensures(*If there is a non-repeating character in the string parameter `s`, the integer result is the index of the first occurrence of that character.*);
// ensures(*If there is no non-repeating character in the string parameter `s`, the integer result is -1.*);
    method firstUniqChar(s: string) returns (result: int)
    {
        var ans: int := 2147483647;
        // for (char i = 'a'; i <= 'z'; i++)
            var ind: int := s.indexOf(i);
            if ind != -1 && ind == s.lastIndexOf(i) {
                ans := min(ans, ind);
            }
        if ans == 2147483647 {
            result := -1;
            return;
        }
        result := ans;
        return;
    }
