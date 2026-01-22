/*
====================================================
📌 Problem: Count Square Submatrices with All Ones
(LeetCode 1277)
====================================================

Given a binary matrix, count the number of square submatrices
that have **all 1s**.

====================================================
🧠 Key Insight
====================================================

For each cell (i, j) as the **bottom-right corner** of a square:
- The size of the largest square ending here depends on:
  - top        → (i-1, j)
  - left       → (i, j-1)
  - top-left   → (i-1, j-1)

If matrix[i][j] == 1:
dp[i][j] = 1 + min(top, left, top-left)

Sum of all dp[i][j] = total number of square submatrices.

====================================================
🧠 Approaches Covered
====================================================

1️⃣ Recursion (Brute Force) 
2️⃣ Memoization (Top-Down DP)  
3️⃣ Tabulation (Bottom-Up DP) BEST  

====================================================
*/

import java.util.*;

class Solution {

    /*
    ====================================================
    1️⃣ RECURSION (Brute Force) 
    ====================================================
    f(i, j) = largest square ending at (i, j)
    ====================================================
    Time Complexity: Exponential (TLE)
    Space Complexity: O(n + m) recursion stack
    ====================================================
    */
    private int solveRec(int i, int j, int[][] matrix) {

        if (i < 0 || j < 0) return 0;
        if (matrix[i][j] == 0) return 0;

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
    private int solveMemo(int i, int j, int[][] matrix, int[][] dp) {

        if (i < 0 || j < 0) return 0;
        if (matrix[i][j] == 0) return 0;

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
    private int solveTab(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        int[][] dp = new int[n][m];
        int totalSquares = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (matrix[i][j] == 1) {

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

                    totalSquares += dp[i][j];
                }
            }
        }

        return totalSquares;
    }

    /*
    ====================================================
    🚀 MAIN FUNCTION (LeetCode Expected)
    ====================================================
    */
    public int countSquares(int[][] matrix) {

        // ✅ Best approach
        return solveTab(matrix);

        /*
        // For learning/debugging:

        // 1️⃣ Recursion (TLE)
        int n = matrix.length, m = matrix[0].length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sum += solveRec(i, j, matrix);
            }
        }
        return sum;

        // 2️⃣ Memoization
        int[][] dp = new int[n][m];
        for (int[] row : dp) Arrays.fill(row, -1);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans += solveMemo(i, j, matrix, dp);
            }
        }
        return ans;
        */
    }
}
