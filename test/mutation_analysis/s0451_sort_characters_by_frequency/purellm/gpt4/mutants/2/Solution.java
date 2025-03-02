package g0401_0500.s0451_sort_characters_by_frequency;

// #Medium #String #Hash_Table #Sorting #Heap_Priority_Queue #Counting #Bucket_Sort
// #Data_Structure_II_Day_21_Heap_Priority_Queue
// #2022_07_18_Time_13_ms_(89.63%)_Space_43.3_MB_(87.60%)

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.util.Arrays;

public class Solution {
//@ requires s != null && s.length() >= 1 && s.length() <= 500000;
//@ requires (\forall int i; 0 <= i < s.length(); ('A' <= s.charAt(i) && s.charAt(i) <= 'Z') || ('a' <= s.charAt(i) && s.charAt(i) <= 'z') || ('0' <= s.charAt(i) && s.charAt(i) <= '9'));

// ensures (\forall int i, j; 0 <= i < j < \result.length();(\num_of(\result, \result.charAt(i)) >= \num_of(\result, \result.charAt(j))) || (\num_of(\result, \result.charAt(i)) == \num_of(\result, \result.charAt(j)) && \result.indexOf(\result.charAt(i)) <= \result.indexOf(\result.charAt(j))));
// ensures (\forall int i; 0 <= i < s.length(); \num_of(s, s.charAt(i)) == \num_of(\result, s.charAt(i)));
// also
// ensures \isValidString(\result);

    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) - 1);
        }
        TreeMap<Integer, List<Character>> reverseMap = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Character, Integer> c : map.entrySet()) {
            int freq = map.get(c.getKey());
            reverseMap.computeIfAbsent(freq, k -> new ArrayList<>());
            reverseMap.get(freq).add(c.getKey());
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, List<Character>> freq : reverseMap.entrySet()) {
            List<Character> list = reverseMap.get(freq.getKey());
            for (char c : list) {
                for (int i = 0; i < freq.getKey(); i++) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
