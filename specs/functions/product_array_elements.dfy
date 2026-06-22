ghost function product_array_elements(arr: array<int>): int
    requires arr != null
    requires arr.Length > 0
{
    var p := 1;
    for i := 0 to arr.Length {
        p := p * arr[i];
    }
    p
}
