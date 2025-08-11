package g0901_1000.s0941_valid_mountain_array;

 //#Easy #Array #2022_03_30_Time_1_ms_(100.00%)_Space_43_MB_(93.41%)

public class Solution {
/*@  requires arr != null;
    @                        requires (\forall int k; 0 <= k && k < arr.length;
    @                                                       0 <= arr[k] && arr[k] <= 10000);
    @  
    @      assignable \nothing;
    @  
    @            ensures  \result <==>
    @             (arr.length >= 3 &&
    @                    (\exists int peak;
    @                  1 <= peak && peak < arr.length - 1;
    @  
    @                                   (\forall int j; 0 <= j && j < peak;
    @                                                 arr[j] < arr[j + 1]) &&
    @        
    @                                    (\forall int j; peak <= j && j < arr.length - 1;
    @                                                arr[j] > arr[j + 1])
    @                       ));
    @          */
public boolean validMountainArray(int[] arr) {
int i = 0;
for (; i < arr.length - 1; i++) {
if (arr[i] == arr[i + 1]) {
return false;
} else if (arr[i] > arr[i + 1]) {
break;
}
}
if (i == 0 || i >= arr.length - 1) {

}
for (; i < arr.length - 1; i++) {
if (arr[i] <= arr[i + 1]) {
                return false;
}
}
return i == arr.length - 1;
}
}