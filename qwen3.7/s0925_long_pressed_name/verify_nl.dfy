// s0925 - Long Pressed Name
// Dafny formal specification (spec-only: nested loop two-pointer)

method isLongPressedName(name: string, typed: string) returns (result: bool)
{
    // requires(*The length of the string parameter `name` is greater than or equal to 1 and is less than or equal to 1000.*);
    // requires(*The length of the string parameter `typed` is greater than or equal to 1 and is less than or equal to 1000.*);
    // requires(*The string parameter `name` is not equal to the null literal.*);
    // requires(*The string parameter `typed` is not equal to the null literal.*);
    // requires(*All values in the string parameter `name` are lowercase English letters.*);
    // requires(*All values in the string parameter `typed` are lowercase English letters.*);
    // ensures(*The boolean result is equal to the true literal if and only if the string parameter `typed` can be formed by long-pressing characters of the string parameter `name`.*);
    // ensures(*If the string parameter `name` is equal to "alex" and the string parameter `typed` is equal to "aaleex", the boolean result is equal to the true literal.*);
    // ensures(*If the string parameter `name` is equal to "saeed" and the string parameter `typed` is equal to "ssaaedd", the boolean result is equal to the false literal.*);
    result := false;
    assume false;  // spec-only
}
