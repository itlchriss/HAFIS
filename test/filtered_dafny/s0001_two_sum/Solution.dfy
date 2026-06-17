// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Hash_Table
// #Data_Structure_I_Day_2_Array #Level_1_Day_13_Hashmap #Udemy_Arrays #Big_O_Time_O(n)_Space_O(n)
// #2024_01_04_Time_2_ms_(85.97%)_Space_44.8_MB_(15.45%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `numbers` is less than or equal to 10000 and is greater than or equal to 2.*);
// requires(*All the values in the integer array parameter `numbers` are less than or equal to 1000000000 and are greater than or equal to -1000000000.*);
// requires(*The integer parameter `target` is less than or equal to 1000000000 and is greater than or equal to -1000000000.*);
// ensures(*The integer array result has a length of 2.*);
// ensures(*The sum of the values at the indices specified by the integer array result is equal to the integer parameter `target`.*);
// ensures(*The values in the integer array result are unique.*);
// ensures(*The values in the integer array result are indices of the two numbers in the integer array parameter `numbers` that add up to the integer parameter `target`.*);
    method twoSum(numbers: array<int>, target: int) returns (result: array<int>)
    {
        // Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        // invariant 0 <= i <= numbers.length;
        for i := 0 to |numbers|
            // invariant i >= 0
            // invariant(*The index i is within the bounds of the integer array parameter `numbers`.*);
            // invariant(*The index i is non-negative.*);
            // invariant i <= |numbers|
        {
            var requiredNum: int := target - numbers[i];
            if requiredNum in indexMap {
                result := new int[] {indexMap[requiredNum], i};
                return;
            }
            indexMap := indexMap[numbers[i] := i];
        }
        result := new int[] {-1, -1};
        return;
    }
