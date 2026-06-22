ghost function sum_array_elements(arr: array<int>): int
{
    var sum := 0;
    for i := 0 to arr.Length do
        sum := sum + arr[i];
    sum
}
