import java.util.*;

/*
Problem: Champagne Tower (LeetCode 799)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You pour "poured" cups of champagne into the top glass of a pyramid.
Each glass can hold at most 1 cup of champagne.
Any extra champagne overflows equally to the two glasses below it.

Return how full the glass at position (query_row, query_glass) is.

--------------------------------------------------
Intuition:
--------------------------------------------------
Each glass receives overflow only from two parents:
1) upper-left parent (row-1, col-1)
2) upper-right parent (row-1, col)

If a parent glass has more than 1 cup, the extra flows down equally.
So the amount in current glass is:

max(0, (parent - 1) / 2) from each parent.

--------------------------------------------------
Approach 1: Recursion + Memoization (Top-Down DP)
--------------------------------------------------
- Define dp[i][j] = amount of champagne in glass (i, j)
- Recursively compute dp from parents
- Store results to avoid recomputation

Time Complexity: O(query_row^2)
Space Complexity: O(query_row^2) for memo + recursion stack

--------------------------------------------------
Approach 2: Tabulation (Bottom-Up DP)
--------------------------------------------------
- Use dp table
- dp[0][0] = poured
- For each glass, push overflow to the next row
- Finally answer is min(1, dp[query_row][query_glass])

Time Complexity: O(query_row^2)
Space Complexity: O(query_row^2)

--------------------------------------------------
Test Cases:
--------------------------------------------------
Input: poured = 1, row = 1, glass = 1
Output: 0.0

Input: poured = 2, row = 1, glass = 1
Output: 0.5

Input: poured = 100, row = 4, glass = 2
Output: 1.0

--------------------------------------------------
*/

class Solution {

    // -----------------------------
    // Approach 1: Recursion + Memo
    // -----------------------------

    int initial;
    double[][] memo = new double[101][101]; // safe size

    public double champagneTower(int poured, int query_row, int query_glass) {
        initial = poured;

        for (int i = 0; i <= query_row; i++) {
            Arrays.fill(memo[i], -1.0);
        }

        double ans = solve(query_row, query_glass);
        return Math.min(1.0, ans);
    }

    public double solve(int i, int j) {
        if (i < 0 || j < 0 || j > i) {
            return 0.0;
        }

        if (i == 0 && j == 0) {
            return initial;
        }

        if (memo[i][j] != -1.0) {
            return memo[i][j];
        }

        double upLeft  = Math.max(0.0, (solve(i - 1, j - 1) - 1.0) / 2.0);
        double upRight = Math.max(0.0, (solve(i - 1, j)     - 1.0) / 2.0);

        memo[i][j] = upLeft + upRight;
        return memo[i][j];
    }

    // -----------------------------
    // Approach 2: Tabulation
    // -----------------------------

    public double champagneTowerTabulation(int poured, int query_row, int query_glass) {

        double[][] dp = new double[101][101];
        dp[0][0] = poured;

        for (int i = 0; i <= query_row; i++) {
            for (int j = 0; j <= i; j++) {

                if (dp[i][j] > 1.0) {
                    double overflow = (dp[i][j] - 1.0) / 2.0;
                    dp[i + 1][j]     += overflow;
                    dp[i + 1][j + 1] += overflow;
                }
            }
        }

        return Math.min(1.0, dp[query_row][query_glass]);
    }
}
