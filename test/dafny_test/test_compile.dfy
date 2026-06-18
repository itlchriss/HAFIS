// Example Dafny file with 2 NL annotations for testing

method TwoSum(arr: array?<int>, target: int) returns (i: int, j: int)
    // requires(*arr is not null*);
{
    i := 0;
    j := 0;
}

method RangeCheck(n: int) returns (r: bool)
    // requires(*n is greater than or equal to 2*);
{
    r := true;
}
