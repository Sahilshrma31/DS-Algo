/*
Problem: Number of Increasing Paths in a Grid
(LeetCode 2328)

Problem Explanation:
You are given an m x n grid of integers.
A path is considered valid if:
- It starts at any cell
- Moves only in 4 directions (up, down, left, right)
- Each next cell has a strictly smaller value than the current cell

A single cell itself is also considered a valid path.

Task:
Count the total number of strictly increasing paths in the grid.
Return the answer modulo 1e9 + 7.

--------------------------------------------------
Core Intuition:
--------------------------------------------------
This is a DP on grid problem with ordering constraints.

Key observations:
- From a cell (i, j), you can only move to neighbors with smaller values
- This forms a DAG (no cycles because values strictly decrease)
- For each cell, we want to know:
  "How many valid paths start from this cell?"

The final answer is the sum of paths starting from every cell.

--------------------------------------------------
DP State Definition:
--------------------------------------------------
dp[i][j] = number of increasing paths starting from cell (i, j)

Transition:
dp[i][j] = 1 (the cell itself)
         + sum(dp[ni][nj]) for all neighbors (ni, nj)
           such that grid[ni][nj] < grid[i][j]

--------------------------------------------------
Approaches Covered:
--------------------------------------------------
1. Pure Recursion (Brute Force)
2. Memoization (DFS + DP)
3. Tabulation (Topological / Value-based DP)

--------------------------------------------------
Time Complexity:
--------------------------------------------------
Recursion        : Exponential (TLE)
Memoization     : O(m * n)
Tabulation      : O(m * n * log(m*n)) due to sorting

--------------------------------------------------
Space Complexity:
--------------------------------------------------
Memoization     : O(m * n)
Tabulation      : O(m * n)

--------------------------------------------------
*/

import java.util.*;

class Solution {

    int m, n;
    static final int MOD = 1_000_000_007;

    int[][] directions = {
        {-1, 0}, {1, 0},
        {0, -1}, {0, 1}
    };

    /*
    --------------------------------------------------
    1) RECURSION (Brute Force)
    --------------------------------------------------
    f(i, j) = number of increasing paths from cell (i, j)
    --------------------------------------------------
    */
    private int dfsRec(int[][] grid, int i, int j) {

        int ans = 1; // path of length 1 (cell itself)

        for (int[] d : directions) {
            int ni = i + d[0];
            int nj = j + d[1];

            if (isSafe(ni, nj) && grid[ni][nj] < grid[i][j]) {
                ans = (ans + dfsRec(grid, ni, nj)) % MOD;
            }
        }

        return ans;
    }

    /*
    --------------------------------------------------
    2) MEMOIZATION (DFS + DP)
    --------------------------------------------------
    dp[i][j] stores result of dfs(i, j)
    --------------------------------------------------
    */
    int[][] dp;

    private int dfsMemo(int[][] grid, int i, int j) {

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int ans = 1;

        for (int[] d : directions) {
            int ni = i + d[0];
            int nj = j + d[1];

            if (isSafe(ni, nj) && grid[ni][nj] < grid[i][j]) {
                ans = (ans + dfsMemo(grid, ni, nj)) % MOD;
            }
        }

        return dp[i][j] = ans;
    }

    /*
    --------------------------------------------------
    3) TABULATION (Bottom-Up DP)
    --------------------------------------------------
    Idea:
    - Sort all cells by their value in increasing order
    - For each cell, push its dp value to neighbors with larger values
    --------------------------------------------------
    */
    private int solveTab(int[][] grid) {

        int[][] dp = new int[m][n];

        // Each cell contributes at least 1 path
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], 1);
        }

        List<int[]> cells = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cells.add(new int[]{grid[i][j], i, j});
            }
        }

        // Sort by cell value (ascending)
        Collections.sort(cells, (a, b) -> Integer.compare(a[0], b[0]));

        for (int[] cell : cells) {
            int val = cell[0];
            int i = cell[1];
            int j = cell[2];

            for (int[] d : directions) {
                int ni = i + d[0];
                int nj = j + d[1];

                if (isSafe(ni, nj) && grid[ni][nj] > val) {
                    dp[ni][nj] = (dp[ni][nj] + dp[i][j]) % MOD;
                }
            }
        }

        int result = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result = (result + dp[i][j]) % MOD;
            }
        }

        return result;
    }

    private boolean isSafe(int i, int j) {
        return i >= 0 && i < m && j >= 0 && j < n;
    }

    /*
    --------------------------------------------------
    MAIN FUNCTION
    --------------------------------------------------
    */
    public int countPaths(int[][] grid) {

        m = grid.length;
        n = grid[0].length;

        // -------- Memoization (Best Practical Approach) --------
        dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], -1);
        }

        int result = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result = (result + dfsMemo(grid, i, j)) % MOD;
            }
        }

        return result;

        /*
        // -------- Recursion (TLE) --------
        // int ans = 0;
        // for (int i = 0; i < m; i++) {
        //     for (int j = 0; j < n; j++) {
        //         ans = (ans + dfsRec(grid, i, j)) % MOD;
        //     }
        // }
        // return ans;

        // -------- Tabulation --------
        // return solveTab(grid);
        */
    }
}

/*
--------------------------------------------------
Example Test Case:
--------------------------------------------------

Input:
grid = [
  [1, 1],
  [3, 4]
]

Valid increasing paths:
Single cells: 4
Other paths: [1->3], [1->4], [3->4]

Output:
8

--------------------------------------------------
*/

