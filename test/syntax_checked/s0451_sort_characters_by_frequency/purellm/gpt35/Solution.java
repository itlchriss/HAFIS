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
//@ requires s != null && s.length() >= 1 && s.length() <= 5 * Math.pow(10, 5);
//@ requires s.matches("[a-zA-Z0-9]+");
//@ ensures \result != null;
//@ ensures (\forall int i, j; 0 <= i && i < j && j < \result.length(); s.charAt(i) != s.charAt(j) || \result.indexOf(s.charAt(i)) >= \result.indexOf(s.charAt(j)));
// ensures (\forall char c; s.chars().distinct().allMatch(c -> s.chars().filter(ch -> ch == c).count() == \result.chars().filter(ch -> ch == c).count()));
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
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
