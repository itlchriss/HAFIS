// Test file with multiple methods and multiple annotations
// This tests the table-based annotation tracking system

method TwoSum(arr: array?<int>, target: int) returns (i: int, j: int)
    // requires(*arr is not null*);
    // ensures(*the value of i is greater than or equal to 0*);
    // ensures(*the value of j is less than the length of arr*);
{
    i := 0;
    j := 0;
    // assert(*i is less than j*);
    var x := i + j;
}

method RangeCheck(n: int) returns (r: bool)
    // requires(*n is greater than or equal to 2*);
    // requires(*n is less than or equal to 2147483647*);
    // ensures(*r is true*);
{
    r := true;
}

method ProcessArray(data: array<int>) returns (sum: int)
    // requires(*data is not null*);
    // ensures(*sum is greater than or equal to 0*);
{
    sum := 0;
    var idx := 0;
    while idx < data.Length
    {
        // assert(*idx is greater than or equal to 0*);
        // assert(*idx is less than the length of data*);
        sum := sum + data[idx];
        idx := idx + 1;
    }
}
