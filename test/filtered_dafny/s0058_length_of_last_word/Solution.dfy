// #Easy #String #Programming_Skills_II_Day_6 #Udemy_Arrays
// #2023_08_11_Time_0_ms_(100.00%)_Space_40.3_MB_(97.60%)
// Dafny version of Solution

// requires(*The length of the string parameter `s` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*The string parameter `s` consists of only English letters and spaces ' '.*);
// requires(*There will be at least one word in the string parameter `s`.*);
// ensures(*The integer result is less than or equal to the length of the last word in the string parameter `s`.*);
// ensures(*If the string parameter `s` is equal to "Hello World", the integer result is equal to 5.*);
// ensures(*If the string parameter `s` is equal to " fly me to the moon ", the integer result is equal to 4.*);
// ensures(*If the string parameter `s` is equal to "luffy is still joyboy", the integer result is equal to 6.*);
    method lengthOfLastWord(s: string) returns (result: int)
    {
        var len: int := 0;
        var i := |s|() - 1;
        while i >= 0
            invariant true
        {
            var ch: char := s[i];
            if ch == ' ' && len > 0 {
                break;
            } else if ch != ' ' {
                len := len + 1;
            }
            i := i - 1;
        }
        result := len;
        return;
    }
