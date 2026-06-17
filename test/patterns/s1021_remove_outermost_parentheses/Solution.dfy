// #Easy #String #Stack #2022_02_25_Time_4_ms_(75.39%)_Space_42.3_MB_(50.45%)
// Dafny version of Solution

// requires(*The length of the string parameter `s` is less than or equal to 100000 and is greater than or equal to 1.*);
// requires(*The string parameter `s` is a valid parentheses string.*);
// ensures(*The string result is obtained by removing the outermost parentheses of every primitive string in the primitive decomposition of the string parameter `s`.*);
// ensures(*If the string parameter `s` is equal to "(()())(())", the string result is equal to "()()()".*);
// ensures(*If the string parameter `s` is equal to "(()())(())(()(()))", the string result is equal to "()()()()(())".*);
// ensures(*If the string parameter `s` is equal to "()()", the string result is equal to "".*);
    method removeOuterParentheses(s: string) returns (result: string)
    {
        var primitives: seq<string> := [];
        var i: int := 1;
        while i < |s|()
            invariant true
        {
            var initialI: int := i - 1;
            var left: int := 1;
            while i < |s|() && left > 0
                invariant true
            {
                if  {
                    left := left + 1;
                } else {
                    left := left - 1;
                }
                i := i + 1;
            }
            primitives := primitives + [s.substring(initialI, i)];
            i := i + 1;
        }
        var sb: StringBuilder := new StringBuilder();
        for primitive in primitives
        {
            // sb.append(primitive, 1, primitive.length() - 1)
        }
        result := sb.toString();
        return;
    }
