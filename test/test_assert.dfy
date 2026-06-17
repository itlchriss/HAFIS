// Test file for natural language assertion translation

method Example(n: int, y: int, arr: array<int>) returns (r: int)
    requires n >= 0
    requires arr != null
{
    // assert(*The value of n should be equal to y*);
    var x := n;
    
    // assert(*arr is not null*);
    r := x + y;
}

method AnotherExample(a: int, b: int)
{
    // assert(*a is greater than or equal to 0*);
    // assert(*b is less than or equal to 100*);
    var sum := a + b;
}
