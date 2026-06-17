// #Easy #Array #Hash_Table #Sorting #Binary_Search
// #2022_03_28_Time_18_ms_(68.20%)_Space_72.2_MB_(19.02%)
// Dafny version of Solution

requires (alicesizes.length <= 10000) && (alicesizes.length >= 1)
requires (alicesizes.length <= 10000) && (alicesizes.length >= 1)
requires (alicesizes.size() <= 10000) && (alicesizes.size() >= 1)
requires (alicesizes.length() <= 10000) && (alicesizes.length() >= 1)
requires (alicesizes.size() <= 10000) && (alicesizes.size() >= 1)
requires (bobsizes.length <= 10000) && (bobsizes.length >= 1)
requires (bobsizes.length <= 10000) && (bobsizes.length >= 1)
requires (bobsizes.size() <= 10000) && (bobsizes.size() >= 1)
requires (bobsizes.length() <= 10000) && (bobsizes.length() >= 1)
requires (bobsizes.size() <= 10000) && (bobsizes.size() >= 1)
requires (\forall int i; 0 <= i < alicesizes.length; alicesizes[i] <= 100000) && (\forall int i; 0 <= i < alicesizes.length; alicesizes[i] >= 1)
requires (\forall int i; 0 <= i < bobsizes.length; bobsizes[i] <= 100000) && (\forall int i; 0 <= i < bobsizes.length; bobsizes[i] >= 1)
requires (\sum int i; 0 <= i < alicesizes.length; alicesizes[i]) != (\sum int i; 0 <= i < bobsizes.length; bobsizes[i])
ensures \result.length == 2
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
