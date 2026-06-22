ghost function max_array(arr: array<int>): int
    requires arr != null
    requires arr.Length > 0
{
    var m := arr[0];
    for i := 1 to arr.Length
        if arr[i] > m
            m := arr[i];
    m
}
