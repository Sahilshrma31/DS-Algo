/*
====================================================
📌 Problem: Longest Common Subsequence (LCS)
====================================================

Given two strings text1 and text2, return the length
of their Longest Common Subsequence.

A subsequence is a sequence that appears in the same
relative order, but not necessarily contiguous.

----------------------------------------------------
🧠 All Approaches Covered
----------------------------------------------------
1️⃣ Recursion (Brute Force)
2️⃣ Memoization (Top-Down DP)
3️⃣ Tabulation (Bottom-Up DP)
4️⃣ Space Optimized DP

----------------------------------------------------
⏱ Time & Space Complexity Summary
----------------------------------------------------

1️⃣ Recursion:
   Time: O(2^(n+m))
   Space: O(n+m) (recursion stack)

2️⃣ Memoization:
   Time: O(n*m)
   Space: O(n*m) + O(n+m)

3️⃣ Tabulation:
   Time: O(n*m)
   Space: O(n*m)

4️⃣ Space Optimized:
   Time: O(n*m)
   Space: O(m)

====================================================
*/

import java.util.*;

class p1 {

    /* =================================================
       1️⃣ RECURSION (Brute Force)
       ================================================= */

    public int lcsRecursive(String s1, String s2) {
        return helperRec(s1, s2, s1.length() - 1, s2.length() - 1);
    }

    private int helperRec(String s1, String s2, int i, int j) {

        // Base case
        if (i < 0 || j < 0) return 0;

        // Match
        if (s1.charAt(i) == s2.charAt(j)) {
            return 1 + helperRec(s1, s2, i - 1, j - 1);
        }

        // No match
        return Math.max(
            helperRec(s1, s2, i - 1, j),
            helperRec(s1, s2, i, j - 1)
        );
    }

    /* =================================================
       2️⃣ MEMOIZATION (Top-Down DP)
       ================================================= */

    public int lcsMemo(String s1, String s2) {

        int n = s1.length();
        int m = s2.length();

        int[][] dp = new int[n][m];
        for (int[] row : dp) Arrays.fill(row, -1);

        return helperMemo(s1, s2, n - 1, m - 1, dp);
    }

    private int helperMemo(String s1, String s2, int i, int j, int[][] dp) {

        if (i < 0 || j < 0) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        if (s1.charAt(i) == s2.charAt(j)) {
            return dp[i][j] =
                1 + helperMemo(s1, s2, i - 1, j - 1, dp);
        }

        return dp[i][j] = Math.max(
            helperMemo(s1, s2, i - 1, j, dp),
            helperMemo(s1, s2, i, j - 1, dp)
        );
    }

    /* =================================================
       3️⃣ TABULATION (Bottom-Up DP)
       ================================================= */

    public int lcsTabulation(String s1, String s2) {

        int n = s1.length();
        int m = s2.length();

        int[][] dp = new int[n + 1][m + 1];

        //dp[*][0] and dp[0][*] are 0 already as we have shfited the indexes the 0 
       //index refers to -1 index where the lcs is 0

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(
                        dp[i - 1][j],
                        dp[i][j - 1]
                    );
                }
            }
        }

        return dp[n][m];
    }

    /* =================================================
       4️⃣ SPACE OPTIMIZED DP
       ================================================= */

    public int lcsSpaceOptimized(String s1, String s2) {

        int n = s1.length();
        int m = s2.length();

        int[] prev = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];

            for (int j = 1; j <= m; j++) {

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(
                        prev[j],
                        curr[j - 1]
                    );
                }
            }
            prev = curr;
        }

        return prev[m];
    }
}
