/*
====================================================
📌 Problem: Triangle (Minimum Path Sum)

Given a triangle array, return the minimum path sum
from top to bottom.

At each step, you may move to:
- the same index
- the next index

Example:
Input:
[
 [2],
 [3,4],
 [6,5,7],
 [4,1,8,3]
]

Output: 11
Path: 2 → 3 → 5 → 1

====================================================
🧠 Intuition:
At every cell (i, j), you have only two choices:
1️⃣ Go down      → (i+1, j)
2️⃣ Go diagonal  → (i+1, j+1)

We choose the minimum cost path recursively and
optimize using DP.

====================================================
🧩 Approaches Covered:
1️⃣ Recursive (Top-Down)
2️⃣ Memoization (Top-Down DP)
3️⃣ Tabulation (Bottom-Up DP)
4️⃣ Space Optimized DP

====================================================
*/

import java.util.*;

class Solution {

    /* ====================================================
       1️⃣ RECURSIVE APPROACH (Top-Down)
       ==================================================== */
    public int minimumTotalRecursive(List<List<Integer>> triangle) {
        return helper(0, 0, triangle);
    }

    private int helper(int i, int j, List<List<Integer>> triangle) {
        // Base case: last row
        if (i == triangle.size() - 1) {
            return triangle.get(i).get(j);
        }

        int down = helper(i + 1, j, triangle);
        int diag = helper(i + 1, j + 1, triangle);

        return triangle.get(i).get(j) + Math.min(down, diag);
    }

    /*
    ⏱ Time Complexity: O(2^n)
    💾 Space Complexity: O(n) (recursion stack)
    */


    /* ====================================================
       2️⃣ MEMOIZATION (Top-Down DP)
       ==================================================== */
    public int minimumTotalMemo(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];

        for (int[] row : dp)
            Arrays.fill(row, -1);

        return memoHelper(0, 0, triangle, dp);
    }

    private int memoHelper(int i, int j, List<List<Integer>> triangle, int[][] dp) {
        if (i == triangle.size() - 1) {
            return triangle.get(i).get(j);
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int down = memoHelper(i + 1, j, triangle, dp);
        int diag = memoHelper(i + 1, j + 1, triangle, dp);

        return dp[i][j] =
                triangle.get(i).get(j) + Math.min(down, diag);
    }

    /*
    ⏱ Time Complexity: O(n²)
    💾 Space Complexity: O(n²) + O(n) recursion stack
    */


    /* ====================================================
       3️⃣ TABULATION (Bottom-Up DP)
       ==================================================== */
    public int minimumTotalTabulation(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];

        // Base case: copy last row
        for (int j = 0; j < n; j++) {
            dp[n - 1][j] = triangle.get(n - 1).get(j);
        }

        // Build DP from bottom to top
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = triangle.get(i).get(j)
                        + Math.min(dp[i + 1][j], dp[i + 1][j + 1]);
            }
        }

        return dp[0][0];
    }

    /*
    ⏱ Time Complexity: O(n²)
    💾 Space Complexity: O(n²)
    */


    /* ====================================================
       4️⃣ SPACE OPTIMIZED DP
       ==================================================== */
    public int minimumTotalSpaceOptimized(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] prev = new int[n];

        // Initialize with last row
        for (int j = 0; j < n; j++) {
            prev[j] = triangle.get(n - 1).get(j);
        }

        for (int i = n - 2; i >= 0; i--) {
            int[] curr = new int[n];
            for (int j = 0; j <= i; j++) {
                curr[j] = triangle.get(i).get(j)
                        + Math.min(prev[j], prev[j + 1]);
            }
            prev = curr;
        }

        return prev[0];
    }

    /*
    ⏱ Time Complexity: O(n²)
    💾 Space Complexity: O(n)
    */

}
