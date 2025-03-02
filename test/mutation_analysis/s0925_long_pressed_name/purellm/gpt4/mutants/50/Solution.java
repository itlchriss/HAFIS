package g0901_1000.s0925_long_pressed_name;

// #Easy #String #Two_Pointers #2022_03_29_Time_1_ms_(84.87%)_Space_40.1_MB_(93.12%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires name != null && typed != null;
//@ requires name.length() >= 1 && name.length() <= 1000;
//@ requires typed.length() >= 1 && typed.length() <= 1000;
//@ requires (\forall int i; 0 <= i < name.length(); Character.isLowerCase(name.charAt(i)));
//@ requires (\forall int i; 0 <= i < typed.length(); Character.isLowerCase(typed.charAt(i)));
//@ ensures \result == true <==> ((\exists int[] map; map.length == name.length(); (\forall int i; 0 <= i < name.length(); (\exists int j; 0 <= j < typed.length(); map[i] == j && typed.charAt(j) == name.charAt(i) && (i == 0 || map[i-1] < map[i]) && (\forall int k; map[i] <= k < (i < name.length() - 1 ? map[i+1] : typed.length()); typed.charAt(k) == name.charAt(i))) ) ));
//@ also
//@ ensures \result == false <==> !((\exists int[] map; map.length == name.length(); (\forall int i; 0 <= i < name.length(); (\exists int j; 0 <= j < typed.length(); map[i] == j && typed.charAt(j) == name.charAt(i) && (i == 0 || map[i-1] < map[i]) && (\forall int k; map[i] <= k < (i < name.length() - 1 ? map[i+1] : typed.length()); typed.charAt(k) == name.charAt(i)) ) ) ) );

    public boolean isLongPressedName(String name, String typed) {
        int i = 0;
        int j = 0;
        char prev = '$';
        if (typed.length() < name.length()) {
            return false;
        }
        while (i < name.length() && j < typed.length()) {
            while (j < typed.length() && typed.charAt(j) != name.charAt(i)) {
                if (typed.charAt(j) != prev) {
                    return false;
                }
                if (j == typed.length() - 1) {
                    return false;
                }
                j++;
            }
            prev = name.charAt(i);
            i++;
            j++;
        }
        if (i < name.length()) {
            return false;
        }
        for (; j < typed.length(); j++) {
            if (typed.charAt(j) != prev) {

            }
        }
        return true;
    }
}
