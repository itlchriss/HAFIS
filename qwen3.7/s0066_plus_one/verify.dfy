// s0066 - Plus One
// Dafny formal specification with verified implementation

method plusOne(digits: array<int>) returns (result: array<int>)
    requires 1 <= digits.Length <= 100
    requires forall i: nat :: i < digits.Length ==> 0 <= digits[i] <= 9
    ensures result.Length == digits.Length || result.Length == digits.Length + 1
    ensures forall i: nat :: i < result.Length ==> 0 <= result[i] <= 9
    ensures digits[digits.Length - 1] < 9 ==>
        (result.Length == digits.Length &&
         result[result.Length - 1] == digits[digits.Length - 1] + 1 &&
         (forall i: nat :: i < result.Length - 1 ==> result[i] == digits[i]))
    ensures digits[digits.Length - 1] >= 9 && (forall i: nat :: i < digits.Length ==> digits[i] == 9) ==>
        (result.Length == digits.Length + 1 && result[0] == 1 &&
         (forall i: nat :: 1 <= i < result.Length ==> result[i] == 0))
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
