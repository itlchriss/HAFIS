// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #String #Stack
// #Data_Structure_I_Day_9_Stack_Queue #Udemy_Strings #Big_O_Time_O(n)_Space_O(n)
// #2023_08_09_Time_2_ms_(90.49%)_Space_40.1_MB_(98.14%)
// Dafny version of Solution

// requires(*The length of the string parameter `s` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*The string parameter `s` consists of only the characters `'('`, `')'`, `'{'`, `'}'`, `'['`, and `']'`.*);
// ensures(*The boolean result is true if the input string is valid according to the given conditions, and false otherwise.*);
// ensures(*If the string parameter `s` is equal to "()", the boolean result is true.*);
// ensures(*If the string parameter `s` is equal to "()[]{}", the boolean result is true.*);
// ensures(*If the string parameter `s` is equal to "(]", the boolean result is false.*);
// ensures(*If the string parameter `s` is equal to "([)]", the boolean result is false.*);
// ensures(*If the string parameter `s` is equal to "{[]}", the boolean result is true.*);
    method isValid(s: string) returns (result: bool)
    {
        var stack: seq<char> := [];
        // invariant 0 <= i <= s.length();
        for i := 0 to |s|()
            invariant i >= 0
            invariant i <= |s|()
        {
            var c: char := s[i];
            if  {
                stack := stack + [c];
            } else if c == ' {
                stack := stack[0..|stack|-1];
            } else if c == '}' && !|stack| == 0 && stack[|stack|-1] == '{' {
                stack := stack[0..|stack|-1];
            } else if c == ']' && !|stack| == 0 && stack[|stack|-1] == '[' {
                stack := stack[0..|stack|-1];
            } else {
                result := false;
                return;
            }
            result := |stack| == 0;
            return;
        }
    }
