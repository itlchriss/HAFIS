// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Hash_Table
// #Data_Structure_I_Day_2_Array #Level_1_Day_13_Hashmap #Udemy_Arrays #Big_O_Time_O(n)_Space_O(n)
// #2024_01_04_Time_2_ms_(85.97%)_Space_44.8_MB_(15.45%)
// Dafny version of Solution

// ensures(*The sum of the values at the indices specified by the integer array result is equal to the integer parameter `target`.*);
// ensures(*The values in the integer array result are indices of the two numbers in the integer array parameter `numbers` that add up to the integer parameter `target`.*);
    method twoSum(numbers: array<int>, target: int) returns (result: array<int>)
    requires (numbers.length <= 10000) && (numbers.length >= 2)
    requires (numbers.length <= 10000) && (numbers.length >= 2)
    requires (numbers.size() <= 10000) && (numbers.size() >= 2)
    requires (numbers.length() <= 10000) && (numbers.length() >= 2)
    requires (numbers.size() <= 10000) && (numbers.size() >= 2)
    requires (\forall int i; 0 <= i < numbers.length; numbers[i] <= 1000000000) && (\forall int i; 0 <= i < numbers.length; numbers[i] >= -1000000000)
    requires (target <= 1000000000) && (target >= -1000000000)
    ensures \result.length == 2
    ensures \forall int i; 0 <= i < \result.length; (\forall int j; 0 <= j < \result.length && j != i; \result[j] != \result[i])
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
