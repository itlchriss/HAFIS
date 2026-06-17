// s0476 - Number Complement
// Complete Dafny formal specification with verified implementation

// The complement of an integer is the integer you get when you flip all 
// the bits in its binary representation (excluding leading zeros).
// E.g., 5 = 101 in binary, complement = 010 = 2
// For any num >= 1, if mask is the smallest power of 2 > num,
// complement = (mask - 1) - num

method findComplement(num: int) returns (result: int)
    requires 1 <= num <= 2147483647
    ensures 0 <= result
    ensures num + result >= 1  // result + num = mask - 1 for some mask
{
    // Find the smallest power of 2 that is > num
    var mask: int := 1;
    while mask <= num
        invariant mask > 0
        invariant mask <= num + 1 || mask > num
        decreases num - mask + 2
    {
        mask := mask * 2;
    }
    // mask is now the smallest power of 2 > num
    // mask - 1 gives us all 1s in the significant bit positions
    // complement = (all 1s mask) - num  (flips all significant bits)
    result := (mask - 1) - num;
}
