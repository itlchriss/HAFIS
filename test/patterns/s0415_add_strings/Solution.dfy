// #Easy #String #Math #Simulation #Data_Structure_II_Day_6_String
// #2022_07_16_Time_3_ms_(82.41%)_Space_43.1_MB_(66.56%)
// Dafny version of Solution

// requires(*The length of the string parameter `num1` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*The length of the string parameter `num2` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*The string parameter `num1` consists of only digits and does not have any leading zeros except for zero itself.*);
// requires(*The string parameter `num2` consists of only digits and does not have any leading zeros except for zero itself.*);
// ensures(*The string result is the sum of the string parameter `num1` and the string parameter `num2`.*);
// ensures(*The string result is represented as a string.*);
// ensures(*The string result is equal to the sum of the integer values represented by the string parameter `num1` and the string parameter `num2`.*);
    method addStrings(num1: string, num2: string) returns (result: string)
    {
        var result: StringBuilder := new StringBuilder();
        var carry: int := 0;
        // for (int i = num1.length() - 1, j = num2.length() - 1;
        // i >= 0 || j >= 0 || carry != 0;
        // i--, j--) {
        var sum: int := carry;
        if i >= 0 {
            sum := sum + Character.digit(num1[i], 10);
        }
        if j >= 0 {
            sum := sum + Character.digit(num2[j], 10);
        }
        carry := sum / 10;
        // result.append(sum % 10)
        result := result.reverse().toString();
        return;
    }
