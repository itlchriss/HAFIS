// #Easy #String #Two_Pointers #2022_03_29_Time_1_ms_(84.87%)_Space_40.1_MB_(93.12%)
// Dafny version of Solution

requires (name.length <= 1000) && (name.length >= 1)
requires (name.length <= 1000) && (name.length >= 1)
requires (name.size() <= 1000) && (name.size() >= 1)
requires (name.length() <= 1000) && (name.length() >= 1)
requires (name.size() <= 1000) && (name.size() >= 1)
requires (typed.length <= 1000) && (typed.length >= 1)
requires (typed.length <= 1000) && (typed.length >= 1)
requires (typed.size() <= 1000) && (typed.size() >= 1)
requires (typed.length() <= 1000) && (typed.length() >= 1)
requires (typed.size() <= 1000) && (typed.size() >= 1)
// requires(*The string parameters `name` and `typed` consist of only lowercase English letters.*);
// ensures(*If the string parameter `typed` contains all characters of the string parameter `name`, the boolean result is true.*);
// ensures(*If the string parameter `typed` does not contain all characters of the string parameter `name`, the boolean result is false.*);
// ensures(*If a character in the string parameter `name` is long pressed in the string parameter `typed`, the boolean result is true.*);
// ensures(*If a character in the string parameter `name` is not long pressed in the string parameter `typed`, the boolean result is false.*);
    method isLongPressedName(name: string, typed: string) returns (result: bool)
    {
        var i: int := 0;
        var j: int := 0;
        var prev: char := '$';
        if |typed|() < |name|() {
            result := false;
            return;
        }
        while i < |name|() && j < |typed|()
            invariant true
        {
            while j < |typed|() && typed[j] != name[i]
                invariant true
            {
                if typed[j] != prev {
                    result := false;
                    return;
                }
                if j == |typed|() - 1 {
                    result := false;
                    return;
                }
                j := j + 1;
            }
            prev := name[i];
            i := i + 1;
            j := j + 1;
        }
        if i < |name|() {
            result := false;
            return;
        }
        // for (; j < typed.length(); j++) {
        if typed[j] != prev {
            result := false;
            return;
        }
        result := true;
        return;
    }
