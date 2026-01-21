/*
====================================================
📌 Problem: Ninja's Training
(DP – Medium)
====================================================

A ninja plans a training schedule for `n` days.
Each day, he must perform exactly ONE of the following activities:
- Running (0)
- Stealth Training (1)
- Fighting Practice (2)

Rules:
- The same activity CANNOT be done on two consecutive days.
- Each activity gives different merit points on different days.

You are given a `n x 3` matrix `points` where:
points[i][0] → running points on day i
points[i][1] → stealth points on day i
points[i][2] → fighting points on day i

Return the **maximum total merit points**.

====================================================
🧠 Core DP Idea
====================================================

On each day, choose an activity that is:
- Different from the activity chosen on the previous day
- Gives maximum total points till that day

This is a **DP on days + last activity** problem.

====================================================
🧠 Approaches Covered
====================================================

1️⃣ Recursion (Brute Force) ❌  
2️⃣ Memoization (Top-Down DP)  
3️⃣ Tabulation (Bottom-Up DP)  
4️⃣ Space Optimized DP ✅ (BEST)

====================================================
*/

import java.util.*;

class Solution {

    /*
    ====================================================
    1️⃣ RECURSION (Brute Force) ❌
    ====================================================
    f(day, lastTask)
    lastTask = activity done on previous day
    ====================================================
    Time Complexity: O(3^n)
    Space Complexity: O(n) (recursion stack)
    ====================================================
    */
    private int solveRec(int day, int last, int[][] points) {

        if (day == 0) {
            int max = 0;
            for (int task = 0; task < 3; task++) {
                if (task != last) {
                    max = Math.max(max, points[0][task]);
                }
            }
            return max;
        }

        int max = 0;
        for (int task = 0; task < 3; task++) {
            if (task != last) {
                int val = points[day][task]
                        + solveRec(day - 1, task, points);
                max = Math.max(max, val);
            }
        }
        return max;
    }

    /*
    ====================================================
    2️⃣ MEMOIZATION (Top-Down DP)
    ====================================================
    dp[day][last]
    last ∈ {0,1,2,3} (3 means no previous task)
    ====================================================
    Time Complexity: O(n * 4 * 3)
    Space Complexity: O(n * 4) + recursion stack
    ====================================================
    */
    private int solveMemo(int day, int last,
                          int[][] points, int[][] dp) {

        if (dp[day][last] != -1) {
            return dp[day][last];
        }

        if (day == 0) {
            int max = 0;
            for (int task = 0; task < 3; task++) {
                if (task != last) {
                    max = Math.max(max, points[0][task]);
                }
            }
            return dp[day][last] = max;
        }

        int max = 0;
        for (int task = 0; task < 3; task++) {
            if (task != last) {
                int val = points[day][task]
                        + solveMemo(day - 1, task, points, dp);
                max = Math.max(max, val);
            }
        }

        return dp[day][last] = max;
    }

    /*
    ====================================================
    3️⃣ TABULATION (Bottom-Up DP)
    ====================================================
    dp[day][last]
    ====================================================
    Time Complexity: O(n * 4 * 3)
    Space Complexity: O(n * 4)
    ====================================================
    */
    private int solveTab(int[][] points, int n) {

        int[][] dp = new int[n][4];

        // Base case (day = 0)
        dp[0][0] = Math.max(points[0][1], points[0][2]);
        dp[0][1] = Math.max(points[0][0], points[0][2]);
        dp[0][2] = Math.max(points[0][0], points[0][1]);
        dp[0][3] = Math.max(
                        points[0][0],
                        Math.max(points[0][1], points[0][2])
                   );

        for (int day = 1; day < n; day++) {
            for (int last = 0; last < 4; last++) {
                dp[day][last] = 0;

                for (int task = 0; task < 3; task++) {
                    if (task != last) {
                        int val = points[day][task]
                                + dp[day - 1][task];
                        dp[day][last] = Math.max(dp[day][last], val);
                    }
                }
            }
        }

        return dp[n - 1][3];
    }

    /*
    ====================================================
    4️⃣ SPACE OPTIMIZED DP ✅ (BEST)
    ====================================================
    ====================================================
    Time Complexity: O(n * 4 * 3)
    Space Complexity: O(4)
    ====================================================
    */
    private int solveSpaceOptimized(int[][] points, int n) {

        int[] prev = new int[4];

        // Base case
        prev[0] = Math.max(points[0][1], points[0][2]);
        prev[1] = Math.max(points[0][0], points[0][2]);
        prev[2] = Math.max(points[0][0], points[0][1]);
        prev[3] = Math.max(
                    points[0][0],
                    Math.max(points[0][1], points[0][2])
                 );

        for (int day = 1; day < n; day++) {

            int[] curr = new int[4];

            for (int last = 0; last < 4; last++) {
                curr[last] = 0;

                for (int task = 0; task < 3; task++) {
                    if (task != last) {
                        int val = points[day][task] + prev[task];
                        curr[last] = Math.max(curr[last], val);
                    }
                }
            }

            prev = curr;
        }

        return prev[3];
    }

    /*
    ====================================================
    🚀 MAIN FUNCTION (Expected by Platform)
    ====================================================
    */
    public int ninjaTraining(int n, int[][] points) {

        // ✅ Best solution
        return solveSpaceOptimized(points, n);

        // For learning/debugging, you can try:
        // return solveRec(n - 1, 3, points);

        // int[][] dp = new int[n][4];
        // for (int[] row : dp) Arrays.fill(row, -1);
        // return solveMemo(n - 1, 3, points, dp);

        // return solveTab(points, n);
    }
}
