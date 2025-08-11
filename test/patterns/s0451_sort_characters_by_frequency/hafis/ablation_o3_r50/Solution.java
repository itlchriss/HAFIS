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
//@ requires(*The string parameter `s` consists of uppercase English letters lowercase English letters and digits.*);
//@ ensures(*The string result has the same length as the string parameter `s` and every character appears the same number of times in the string result and in the string parameter `s`.*);
//@ ensures(*All occurrences of each distinct character are contiguous in the string result.*);
//@ ensures(*Whenever two different characters appear consecutively in the string result the number of occurrences of the leading character in the string parameter `s` is greater than or equal to the number of occurrences of the following character in the string parameter `s`.*);
//@ ensures(*If the string parameter `s` is equal to "tree" the string result is equal to "eert".*);
//@ ensures(*If the string parameter `s` is equal to "tree" the string result is equal to "eetr".*);
//@ ensures(*If the string parameter `s` is equal to "cccaaa" the string result is equal to "aaaccc".*);
//@ ensures(*If the string parameter `s` is equal to "cccaaa" the string result is equal to "cccaaa".*);
//@ ensures(*If the string parameter `s` is equal to "Aabb" the string result is equal to "bbAa".*);
//@ ensures(*If the string parameter `s` is equal to "Aabb" the string result is equal to "bbaA".*);
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