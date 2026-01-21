// ------------------------------------------------------------
// Problem: Unique Paths II (Maze with Obstacles)
// ------------------------------------------------------------
// You are given an m x n grid where:
// 0 -> empty cell
// 1 -> obstacle
// You can move only RIGHT or DOWN.
// Find the number of unique paths from (0,0) to (m-1,n-1).
//
// Note:
// - If start or end cell has an obstacle, answer = 0
//
// ------------------------------------------------------------
// Approaches Covered (Striver Style):
// 1) Recursive (Exponential)
// 2) Memoization (Top-Down DP)
// 3) Tabulation (Bottom-Up DP)
// 4) Space Optimized DP
//
// Time & Space Complexity:
// Let m = rows, n = columns
//
// 1) Recursive
//    Time: O(2^(m*n))
//    Space: O(path length)
//
// 2) Memoization
//    Time: O(m*n)
//    Space: O(m*n) + recursion stack
//
// 3) Tabulation
//    Time: O(m*n)
//    Space: O(m*n)
//
// 4) Space Optimized
//    Time: O(m*n)
//    Space: O(n)
//
// ------------------------------------------------------------

import java.util.*;

class Solution {

    static int MOD = (int) 1e9 + 7;

    // ============================
    // 1) RECURSIVE APPROACH
    // ============================
    public int uniquePathsRecursive(int i, int j, int[][] grid) {
        if (i < 0 || j < 0 || grid[i][j] == 1) return 0;
        if (i == 0 && j == 0) return 1;

        int up = uniquePathsRecursive(i - 1, j, grid);
        int left = uniquePathsRecursive(i, j - 1, grid);

        return up + left;
    }

    // ============================
    // 2) MEMOIZATION (TOP-DOWN)
    // ============================
    public int uniquePathsMemo(int i, int j, int[][] grid, int[][] dp) {
        if (i < 0 || j < 0 || grid[i][j] == 1) return 0;
        if (i == 0 && j == 0) return 1;

        if (dp[i][j] != -1) return dp[i][j];

        int up = uniquePathsMemo(i - 1, j, grid, dp);
        int left = uniquePathsMemo(i, j - 1, grid, dp);

        return dp[i][j] = (up + left) % MOD;
    }

    // ============================
    // 3) TABULATION (BOTTOM-UP)
    // LOGIC EXACTLY LIKE YOU SHARED
    // ============================
    public int uniquePathsTabulation(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];

        if (obstacleGrid[0][0] == 1) return 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                }
                else if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                }
                else {
                    int up = 0, left = 0;

                    if (i > 0) up = dp[i - 1][j];
                    if (j > 0) left = dp[i][j - 1];

                    dp[i][j] = (up + left) % MOD;
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    // ============================
    // 4) SPACE OPTIMIZED DP
    // ============================
    public int uniquePathsSpaceOptimized(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[] prev = new int[n];

        if (obstacleGrid[0][0] == 1) return 0;

        for (int i = 0; i < m; i++) {
            int[] curr = new int[n];
            for (int j = 0; j < n; j++) {

                if (obstacleGrid[i][j] == 1) {
                    curr[j] = 0;
                }
                else if (i == 0 && j == 0) {
                    curr[j] = 1;
                }
                else {
                    int up = (i > 0) ? prev[j] : 0;
                    int left = (j > 0) ? curr[j - 1] : 0;
                    curr[j] = (up + left) % MOD;
                }
            }
            prev = curr;
        }

        return prev[n - 1];
    }

    // ============================
    // MAIN FUNCTION (LeetCode/GFG)
    // ============================
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {

        // Uncomment ANY approach you want to use 👇

        // ---- Recursive (Not recommended for large input) ----
        // return uniquePathsRecursive(
        //     obstacleGrid.length - 1,
        //     obstacleGrid[0].length - 1,
        //     obstacleGrid
        // );

        // ---- Memoization ----
        // int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
        // for (int[] row : dp) Arrays.fill(row, -1);
        // return uniquePathsMemo(
        //     obstacleGrid.length - 1,
        //     obstacleGrid[0].length - 1,
        //     obstacleGrid,
        //     dp
        // );

        // ---- Tabulation (Recommended) ----
        return uniquePathsTabulation(obstacleGrid);

        // ---- Space Optimized ----
        // return uniquePathsSpaceOptimized(obstacleGrid);
    }
}

