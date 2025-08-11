package g0101_0200.s0152_maximum_product_subarray;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Dynamic_Programming_I_Day_6 #Level_2_Day_13_Dynamic_Programming #Udemy_Dynamic_Programming
// #Big_O_Time_O(N)_Space_O(1) #2022_06_25_Time_0_ms_(100.00%)_Space_42.7_MB_(82.46%)

public class Solution {
//@ requires(*All the values in the integer array parameter `arr` are greater than or equal to -10 and are less than or equal to 10*);
//@ ensures(*The integer result is representable in a 32 bit signed integer*);
//@ ensures(*If the length of the integer array parameter `arr` is equal to 1 the integer result is equal to the single value of the integer array parameter `arr`*);
//@ ensures(*The integer result is greater than or equal to the product of every contiguous non empty subarray of the integer array parameter `arr`*);
//@ ensures(*If every value in the integer array parameter `arr` is equal to 0 the integer result is equal to 0*);
//@ ensures(*If the integer array parameter `arr` is equal to [2,3,-2,4] the integer result is equal to 6*);
//@ ensures(*If the integer array parameter `arr` is equal to [-2,0,-1] the integer result is equal to 0*);
    public int maxProduct(int[] arr) {
        int ans = Integer.MIN_VALUE;
        int cprod = 1;
        for (int j : arr) {
            cprod = cprod * j;
            ans = Math.max(ans, cprod);
            if (cprod == 0) {
                cprod = 1;
            }
        }
        cprod = 1;
        //@ maintaining 0 <= i <= arr.length - 1 || i == -1;
        for (int i = arr.length - 1; i >= 0; i--) {
            cprod = cprod * arr[i];
            ans = Math.max(ans, cprod);
            if (cprod == 0) {
                cprod = 1;
            }
        }
        return ans;
    }
}