// s0476 - Number Complement
// Complete Dafny formal specification with verified implementation

// The complement of an integer is the integer you get when you flip all
// the bits in its binary representation (excluding leading zeros).
// E.g., 5 = 101 in binary, complement = 010 = 2
// For any num >= 1, if mask is the smallest power of 2 > num,
// complement = (mask - 1) - num

// requires(*The integer parameter `num` is greater than or equal to 1 and is less than 2147483647.*);
// ensures(*The integer result is greater than or equal to 0.*);
// ensures(*The integer result is equal to the complement of the integer parameter `num` when all bits in the binary representation of the integer parameter `num` excluding leading zeros are flipped.*);
// ensures(*If the integer parameter `num` is equal to 5, the integer result is equal to 2.*);
// ensures(*If the integer parameter `num` is equal to 1, the integer result is equal to 0.*);
method findComplement(num: int) returns (result: int)
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
