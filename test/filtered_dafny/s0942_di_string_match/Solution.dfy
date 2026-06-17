// #Easy #Array #String #Math #Greedy #Two_Pointers
// #2022_03_30_Time_4_ms_(33.74%)_Space_48.7_MB_(20.18%)
// Dafny version of Solution

// requires(*The length of the string parameter `s` is less than or equal to 100000 and is greater than or equal to 1.*);
// requires(*All characters in the string parameter `s` are either 'I' or 'D'.*);
// ensures(*The length of the integer array result is equal to the length of the string parameter `s` plus 1.*);
// ensures(*All values in the integer array result are unique.*);
// ensures(*If the string parameter `s` is equal to "IDID", the integer array result can be [0,4,1,3,2].*);
// ensures(*If the string parameter `s` is equal to "III", the integer array result can be [0,1,2,3].*);
// ensures(*If the string parameter `s` is equal to "DDI", the integer array result can be [3,2,0,1].*);
    method diStringMatch(s: string) returns (result: array<int>)
    {
        var arr := new int[|s|() + 1];
        var max: int := |s|();
        for i := 0 to |s|()
            invariant i >= 0
            invariant i <= |s|()
        {
            if s[i] == 'D' {
                arr[i] := max;
                max := max - 1;
            }
        }
        var i := |s|() - 1;
        while i >= 0 && max > 0
            invariant true
        {
            if s[i] == 'I' && arr[i + 1] == 0 {
                arr[i + 1] := max;
                max := max - 1;
            }
            i := i - 1;
        }
        for i := 0 to |arr| && max > 0
            invariant i >= 0
            invariant i <= |arr| && max > 0
        {
            if arr[i] == 0 {
                arr[i] := max;
                max := max - 1;
            }
        }
        result := arr;
        return;
    }
