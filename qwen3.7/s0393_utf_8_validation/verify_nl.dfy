// s0393 - UTF-8 Validation
// Dafny formal specification (spec-only)
// requires(*The length of the integer array parameter `data` is greater than or equal to 1 and is less than or equal to 20000.*);
// requires(*All values in the integer array parameter `data` are greater than or equal to 0 and are less than or equal to 255.*);
// requires(*The integer array parameter `data` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, the integer array parameter `data` is a valid UTF-8 encoding.*);
// ensures(*If the boolean result is equal to the false literal, the integer array parameter `data` is not a valid UTF-8 encoding.*);
// ensures(*If the integer array parameter `data` is equal to [197,130,1], the boolean result is equal to the true literal.*);
// ensures(*If the integer array parameter `data` is equal to [235,140,4], the boolean result is equal to the false literal.*);
method validUtf8(data: array<int>) returns (result: bool)
{
    result := false;
    assume false;
}
