// s0925 - Long Pressed Name
// Dafny formal specification (spec-only: nested loop two-pointer)

// requires(*The length of the string parameter `name` is greater than or equal to 1 and is less than or equal to 1000.*);
// requires(*The length of the string parameter `typed` is greater than or equal to 1 and is less than or equal to 1000.*);
// requires(*The string parameter `name` consists of only lowercase English letters.*);
// requires(*The string parameter `typed` consists of only lowercase English letters.*);
// requires(*The string parameter `name` is not equal to the null literal.*);
// requires(*The string parameter `typed` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, the string parameter `typed` can be formed by inserting zero or more additional consecutive copies of characters into the string parameter `name`.*);
// ensures(*If the boolean result is equal to the false literal, the string parameter `typed` cannot be formed by inserting zero or more additional consecutive copies of characters into the string parameter `name`.*);
// ensures(*If the string parameter `name` is equal to "alex" and the string parameter `typed` is equal to "aaleex", the boolean result is equal to the true literal.*);
// ensures(*If the string parameter `name` is equal to "saeed" and the string parameter `typed` is equal to "ssaaedd", the boolean result is equal to the false literal.*);
method isLongPressedName(name: string, typed: string) returns (result: bool)
{
    result := false;
    assume false;  // spec-only
}
