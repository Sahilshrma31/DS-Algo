/*
====================================================
📌 Problem: Minimum falling Path Sum
====================================================

Given an N x N matrix, you can start from any cell
in the last row and move to the top row.

From cell (i, j), you can move to:
1) (i-1, j)     -> up
2) (i-1, j-1)   -> up-left diagonal
3) (i-1, j+1)   -> up-right diagonal

Find:
- Minimum Falling Path Sum
- Maximum Falling Path Sum

----------------------------------------------------
🧠 Intuition (Striver Style: Bottom → Top)
----------------------------------------------------
Instead of starting from the top row, we start from
the last row and move upwards.

At each cell (i, j), we ask:
"What is the best path sum to reach here from row 0?"

We try all 3 possible moves from the previous row
and take min/max accordingly.

----------------------------------------------------
🧩 Approaches Covered
----------------------------------------------------
1️⃣ Recursive (Brute Force)
2️⃣ Memoization (Top-Down DP)
3️⃣ Tabulation (Bottom-Up DP)
4️⃣ Space Optimized DP

----------------------------------------------------
⏱ Time & Space Complexity
----------------------------------------------------
Let N = number of rows (N x N matrix)

1️⃣ Recursion:
   Time: O(3^N)
   Space: O(N) (recursion stack)

2️⃣ Memoization:
   Time: O(N * N)
   Space: O(N * N) + O(N) stack

3️⃣ Tabulation:
   Time: O(N * N)
   Space: O(N * N)

4️⃣ Space Optimized:
   Time: O(N * N)
   Space: O(N)

====================================================
*/

import java.util.*;

class Solution {

    /* =======================
       1️⃣ RECURSIVE APPROACH
       ======================= */

    public int minFallingPathSumRecursive(int[][] matrix) {
        int n = matrix.length;
        int ans = Integer.MAX_VALUE;

        for (int j = 0; j < n; j++) {
            ans = Math.min(ans, helperRec(matrix, n - 1, j));
        }
        return ans;
    }

    private int helperRec(int[][] matrix, int i, int j) {
        if (j < 0 || j >= matrix.length) return (int) 1e9;
        if (i == 0) return matrix[0][j];

        int up = helperRec(matrix, i - 1, j);
        int leftDiag = helperRec(matrix, i - 1, j - 1);
        int rightDiag = helperRec(matrix, i - 1, j + 1);

        return matrix[i][j] + Math.min(up, Math.min(leftDiag, rightDiag));
    }

    /* =========================
       2️⃣ MEMOIZATION APPROACH
       ========================= */

    public int minFallingPathSumMemo(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][n];

        for (int[] row : dp) Arrays.fill(row, Integer.MIN_VALUE);

        int ans = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            ans = Math.min(ans, helperMemo(matrix, n - 1, j, dp));
        }
        return ans;
    }

    private int helperMemo(int[][] matrix, int i, int j, int[][] dp) {
        if (j < 0 || j >= matrix.length) return (int) 1e9;
        if (i == 0) return matrix[0][j];

        if (dp[i][j] != Integer.MIN_VALUE) return dp[i][j];

        int up = helperMemo(matrix, i - 1, j, dp);
        int leftDiag = helperMemo(matrix, i - 1, j - 1, dp);
        int rightDiag = helperMemo(matrix, i - 1, j + 1, dp);

        return dp[i][j] =
                matrix[i][j] + Math.min(up, Math.min(leftDiag, rightDiag));
    }

    /* ======================
       3️⃣ TABULATION (DP)
       ====================== */

    public int minFallingPathSumTabulation(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][n];

        // Base case: first row
        for (int j = 0; j < n; j++) {
            dp[0][j] = matrix[0][j];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int up = dp[i - 1][j];
                int leftDiag = j > 0 ? dp[i - 1][j - 1] : (int) 1e9;
                int rightDiag = j < n - 1 ? dp[i - 1][j + 1] : (int) 1e9;

                dp[i][j] = matrix[i][j] +
                        Math.min(up, Math.min(leftDiag, rightDiag));
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            ans = Math.min(ans, dp[n - 1][j]);
        }
        return ans;
    }

    /* =============================
       4️⃣ SPACE OPTIMIZED DP
       ============================= */

    public int minFallingPathSumSpaceOptimized(int[][] matrix) {
        int n = matrix.length;
        int[] prev = new int[n];

        for (int j = 0; j < n; j++) {
            prev[j] = matrix[0][j];
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[n];
            for (int j = 0; j < n; j++) {
                int up = prev[j];
                int leftDiag = j > 0 ? prev[j - 1] : (int) 1e9;
                int rightDiag = j < n - 1 ? prev[j + 1] : (int) 1e9;

                curr[j] = matrix[i][j] +
                        Math.min(up, Math.min(leftDiag, rightDiag));
            }
            prev = curr;
        }

        int ans = Integer.MAX_VALUE;
        for (int val : prev) ans = Math.min(ans, val);
        return ans;
    }
}
