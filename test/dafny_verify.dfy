// Sample Dafny file to verify generated specifications
// This file tests the output format from the HAFIS Dafny backend

method Sort(arr: array?<int>) returns (r: array?<int>)
    // Generated from: "The param_arr_ type_integer_array_ is not null"
    requires arr != null
    // Generated from: "n is greater than or equal to 2 and less than or equal to 2147483647"  
    ensures r != null
{
    r := arr;
}

method RangeCheck(n: int) returns (r: bool)
    // Generated from: "n >= 2 and n <= 2147483647"
    requires (n >= 2) && (n <= 2147483647)
{
    r := true;
}

// Dafny-specific constructs from std_si_dafny.yml
method TestDafnyQuantifiers(arr: array<int>) returns (r: bool)
    // Dafny universal quantifier: forall i:int :: 0 <= i < arr.Length ==> P(i)
    requires forall i:int :: 0 <= i < arr.Length ==> arr[i] >= 0
    // Dafny existential quantifier: exists i:int :: 0 <= i < arr.Length && P(i)
    requires arr.Length > 0 && arr[0] == 0
    ensures exists i:int :: 0 <= i < arr.Length && arr[i] == 0
{
    r := true;
}

method TestDafnyOld(x: array<int>) returns (r: array<int>)
    // Dafny old value: old(x) - note: old() is meaningful for heap-modifying methods
    ensures r.Length == x.Length
    ensures forall i:int :: 0 <= i < x.Length ==> r[i] == x[i]
{
    r := x;
}

method TestDafnySequence(s: seq<int>) returns (r: seq<int>)
    // Dafny sequence length: |s|
    requires |s| > 0
    ensures |r| == |s|
{
    r := s;
}
