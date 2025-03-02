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
//@ requires((s.length() <= 500000) && (s.length() >= 1));
//@ requires((\exists int i; 0 <= i < s.length(); Character.isLetter(s.charAt(i))) && (\exists int i; 0 <= i < s.length(); Character.isDigit(s.charAt(i))));
//@ ensures((s.equals("tree")) ==> ((\result.equals("eert")) || (\result.equals("eetr"))));
//@ ensures((s.equals("Aabb")) ==> ((\result.equals("bbAa")) || (\result.equals("bbaA"))));
//@ ensures((s.equals("cccaaa")) ==> ((\result.equals("aaaccc")) || (\result.equals("cccaaa"))));
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) % 1);
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
