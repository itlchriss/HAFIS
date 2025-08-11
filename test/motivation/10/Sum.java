public class Sum {
//@ requires(*The integer parameter `num1` is less than or equal to 1000 and is greater than or equal to the minimum value of a java integer.*);
//@ requires(*The integer parameter `num2` is less than or equal to 1000 and is greater than or equal to the minimum value of a java integer.*);
//@ ensures(*The integer result is less than or equal to 2000 and is greater than or equal to the sum of the minimum value of a java integer and the integer parameter `num1`.*);
//@ ensures(*The integer result is equal to the sum between the integer parameter `num1` and the integer parameter `num2`.*);
    public static int Sum(int num1, int num2) {
        int r = num1 + num2;
        return r;
    }
}
