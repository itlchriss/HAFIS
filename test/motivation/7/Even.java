public class Even {
//@ requires(*The integer parameter `number` is less than or equal to the minimum value of java integer and is greater than or equal to the maximum value of java integer.*);
//@ ensures(*If the boolean result is equal to the false literal, the integer parameter `number` mod 2 is not equal to 0.*);
    public static boolean isEven(int number) {
        if(number % 2 == 0)
            return true;
        else
            return false;
    }
}
