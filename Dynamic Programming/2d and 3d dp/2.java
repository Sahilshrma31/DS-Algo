/**
 * ============================================================
 * 📌 Problem: Grid Unique Paths
 * ============================================================
 * You are given a grid of size m x n.
 * You start from the top-left cell (0,0) and want to reach
 * the bottom-right cell (m-1, n-1).
 *
 * You can only move:
 * 👉 Right
 * 👉 Down
 *
 * Count the total number of unique paths.
 *
 * ============================================================
 * 🧠 Intuition (Striver Style)
 * ============================================================
 * At any cell (i, j), you can reach there only from:
 *  - Top cell (i-1, j)
 *  - Left cell (i, j-1)
 *
 * So:
 * paths(i, j) = paths(i-1, j) + paths(i, j-1)
 *
 * ============================================================
 * 🚀 Approaches Covered
 * ============================================================
 * 1️⃣ Recursion (Brute Force)
 * 2️⃣ Recursion + Memoization (Top-Down DP)
 * 3️⃣ Tabulation (Bottom-Up DP)
 * 4️⃣ Space Optimized DP
 *
 * ============================================================
 * ⏱️ Time & Space Complexity
 * ============================================================
 *
 * 1️⃣ Recursion
 *    Time: O(2^(m+n))
 *    Space: O(m+n) [Recursion stack]
 *
 * 2️⃣ Memoization
 *    Time: O(m*n)
 *    Space: O(m*n) + recursion stack
 *
 * 3️⃣ Tabulation
 *    Time: O(m*n)
 *    Space: O(m*n)
 *
 * 4️⃣ Space Optimized
 *    Time: O(m*n)
 *    Space: O(n)
 *
 * ============================================================
 */

class Solution {

    /* =========================================================
     * 1️⃣ Recursive Approach (Brute Force)
     * ========================================================= */
    public int uniquePathsRec(int m, int n) {
        return solveRec(m - 1, n - 1);
    }

    private int solveRec(int i, int j) {
        if (i == 0 && j == 0) return 1;
        if (i < 0 || j < 0) return 0;

        int up = solveRec(i - 1, j);
        int left = solveRec(i, j - 1);

        return up + left;
    }

    /* =========================================================
     * 2️⃣ Memoization (Top-Down DP)
     * ========================================================= */
    public int uniquePathsMemo(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }
        return solveMemo(m - 1, n - 1, dp);
    }

    private int solveMemo(int i, int j, int[][] dp) {
        if (i == 0 && j == 0) return 1;
        if (i < 0 || j < 0) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        int up = solveMemo(i - 1, j, dp);
        int left = solveMemo(i, j - 1, dp);

        return dp[i][j] = up + left;
    }

    /* =========================================================
     * 3️⃣ Tabulation (Bottom-Up DP)
     * ========================================================= */
    public int uniquePathsTab(int m, int n) {
        int[][] dp = new int[m][n];

        // Base case
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) continue;

                int up = (i > 0) ? dp[i - 1][j] : 0;
                int left = (j > 0) ? dp[i][j - 1] : 0;

                dp[i][j] = up + left;
            }
        }

        return dp[m - 1][n - 1];
    }

    /* =========================================================
     * 4️⃣ Space Optimized DP
     * ========================================================= */
    public int uniquePathsSpaceOptimized(int m, int n) {
        int[] prev = new int[n];

        for (int i = 0; i < m; i++) {
            int[] curr = new int[n];
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    curr[j] = 1;
                } else {
                    int up = (i > 0) ? prev[j] : 0;
                    int left = (j > 0) ? curr[j - 1] : 0;
                    curr[j] = up + left;
                }
            }
            prev = curr;
        }

        return prev[n - 1];
    }
}

