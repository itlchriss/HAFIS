import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DeepEquals {
//@ requires(*The integer array parameter `x` is not null.*);
//@ requires(*The integer array parameter `y` is not null.*);
//@ ensures(*The boolean result is equal to the true literal if and only if the integer array parameter `x` deeply equals the integer array parameter `y`.*);
//@ ensures(*The boolean result is equal to the false literal if the integer array parameter `x` does not deeply equal the integer array parameter `y`.*);
    public boolean deepEquals(int[] x, int[] y) {
        return Arrays.deepEquals(x, y);
    }
}
