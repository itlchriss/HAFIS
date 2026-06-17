// #Medium #String #Hash_Table #Sorting #Heap_Priority_Queue #Counting #Bucket_Sort
// #Data_Structure_II_Day_21_Heap_Priority_Queue
// #2022_07_18_Time_13_ms_(89.63%)_Space_43.3_MB_(87.60%)
// Dafny version of Solution

// requires(*The length of the string parameter `s` is less than or equal to 500000 and is greater than or equal to 1.*);
// requires(*The string parameter `s` consists of uppercase and lowercase English letters and digits.*);
// ensures(*The characters in the string result are sorted in decreasing order based on their frequency in the string parameter `s`.*);
// ensures(*If there are multiple valid answers, any of them can be returned as the result.*);
    method frequencySort(s: string) returns (result: string)
    {
        // Map<Character, Integer> map = new HashMap<>();
        for c in s.toCharArray()
        {
            map := map[c := map.getOrDefault(c, 0) + 1];
        }
        // TreeMap<Integer, List<Character>> reverseMap = new TreeMap<>(Collections.reverseOrder());
        // for (Map.Entry<Character, Integer> c : map.entrySet()) {
        var freq: int := map[c.getKey(]);
        // reverseMap.computeIfAbsent(freq, k -> new ArrayList<>())
        // reverseMap.get(freq).add(c.getKey())
        var sb: StringBuilder := new StringBuilder();
        // for (Map.Entry<Integer, List<Character>> freq : reverseMap.entrySet()) {
        var list: seq<char> := reverseMap[freq.getKey(]);
        for c in list
        {
            for i := 0 to freq.getKey()
                invariant i >= 0
                invariant i <= freq.getKey()
            {
                // sb.append(c)
            }
        }
        result := sb.toString();
        return;
    }
