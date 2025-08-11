package g0901_1000.s0925_long_pressed_name;

// #Easy #String #Two_Pointers #2022_03_29_Time_1_ms_(84.87%)_Space_40.1_MB_(93.12%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires(\forall int i; 0 <= i < typed.length(); Character.isLowerCase(typed.charAt(i)));
//@ requires(\forall int i; 0 <= i < name.length(); Character.isLowerCase(name.charAt(i)));
//@ requires((name.length() >= 1) && (name.length() <= 1000));
//@ requires((typed.length() >= 1) && (typed.length() <= 1000));
//@ ensures((\result == true) ==> (typed.length() >= name.length()));
//@ ensures(((name.equals("alex")) && (typed.equals("aaleex"))) ==> (\result == true));
//@ ensures(((name.equals("saeed")) && (typed.equals("ssaaedd"))) ==> (\result == false));
    public boolean isLongPressedName(String name, String typed) {
        int i = 0;
        int j = 0;
        char prev = '$';
        if (typed.length() < name.length()) {
            return false;
        }
        while (i < name.length() && j <= typed.length()) {
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
                return false;
            }
        }
        return true;
    }
}
