/*
Problem: Count Ways To Build Good Strings
(LeetCode 2466)

Problem Explanation:
You are given four integers:
- low   → minimum allowed length
- high  → maximum allowed length
- zero  → length added by appending a block of '0's
- one   → length added by appending a block of '1's

You start with an empty string of length 0.
You can repeatedly:
- append `zero` number of characters
- append `one` number of characters

Task:
Count how many strings can be formed whose final length is
between low and high (inclusive).

Return the answer modulo 1e9 + 7.

--------------------------------------------------
Core Intuition:
--------------------------------------------------
This is a classic DP on length.

Think in reverse:
- Instead of building strings, think in terms of lengths
- From a given length `len`, you can:
  - go to len + zero
  - go to len + one

If a length is already between [low, high], it contributes 1 valid string.

The problem reduces to:
“How many ways can I reach valid lengths starting from length 0?”

--------------------------------------------------
DP State Definition:
--------------------------------------------------
dp[len] = number of good strings that can be formed
          starting from current length = len

Answer = dp[0]

--------------------------------------------------
Approaches Covered:
--------------------------------------------------
1. Recursion (Brute Force)
2. Memoization (Top-Down DP)
3. Tabulation (Bottom-Up DP)
4. Space Optimized DP (Final)

--------------------------------------------------
Time Complexity:
--------------------------------------------------
All DP versions: O(high)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
Recursion      : O(high) stack
Memoization   : O(high)
Tabulation    : O(high)
Optimized     : O(high)  (cannot reduce further due to transitions)

--------------------------------------------------
*/

class Solution {

    static final int MOD = 1_000_000_007;

    /*
    --------------------------------------------------
    1) RECURSION (Brute Force)
    --------------------------------------------------
    f(len) = number of valid strings from length = len
    --------------------------------------------------
    Time: Exponential (TLE)
    Space: O(high) recursion stack
    --------------------------------------------------
    */
    private int solveRec(int len, int low, int high, int zero, int one) {

        if (len > high) return 0;

        int ans = 0;

        if (len >= low && len <= high) {
            ans = 1;
        }

        ans = (ans + solveRec(len + zero, low, high, zero, one)) % MOD;
        ans = (ans + solveRec(len + one, low, high, zero, one)) % MOD;

        return ans;
    }

    /*
    --------------------------------------------------
    2) MEMOIZATION (Top-Down DP)
    --------------------------------------------------
    dp[len] stores result of f(len)
    --------------------------------------------------
    Time: O(high)
    Space: O(high)
    --------------------------------------------------
    */
    private int solveMemo(int len, int low, int high,
                          int zero, int one, int[] dp) {

        if (len > high) return 0;

        if (dp[len] != -1) return dp[len];

        int ans = 0;

        if (len >= low && len <= high) {
            ans = 1;
        }

        ans = (ans + solveMemo(len + zero, low, high, zero, one, dp)) % MOD;
        ans = (ans + solveMemo(len + one, low, high, zero, one, dp)) % MOD;

        return dp[len] = ans;
    }

    /*
    --------------------------------------------------
    3) TABULATION (Bottom-Up DP)
    --------------------------------------------------
    dp[len] = number of good strings from length = len
    --------------------------------------------------
    Time: O(high)
    Space: O(high)
    --------------------------------------------------
    */
    private int solveTab(int low, int high, int zero, int one) {

        int[] dp = new int[high + 1];

        for (int len = high; len >= 0; len--) {

            int ans = 0;

            if (len >= low && len <= high) {
                ans = 1;
            }

            if (len + zero <= high) {
                ans = (ans + dp[len + zero]) % MOD;
            }

            if (len + one <= high) {
                ans = (ans + dp[len + one]) % MOD;
            }

            dp[len] = ans;
        }

        return dp[0];
    }

    /*
    --------------------------------------------------
    4) SPACE OPTIMIZED DP (FINAL)
    --------------------------------------------------
    Same as tabulation (already optimal)
    --------------------------------------------------
    */
    public int countGoodStrings(int low, int high, int zero, int one) {

        return solveTab(low, high, zero, one);

        /*
        // For reference:

        // Recursion (TLE)
        // return solveRec(0, low, high, zero, one);

        // Memoization
        // int[] dp = new int[high + 1];
        // Arrays.fill(dp, -1);
        // return solveMemo(0, low, high, zero, one, dp);
        */
    }
}

/*
--------------------------------------------------
Example Test Cases:
--------------------------------------------------

Example 1:
Input:
low = 3, high = 3, zero = 1, one = 1

Explanation:
All binary strings of length 3 are valid.
Answer = 8

--------------------------------------------------

Example 2:
Input:
low = 2, high = 3, zero = 1, one = 2

Valid strings lengths:
2, 3

Output:
5

--------------------------------------------------
*/

