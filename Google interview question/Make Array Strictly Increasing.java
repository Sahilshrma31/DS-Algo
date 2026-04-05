import java.util.*;

/*
Problem: Make Array Strictly Increasing (LeetCode 1187)

--------------------------------------------------
Problem Description
--------------------------------------------------
Given two integer arrays arr1 and arr2, return the
minimum number of operations needed to make arr1
strictly increasing.

In one operation, you can replace any element in arr1
with any element from arr2.

If it is not possible, return -1.

--------------------------------------------------
Key Observations
--------------------------------------------------
1. We must ensure arr1 is strictly increasing.
2. At each index, we have 2 choices:
   - Keep arr1[i] (if valid)
   - Replace it using arr2
3. arr2 should be sorted for binary search.
4. We always pick the smallest valid replacement > prev.

--------------------------------------------------
Intuition
--------------------------------------------------
We traverse arr1 and track the previous chosen value.

At every index:
- If current element > prev → we can keep it
- Otherwise, we must replace it

Even if we can keep it, replacing might give a better result.

So we try both options and take minimum operations.

--------------------------------------------------
Approach
--------------------------------------------------
1. Sort arr2
2. Use recursion:
   - Try keeping current element
   - Try replacing using binary search
3. Memoize states (idx, prev)
4. Convert to tabulation

--------------------------------------------------
State Definition
--------------------------------------------------
dp[idx][prev] =
minimum operations needed from index idx
given previous value = prev

(Note: prev is handled using map due to large range)

--------------------------------------------------
Transition
--------------------------------------------------
option1 = solve(idx+1, arr1[idx]) if arr1[idx] > prev

option2 = 1 + solve(idx+1, nextGreaterFromArr2)

dp[idx][prev] = min(option1, option2)

--------------------------------------------------
Time Complexity
--------------------------------------------------
Recursion      : Exponential
Memoization    : O(n * log m * states)
Tabulation     : O(n * m)

--------------------------------------------------
Space Complexity
--------------------------------------------------
Memoization    : O(n * states)
Tabulation     : O(n * m)

--------------------------------------------------
Example
--------------------------------------------------
Input:
arr1 = [1,5,3,6,7]
arr2 = [1,3,2,4]

Output:
1

Explanation:
Replace 5 with 2 → array becomes [1,2,3,6,7]
*/

class Solution {

    int n;

    /*
    --------------------------------------------------
    Binary Search: Find next greater element in arr2
    --------------------------------------------------
    */

    public int binarySearch(int target, int[] arr) {
        int low = 0, high = arr.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] <= target) low = mid + 1;
            else high = mid;
        }

        return low == arr.length ? -1 : low;
    }

    /*
    --------------------------------------------------
    1. Recursion (Brute Force)
    --------------------------------------------------
    */

    public int solveRec(int idx, int[] arr1, int[] arr2, int prev) {

        if (idx >= n) return 0;

        int option1 = Integer.MAX_VALUE;

        // Keep current element
        if (arr1[idx] > prev) {
            option1 = solveRec(idx + 1, arr1, arr2, arr1[idx]);
        }

        int option2 = Integer.MAX_VALUE;

        // Replace using arr2
        int ub = binarySearch(prev, arr2);

        if (ub != -1) {
            int temp = solveRec(idx + 1, arr1, arr2, arr2[ub]);
            if (temp != Integer.MAX_VALUE) {
                option2 = 1 + temp;
            }
        }

        return Math.min(option1, option2);
    }

    /*
    --------------------------------------------------
    2. Memoization (Top Down DP)
    --------------------------------------------------
    */

    Map<String, Integer> dp = new HashMap<>();

    public int solveMemo(int idx, int[] arr1, int[] arr2, int prev) {

        if (idx >= n) return 0;

        String key = idx + "," + prev;

        if (dp.containsKey(key)) {
            return dp.get(key);
        }

        int option1 = Integer.MAX_VALUE;

        if (arr1[idx] > prev) {
            option1 = solveMemo(idx + 1, arr1, arr2, arr1[idx]);
        }

        int option2 = Integer.MAX_VALUE;

        int ub = binarySearch(prev, arr2);

        if (ub != -1) {
            int temp = solveMemo(idx + 1, arr1, arr2, arr2[ub]);
            if (temp != Integer.MAX_VALUE) {
                option2 = 1 + temp;
            }
        }

        int ans = Math.min(option1, option2);
        dp.put(key, ans);

        return ans;
    }

    /*
    --------------------------------------------------
    3. Tabulation (Bottom Up DP using Map)
    --------------------------------------------------
    */

    public int makeArrayIncreasing(int[] arr1, int[] arr2) {

        n = arr1.length;
        Arrays.sort(arr2);

        // dp map: prev -> min operations
        Map<Integer, Integer> dp = new HashMap<>();
        dp.put(Integer.MIN_VALUE, 0);

        for (int num : arr1) {

            Map<Integer, Integer> next = new HashMap<>();

            for (int prev : dp.keySet()) {

                int ops = dp.get(prev);

                // Option 1: Keep
                if (num > prev) {
                    next.put(num,
                        Math.min(next.getOrDefault(num, Integer.MAX_VALUE), ops));
                }

                // Option 2: Replace
                int idx = binarySearch(prev, arr2);

                if (idx != -1) {
                    int val = arr2[idx];

                    next.put(val,
                        Math.min(next.getOrDefault(val, Integer.MAX_VALUE), ops + 1));
                }
            }

            dp = next;
        }

        int ans = Integer.MAX_VALUE;

        for (int val : dp.values()) {
            ans = Math.min(ans, val);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}