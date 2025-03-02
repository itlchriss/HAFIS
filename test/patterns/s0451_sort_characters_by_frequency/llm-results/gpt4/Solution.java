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
//@ @ requires s != null && s.length() >= 1 && s.length() <= 500000;
//@ @ requires (\forall int i; 0 <= i < s.length();
//@ @           ('A' <= s.charAt(i) && s.charAt(i) <= 'Z') ||
//@ @           ('a' <= s.charAt(i) && s.charAt(i) <= 'z') ||
//@ @           ('0' <= s.charAt(i) && s.charAt(i) <= '9'));
//@ @
//@ @ ensures (\forall int i, j; 0 <= i < j < \result.length();
//@ @          (\num_of(\result, \result.charAt(i)) >= \num_of(\result, \result.charAt(j))) ||
//@ @          (\num_of(\result, \result.charAt(i)) == \num_of(\result, \result.charAt(j)) &&
//@ @           \result.indexOf(\result.charAt(i)) <= \result.indexOf(\result.charAt(j))));
//@ @ ensures (\forall int i; 0 <= i < s.length();
//@ @          \num_of(s, s.charAt(i)) == \num_of(\result, s.charAt(i)));
//@ @
//@ @ pure helper int \num_of(String str, char c) {
//@ @     int count = 0;
//@ @     for (int i = 0; i < str.length(); i++) {
//@ @         if (str.charAt(i) == c) {
//@ @             count++;
//@ @         }
//@ @     }
//@ @     return count;
//@ @ }
//@ @
//@ @ pure helper int \indexOf(String str, char c) {
//@ @     for (int i = 0; i < str.length(); i++) {
//@ @         if (str.charAt(i) == c) {
//@ @             return i;
//@ @         }
//@ @     }
//@ @     return -1;
//@ @ }
//@ @
//@ @ pure helper boolean \isValidChar(char c) {
//@ @     return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || ('0' <= c && c <= '9');
//@ @ }
//@ @
//@ @ pure helper boolean \isValidString(String str) {
//@ @     return str != null && str.length() >= 1 && str.length() <= 500000 &&
//@ @            (\forall int i; 0 <= i < str.length(); \isValidChar(str.charAt(i)));
//@ @ }
//@ @
//@ @ also
//@ @ ensures \isValidString(\result);
//@ @*/
//@ public String frequencySort(String s) {
//@ // Implementation goes here
//@ }
//@ ```
//@ 
//@ ### Explanation:
//@ 
//@ - **Preconditions (`requires`)**:
//@ - The input string `s` is not null and its length is between 1 and 500,000.
//@ - Each character in `s` is either an uppercase letter, a lowercase letter, or a digit.
//@ 
//@ - **Postconditions (`ensures`)**:
//@ - The resulting string is sorted in decreasing order based on the frequency of characters. If two characters have the same frequency, their relative order in the result is not specified, but they must appear together.
//@ - The frequency of each character in the result is the same as in the input string.
//@ - The result is a valid string according to the constraints.
//@ 
//@ - **Helper Methods**:
//@ - `\num_of(String str, char c)`: Counts the occurrences of character `c` in string `str`.
//@ - `\indexOf(String str, char c)`: Finds the first index of character `c` in string `str`.
//@ - `\isValidChar(char c)`: Checks if a character is valid (uppercase, lowercase, or digit).
//@ - `\isValidString(String str)`: Checks if a string is valid according to the problem constraints.
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
