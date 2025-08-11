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

public class Solution {
//@ requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 500000.*);
//@ requires(*The string parameter `s` consists of uppercase and lowercase English letters and digits.*);
//@ ensures(*The length of the string result is equal to the length of the string parameter `s`.*);
//@ ensures(*The string result contains the same characters as the string parameter `s` with the same frequencies.*);
//@ ensures(*If a character appears more frequently in the string parameter `s` than another character, that character appears before the other character in the string result.*);
//@ ensures(*If two characters have the same frequency in the string parameter `s`, their relative order in the string result can be arbitrary.*);
//@ ensures(*All occurrences of the same character in the string result are consecutive.*);
//@ ensures(*If the string parameter `s` is equal to "tree", the string result is equal to "eert" or "eetr".*);
//@ ensures(*If the string parameter `s` is equal to "cccaaa", the string result is equal to "aaaccc" or "cccaaa".*);
//@ ensures(*If the string parameter `s` is equal to "Aabb", the string result is equal to "bbAa" or "bbaA".*);
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