/*
====================================================
📌 Problem: Maximal Square
(LeetCode 221)
====================================================

Given a binary matrix filled with characters '0' and '1',
find the **largest square containing only '1's** and
return its **area**.

====================================================
🧠 Key Insight
====================================================

For each cell (i, j) as the **bottom-right corner** of a square:
- The maximum square side length ending here depends on:
  - top        → (i-1, j)
  - left       → (i, j-1)
  - top-left   → (i-1, j-1)

If matrix[i][j] == '1':
dp[i][j] = 1 + min(top, left, top-left)

Track the **maximum side length**, and finally return:
(maxSide)²

====================================================
🧠 Approaches Covered
====================================================

1️⃣ Recursion (Brute Force) ❌  
2️⃣ Memoization (Top-Down DP)  
3️⃣ Tabulation (Bottom-Up DP) ✅ BEST  

====================================================
*/

import java.util.*;

class Solution {

    /*
    ====================================================
    1️⃣ RECURSION (Brute Force) ❌
    ====================================================
    f(i, j) = largest square ending at (i, j)
    ====================================================
    Time Complexity: Exponential (TLE)
    Space Complexity: O(n + m) recursion stack
    ====================================================
    */
    private int solveRec(int i, int j, char[][] matrix) {

        if (i < 0 || j < 0) return 0;
        if (matrix[i][j] == '0') return 0;

        return 1 + Math.min(
                solveRec(i - 1, j, matrix),
                Math.min(
                        solveRec(i, j - 1, matrix),
                        solveRec(i - 1, j - 1, matrix)
                )
        );
    }

    /*
    ====================================================
    2️⃣ MEMOIZATION (Top-Down DP)
    ====================================================
    dp[i][j] = largest square ending at (i, j)
    ====================================================
    Time Complexity: O(n * m)
    Space Complexity: O(n * m) + recursion stack
    ====================================================
    */
    private int solveMemo(int i, int j,
                          char[][] matrix, int[][] dp) {

        if (i < 0 || j < 0) return 0;
        if (matrix[i][j] == '0') return 0;

        if (dp[i][j] != -1) return dp[i][j];

        return dp[i][j] = 1 + Math.min(
                solveMemo(i - 1, j, matrix, dp),
                Math.min(
                        solveMemo(i, j - 1, matrix, dp),
                        solveMemo(i - 1, j - 1, matrix, dp)
                )
        );
    }

    /*
    ====================================================
    3️⃣ TABULATION (Bottom-Up DP)  BEST
    ====================================================
    dp[i][j] = largest square ending at (i, j)
    ====================================================
    Time Complexity: O(n * m)
    Space Complexity: O(n * m)
    ====================================================
    */
    private int solveTab(char[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        int[][] dp = new int[n][m];
        int maxSide = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (matrix[i][j] == '1') {

                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 1 + Math.min(
                                dp[i - 1][j],
                                Math.min(
                                        dp[i][j - 1],
                                        dp[i - 1][j - 1]
                                )
                        );
                    }

                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }

        return maxSide * maxSide;
    }

    /*
    ====================================================
    🚀 MAIN FUNCTION (LeetCode Expected)
    ====================================================
    */
    public int maximalSquare(char[][] matrix) {

        //  Best approach
        return solveTab(matrix);

        /*
        // For learning/debugging:

        // 1️⃣ Recursion (TLE)
        int n = matrix.length, m = matrix[0].length;
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max = Math.max(max, solveRec(i, j, matrix));
            }
        }
        return max * max;

        // 2️⃣ Memoization
        int[][] dp = new int[n][m];
        for (int[] row : dp) Arrays.fill(row, -1);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans = Math.max(ans, solveMemo(i, j, matrix, dp));
            }
        }
        return ans * ans;
        */
    }
}
