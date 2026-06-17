// Example Dafny file with NL annotations
// All annotations use the format: // keyword(*natural language*);

method TwoSum(arr: array?<int>, target: int) returns (i: int, j: int)
    // requires(*arr is not null*);
    // ensures(*the value of i is greater than or equal to 0*);
    // ensures(*the value of j is less than the length of arr*);
{
    i := 0;
    j := 0;
    // assert(*i is less than j*);
}

method RangeCheck(n: int) returns (r: bool)
    // requires(*n is greater than or equal to 2*);
    // requires(*n is less than or equal to 2147483647*);
{
    r := true;
}
