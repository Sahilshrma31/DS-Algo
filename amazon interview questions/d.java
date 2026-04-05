import java.util.*;

/*
Problem: Knight Probability in Chessboard (LeetCode 688)

--------------------------------------------------
Problem Description
--------------------------------------------------
On an n x n chessboard, a knight starts at position
(row, column).

The knight makes exactly k moves. On each move, it
chooses one of 8 possible directions uniformly.

Return the probability that the knight remains on
the board after k moves.

--------------------------------------------------
Key Observations
--------------------------------------------------
1. Knight has 8 possible moves.
2. If it goes out of bounds → probability = 0.
3. If no moves left (k == 0) → valid position → return 1.
4. Each move contributes equally → divide by 8.

--------------------------------------------------
Intuition
--------------------------------------------------
At each step:
- Try all 8 moves
- Recursively compute probability
- Average all possibilities

Overlapping subproblems → use DP.

--------------------------------------------------
Approach
--------------------------------------------------
1. Recursively try all 8 directions.
2. If out of bounds → return 0.
3. If k == 0 → return 1.
4. Memoize states (k, row, col).
5. Convert to tabulation.

--------------------------------------------------
State Definition
--------------------------------------------------
dp[k][row][col] =
probability of staying on board starting from
(row, col) with k moves remaining

--------------------------------------------------
Transition
--------------------------------------------------
dp[k][r][c] =
sum of dp[k-1][new_r][new_c] / 8

--------------------------------------------------
Time Complexity
--------------------------------------------------
Recursion      : O(8^k)
Memoization    : O(k * n * n)
Tabulation     : O(k * n * n)

--------------------------------------------------
Space Complexity
--------------------------------------------------
Memoization    : O(k * n * n)
Tabulation     : O(k * n * n)

--------------------------------------------------
Example
--------------------------------------------------
Input:
n = 3, k = 2, row = 0, column = 0

Output:
0.0625
*/

class Solution {

    int[][] directions = {
        {1, 2}, {1, -2}, {-1, 2}, {-1, -2},
        {2, 1}, {2, -1}, {-2, 1}, {-2, -1}
    };

    /*
    --------------------------------------------------
    1. Recursion (Brute Force)
    --------------------------------------------------
    */

    public double solveRec(int n, int k, int row, int col) {

        if (row < 0 || row >= n || col < 0 || col >= n) {
            return 0;
        }

        if (k == 0) {
            return 1;
        }

        double ans = 0;

        for (int[] dir : directions) {
            ans += solveRec(n, k - 1, row + dir[0], col + dir[1]);
        }

        return ans / 8.0;
    }

    /*
    --------------------------------------------------
    2. Memoization (Top Down DP)
    --------------------------------------------------
    */

    Map<String, Double> dp = new HashMap<>();

    public double solveMemo(int n, int k, int row, int col) {

        if (row < 0 || row >= n || col < 0 || col >= n) {
            return 0;
        }

        if (k == 0) {
            return 1;
        }

        String key = k + "_" + row + "_" + col;

        if (dp.containsKey(key)) {
            return dp.get(key);
        }

        double ans = 0;

        for (int[] dir : directions) {
            ans += solveMemo(n, k - 1, row + dir[0], col + dir[1]);
        }

        ans /= 8.0;

        dp.put(key, ans);
        return ans;
    }

    /*
    --------------------------------------------------
    3. Tabulation (Bottom Up DP)
    --------------------------------------------------
    */

    public double knightProbability(int n, int k, int row, int col) {

        double[][][] dp = new double[k + 1][n][n];

        // Base case
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                dp[0][r][c] = 1;
            }
        }

        for (int move = 1; move <= k; move++) {

            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {

                    double prob = 0;

                    for (int[] dir : directions) {
                        int prevR = r + dir[0];
                        int prevC = c + dir[1];

                        if (prevR >= 0 && prevR < n && prevC >= 0 && prevC < n) {
                            prob += dp[move - 1][prevR][prevC];
                        }
                    }

                    dp[move][r][c] = prob / 8.0;
                }
            }
        }

        return dp[k][row][col];
    }
}