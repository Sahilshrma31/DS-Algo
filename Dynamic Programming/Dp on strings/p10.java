import java.util.*;

/*
Problem: Wildcard Matching (LeetCode 44)

--------------------------------------------------
Problem Description
--------------------------------------------------
Given an input string s and a pattern p, implement
wildcard pattern matching with support for:

'?' → matches any single character  
'*' → matches any sequence of characters (including empty)

Return true if the entire string matches the pattern.

--------------------------------------------------
Key Observations
--------------------------------------------------
1. '?' matches exactly one character.
2. '*' can match:
   - empty string
   - one character
   - multiple characters
3. This is a classic DP matching problem.
4. Similar to string matching with flexible transitions.

--------------------------------------------------
Intuition
--------------------------------------------------
We compare string s and pattern p from the end.

- If characters match OR pattern has '?'
  → move both pointers

- If pattern has '*'
  → 2 choices:
     1. Treat '*' as empty → move pattern
     2. Match one char → move string

- If characters don't match → return false

--------------------------------------------------
Approach
--------------------------------------------------
1. Start from last indices of s and p.
2. Handle base cases carefully:
   - Both exhausted → true
   - Pattern exhausted → false
   - String exhausted → check if remaining pattern is all '*'
3. Use recursion → memoization → tabulation.

--------------------------------------------------
State Definition
--------------------------------------------------
dp[i][j] =
whether first i characters of s match
first j characters of p

--------------------------------------------------
Transition
--------------------------------------------------
if(p[j-1] == s[i-1] OR p[j-1] == '?'):
    dp[i][j] = dp[i-1][j-1]

else if(p[j-1] == '*'):
    dp[i][j] = dp[i][j-1] OR dp[i-1][j]

else:
    dp[i][j] = false

--------------------------------------------------
Time Complexity
--------------------------------------------------
Recursion      : Exponential
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
s = "adceb"
p = "*a*b"

Output:
true

Explanation:
'*' → matches empty
'a' → matches 'a'
'*' → matches "dce"
'b' → matches 'b'
*/

class Solution {

    /*
    --------------------------------------------------
    Helper: Check if pattern[0..j] is all '*'
    --------------------------------------------------
    */

    private boolean isAllStars(String p, int j) {
        for(int i = 0; i <= j; i++) {
            if(p.charAt(i) != '*') return false;
        }
        return true;
    }

    /*
    --------------------------------------------------
    1. Recursion (Brute Force)
    --------------------------------------------------
    */

    public boolean solveRec(int i, int j, String s, String p) {

        // both exhausted
        if(i < 0 && j < 0) return true;

        // pattern exhausted
        if(j < 0) return false;

        // string exhausted
        if(i < 0) return isAllStars(p, j);

        if(s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            return solveRec(i - 1, j - 1, s, p);
        }

        if(p.charAt(j) == '*') {
            return solveRec(i, j - 1, s, p)      // '*' as empty
                || solveRec(i - 1, j, s, p);    // '*' matches char
        }

        return false;
    }

    /*
    --------------------------------------------------
    2. Memoization (Top Down DP)
    --------------------------------------------------
    */

    public boolean solveMemo(int i, int j, String s, String p, int[][] dp) {

        if(i < 0 && j < 0) return true;
        if(j < 0) return false;
        if(i < 0) return isAllStars(p, j);

        if(dp[i][j] != -1) return dp[i][j] == 1;

        boolean ans;

        if(s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            ans = solveMemo(i - 1, j - 1, s, p, dp);
        }
        else if(p.charAt(j) == '*') {
            ans = solveMemo(i, j - 1, s, p, dp)
               || solveMemo(i - 1, j, s, p, dp);
        }
        else {
            ans = false;
        }

        dp[i][j] = ans ? 1 : 0;
        return ans;
    }

    /*
    --------------------------------------------------
    3. Tabulation (Bottom Up DP)
    --------------------------------------------------
    */

    public boolean isMatch(String s, String p) {

        int n = s.length();
        int m = p.length();

        boolean[][] dp = new boolean[n + 1][m + 1];

        // Base case
        dp[0][0] = true;

        // pattern vs empty string
        for(int j = 1; j <= m; j++) {
            dp[0][j] = isAllStars(p, j - 1);
        }

        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= m; j++) {

                if(s.charAt(i - 1) == p.charAt(j - 1)
                    || p.charAt(j - 1) == '?') {

                    dp[i][j] = dp[i - 1][j - 1];
                }
                else if(p.charAt(j - 1) == '*') {

                    dp[i][j] =
                        dp[i][j - 1]   // '*' → empty
                     || dp[i - 1][j]; // '*' → match char
                }
                else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[n][m];
    }

    /*
    --------------------------------------------------
    4. Space Optimized DP
    --------------------------------------------------
    */

    public boolean isMatchSpaceOptimized(String s, String p) {

        int n = s.length();
        int m = p.length();

        boolean[] prev = new boolean[m + 1];
        prev[0] = true;

        for(int j = 1; j <= m; j++) {
            prev[j] = isAllStars(p, j - 1);
        }

        for(int i = 1; i <= n; i++) {

            boolean[] curr = new boolean[m + 1];
            curr[0] = false;

            for(int j = 1; j <= m; j++) {

                if(s.charAt(i - 1) == p.charAt(j - 1)
                    || p.charAt(j - 1) == '?') {

                    curr[j] = prev[j - 1];
                }
                else if(p.charAt(j - 1) == '*') {

                    curr[j] =
                        curr[j - 1]
                     || prev[j];
                }
                else {
                    curr[j] = false;
                }
            }

            prev = curr;
        }

        return prev[m];
    }
}