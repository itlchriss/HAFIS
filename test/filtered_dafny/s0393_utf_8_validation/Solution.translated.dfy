// #Medium #Array #Bit_Manipulation #2022_07_13_Time_1_ms_(100.00%)_Space_43_MB_(87.62%)
// Dafny version of Solution

requires (data.length <= 20000) && (data.length >= 1)
requires (data.length <= 20000) && (data.length >= 1)
requires (data.size() <= 20000) && (data.size() >= 1)
requires (data.length() <= 20000) && (data.length() >= 1)
requires (data.size() <= 20000) && (data.size() >= 1)
requires (\forall int i; 0 <= i < data.length; data[i] > 0) && (\forall int i; 0 <= i < data.length; data[i] < 255)
// ensures(*If the integer array parameter `data` represents a valid UTF-8 encoding, the boolean result is true.*);
// ensures(*If the integer array parameter `data` represents an invalid UTF-8 encoding, the boolean result is false.*);
// ensures(*If the integer array parameter `data` is equal to [197,130,1], the boolean result is true.*);
// ensures(*If the integer array parameter `data` is equal to [235,140,4], the boolean result is false.*);
    method validUtf8(data: array<int>) returns (result: bool)
    {
        var count: int := 0;
        for d in data
        {
            if count == 0 {
                if d >> 5 == 0b110 {
                    count := 1;
                } else if d >> 4 == 0b1110 {
                    count := 2;
                } else if d >> 3 == 0b11110 {
                    count := 3;
                } else if d >> 7 == 1 {
                    result := false;
                    return;
                }
            } else {
                if d >> 6 != 0b10 {
                    result := false;
                    return;
                } else {
                    count := count - 1;
                }
            }
        }
        result := count == 0;
        return;
    }
