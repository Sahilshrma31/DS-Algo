import java.util.*;

/*
Problem: Distinct Subsequences (LeetCode 115)

--------------------------------------------------
Problem Description
--------------------------------------------------
Given two strings s and t, return the number of distinct
subsequences of s which equals t.

A subsequence is formed by deleting some (or no) characters
from the original string without changing the relative
order of the remaining characters.

--------------------------------------------------
Key Observations
--------------------------------------------------
1. At every index, we have 2 choices:
   - Take the character (if it matches)
   - Skip the character
2. If characters match → two possibilities
3. If not match → we can only skip
4. This is a classic pick/not-pick DP problem

--------------------------------------------------
Intuition
--------------------------------------------------
We try to match string t using string s.

At every index i (in s) and j (in t):
- If s[i] == t[j]:
    → either use this character OR skip it
- Else:
    → skip s[i]

We recursively try all possibilities.

--------------------------------------------------
Approach
--------------------------------------------------
1. Start from last indices of both strings
2. If both match → 2 choices
3. If not → skip s
4. Use memoization to avoid recomputation
5. Convert to tabulation

--------------------------------------------------
State Definition
--------------------------------------------------
dp[i][j] =
number of ways to form first j characters of t
using first i characters of s

--------------------------------------------------
Transition
--------------------------------------------------
if(s[i-1] == t[j-1]):
    dp[i][j] = dp[i-1][j-1] + dp[i-1][j]
else:
    dp[i][j] = dp[i-1][j]

--------------------------------------------------
Time Complexity
--------------------------------------------------
Recursion      : O(2^n)
Memoization    : O(n * m)
Tabulation     : O(n * m)

--------------------------------------------------
Space Complexity
--------------------------------------------------
Recursion      : O(n + m)
Memoization    : O(n * m)
Tabulation     : O(n * m)

--------------------------------------------------
Example
--------------------------------------------------
Input:
s = "rabbbit"
t = "rabbit"

Output:
3

Explanation:
There are 3 ways to delete characters from s
to make it equal to t.
*/

class Solution {

    /*
    --------------------------------------------------
    1. Recursion (Brute Force)
    --------------------------------------------------
    */

    public int solveRec(int i, int j, String s, String t) {

        // if t is fully matched
        if(j < 0) return 1;

        // if s is exhausted but t is not
        if(i < 0) return 0;

        if(s.charAt(i) == t.charAt(j)) {
            return solveRec(i - 1, j - 1, s, t)
                 + solveRec(i - 1, j, s, t);
        }

        return solveRec(i - 1, j, s, t);
    }

    /*
    --------------------------------------------------
    2. Memoization (Top Down DP)
    --------------------------------------------------
    */

    public int solveMemo(int i, int j, String s, String t, int[][] dp) {

        if(j < 0) return 1;
        if(i < 0) return 0;

        if(dp[i][j] != -1) return dp[i][j];

        if(s.charAt(i) == t.charAt(j)) {
            return dp[i][j] =
                solveMemo(i - 1, j - 1, s, t, dp)
              + solveMemo(i - 1, j, s, t, dp);
        }

        return dp[i][j] =
            solveMemo(i - 1, j, s, t, dp);
    }

    /*
    --------------------------------------------------
    3. Tabulation (Bottom Up DP)
    --------------------------------------------------
    */

    public int numDistinct(String s, String t) {

        int n = s.length();
        int m = t.length();

        long[][] dp = new long[n + 1][m + 1];

        // Base case
        // Empty t can always be formed
        for(int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= m; j++) {

                if(s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] =
                        dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return (int)dp[n][m];
    }
}