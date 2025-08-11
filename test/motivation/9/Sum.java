public class Sum {
//@ requires(*The integer parameter `num1` is greater than or equal to 0 and is less than or equal to 2000.  *);
//@ requires(*The integer parameter `num2` is greater than or equal to 0 and is less than or equal to 2000.  *);
//@ ensures(*The integer result is less than or equal to 2000 and is greater than or equal to 0.  *);
//@ ensures(*The integer result is equal to the sum between the integer parameter `num1` and the integer parameter `num2`.*);

    public static int Sum(int num1, int num2) {
        int r = num1 + num2;
        return r;
    }
}
