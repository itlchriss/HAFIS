// s0066 - Plus One
// Dafny formal specification with verified implementation

// requires(*The length of the integer array parameter `digits` is greater than or equal to 1 and is less than or equal to 100.*);
// requires(*All values in the integer array parameter `digits` are greater than or equal to 0 and are less than or equal to 9.*);
// requires(*The integer array parameter `digits` does not contain leading zeros.*);
// requires(*The integer array parameter `digits` is not equal to the null literal.*);
// ensures(*The integer array result is not equal to the null literal.*);
// ensures(*The integer array result represents the integer formed by adding 1 to the integer represented by the integer array parameter `digits`.*);
// ensures(*If the integer array parameter `digits` is equal to [1,2,3], the integer array result is equal to [1,2,4].*);
// ensures(*If the integer array parameter `digits` is equal to [4,3,2,1], the integer array result is equal to [4,3,2,2].*);
// ensures(*If the integer array parameter `digits` is equal to [0], the integer array result is equal to [1].*);
// ensures(*If the integer array parameter `digits` is equal to [9], the integer array result is equal to [1,0].*);
method plusOne(digits: array<int>) returns (result: array<int>)
{
    var n := digits.Length;
    var res := new int[n];
    var ci := 0;
    while ci < n
        invariant 0 <= ci <= n
        invariant forall k: nat :: k < ci ==> res[k] == digits[k]
    {
        res[ci] := digits[ci];
        ci := ci + 1;
    }
    assert forall k: nat :: k < n ==> res[k] == digits[k];

    // Simple case: last digit < 9, no carry needed
    if digits[n - 1] < 9 {
        res[n - 1] := digits[n - 1] + 1;
        result := res;
        return;
    }

    // Find the rightmost non-9 digit
    var p := n - 1;
    while p >= 0 && digits[p] == 9
        invariant -1 <= p < n
        invariant forall k: nat :: p < k < n ==> digits[k] == 9
    {
        p := p - 1;
    }

    if p < 0 {
        // All digits are 9: result is [1, 0, 0, ..., 0]
        var ans := new int[n + 1];
        ans[0] := 1;
        var j := 1;
        while j <= n
            invariant 1 <= j <= n + 1
            invariant ans[0] == 1
            invariant forall k: nat :: 1 <= k < j ==> ans[k] == 0
            invariant forall k: nat :: k < j ==> 0 <= ans[k] <= 9
        {
            ans[j] := 0;
            j := j + 1;
        }
        result := ans;
    } else {
        // digits[p] < 9, digits[p+1..n-1] are all 9
        // Result: digits[0..p-1] unchanged, digits[p]+1, then all 0s
        res[p] := digits[p] + 1;
        assert 1 <= res[p] <= 9;  // digits[p] < 9 so digits[p]+1 in [1..9]
        var k := p + 1;
        while k < n
            invariant p + 1 <= k <= n
            invariant forall i: nat :: p + 1 <= i < k ==> res[i] == 0
            invariant forall i: nat :: i < p ==> res[i] == digits[i]
            invariant forall i: nat :: i < k ==> 0 <= res[i] <= 9
            invariant res[p] == digits[p] + 1
        {
            res[k] := 0;
            k := k + 1;
        }
        result := res;
    }
}
