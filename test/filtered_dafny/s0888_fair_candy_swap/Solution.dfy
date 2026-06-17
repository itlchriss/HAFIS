// #Easy #Array #Hash_Table #Sorting #Binary_Search
// #2022_03_28_Time_18_ms_(68.20%)_Space_72.2_MB_(19.02%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `aliceSizes` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*The length of the integer array parameter `bobSizes` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*All the values in the integer array parameter `aliceSizes` are less than or equal to 100000 and are greater than or equal to 1.*);
// requires(*All the values in the integer array parameter `bobSizes` are less than or equal to 100000 and are greater than or equal to 1.*);
// requires(*The sum of all values in the integer array parameter `aliceSizes` is not equal to the sum of all values in the integer array parameter `bobSizes`.*);
// ensures(*The integer array result has a length of 2.*);
// ensures(*The sum of all values in the integer array parameter `aliceSizes` minus the value at index 0 of the integer array result is equal to the sum of all values in the integer array parameter `bobSizes` minus the value at index 1 of the integer array result.*);
    method fairCandySwap(aliceSizes: array<int>, bobSizes: array<int>) returns (result: array<int>)
    {
        var aSum: int := 0;
        var bSum: int := 0;
        var diff: int;
        var ans := new int[2];
        for bar in aliceSizes
        {
            aSum := aSum + bar;
        }
        for bar in bobSizes
        {
            bSum := bSum + bar;
        }
        diff := aSum - bSum;
        var set: set<int> := {};
        for bar in aliceSizes
        {
            set := set + [bar];
        }
        for bar in bobSizes
        {
            if bar + diff / 2 in set {
                ans[0] := bar + diff / 2;
                ans[1] := bar;
                break;
            }
        }
        result := ans;
        return;
    }
