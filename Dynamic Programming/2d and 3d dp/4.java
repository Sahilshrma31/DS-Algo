/*
===========================================================
📌 Problem: Minimum Path Sum (Grid DP)
===========================================================

You are given an m x n grid filled with non-negative numbers.
Find a path from top-left to bottom-right which minimizes
the sum of all numbers along its path.

You can only move:
➡ Right
⬇ Down

-----------------------------------------------------------
🧠 Intuition
-----------------------------------------------------------
To reach any cell (i, j), you must come from:
- Top cell (i-1, j)
- Left cell (i, j-1)

So:
minPath(i, j) = grid[i][j] + min(top, left)

-----------------------------------------------------------
🛠 Approaches Covered
-----------------------------------------------------------
1️⃣ Recursive (Brute Force)
2️⃣ Memoization (Top-Down DP)
3️⃣ Tabulation (Bottom-Up DP)
4️⃣ Space Optimized DP

-----------------------------------------------------------
⏱ Time & Space Complexity Summary
-----------------------------------------------------------
Approach        Time Complexity     Space Complexity
-----------------------------------------------------------
Recursion       O(2^(m*n))           O(path length)
Memoization     O(m*n)               O(m*n) + recursion
Tabulation      O(m*n)               O(m*n)
Space Optimized O(m*n)               O(n)

===========================================================
*/

class Solution {

    /* ----------------------------------------------------
       1️⃣ RECURSIVE APPROACH
       ----------------------------------------------------
       Time: Exponential
       Space: O(path length) due to recursion stack
    */
    public int minPathSumRecursive(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        return helper(grid, m - 1, n - 1);
    }

    private int helper(int[][] grid, int i, int j) {
        if (i == 0 && j == 0) return grid[0][0];
        if (i < 0 || j < 0) return (int) 1e9;

        int up = helper(grid, i - 1, j);
        int left = helper(grid, i, j - 1);

        return grid[i][j] + Math.min(up, left);
    }


    /* ----------------------------------------------------
       2️⃣ MEMOIZATION (TOP-DOWN DP)
       ----------------------------------------------------
       Time: O(m*n)
       Space: O(m*n) + recursion stack
    */
    public int minPathSumMemo(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }

        return memoHelper(grid, m - 1, n - 1, dp);
    }

    private int memoHelper(int[][] grid, int i, int j, int[][] dp) {
        if (i == 0 && j == 0) return grid[0][0];
        if (i < 0 || j < 0) return (int) 1e9;

        if (dp[i][j] != -1) return dp[i][j];

        int up = memoHelper(grid, i - 1, j, dp);
        int left = memoHelper(grid, i, j - 1, dp);

        return dp[i][j] = grid[i][j] + Math.min(up, left);
    }


    /* ----------------------------------------------------
       3️⃣ TABULATION (BOTTOM-UP DP)
       ----------------------------------------------------
       Time: O(m*n)
       Space: O(m*n)
    */
    public int minPathSumTabulation(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                } else {
                    int up = i > 0 ? dp[i - 1][j] : (int) 1e9;
                    int left = j > 0 ? dp[i][j - 1] : (int) 1e9;

                    dp[i][j] = grid[i][j] + Math.min(up, left);
                }
            }
        }
        return dp[m - 1][n - 1];
    }


    /* ----------------------------------------------------
       4️⃣ SPACE OPTIMIZED DP
       ----------------------------------------------------
       Time: O(m*n)
       Space: O(n)
    */
    public int minPathSumSpaceOptimized(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[] prev = new int[n];

        for (int i = 0; i < m; i++) {
            int[] curr = new int[n];
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    curr[j] = grid[i][j];
                } else {
                    int up = i > 0 ? prev[j] : (int) 1e9;
                    int left = j > 0 ? curr[j - 1] : (int) 1e9;

                    curr[j] = grid[i][j] + Math.min(up, left);
                }
            }
            prev = curr;
        }
        return prev[n - 1];
    }
}

