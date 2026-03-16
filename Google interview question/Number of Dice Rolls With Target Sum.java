import java.util.*;

/*
Problem: Number of Dice Rolls With Target Sum (LeetCode 1155)

--------------------------------------------------
Problem Summary
--------------------------------------------------
You have n dice and each die has k faces numbered 1..k.

Return the number of ways to roll the dice so that
the total sum equals target.

Since the answer may be large, return it modulo 1e9+7.

--------------------------------------------------
Intuition
--------------------------------------------------
For every dice we have k choices.

If we roll face f, then the remaining sum becomes:
target - f

So the problem becomes:
number of ways to form (target - f) using (n - 1) dice.

This forms a classic DP recurrence.

--------------------------------------------------
State Definition
--------------------------------------------------
dp[d][s] = number of ways to make sum s using d dice

--------------------------------------------------
Transition
--------------------------------------------------
dp[d][s] = sum of dp[d-1][s-face] for all faces

--------------------------------------------------
Time Complexity
--------------------------------------------------
Recursion:      O(k^n)
Memoization:    O(n * target * k)
Tabulation:     O(n * target * k)
Space optimized O(target * k)

--------------------------------------------------
Space Complexity
--------------------------------------------------
Recursion:      O(n)
Memoization:    O(n * target)
Tabulation:     O(n * target)
Space optimized O(target)

--------------------------------------------------
Example
--------------------------------------------------
Input:
n = 2
k = 6
target = 7

Output:
6

Explanation:
Possible rolls:
1+6
2+5
3+4
4+3
5+2
6+1

--------------------------------------------------
*/

class Solution {

    int MOD = (int)1e9 + 7;

    /*
    --------------------------------------------------
    1. Recursion (Brute Force)
    --------------------------------------------------
    */

    public int solveRec(int dice, int target, int k) {

        if(dice == 0) {
            return target == 0 ? 1 : 0;
        }

        if(target < 0) {
            return 0;
        }

        int ways = 0;

        for(int face = 1; face <= k; face++) {
            ways = (ways + solveRec(dice - 1, target - face, k)) % MOD;
        }

        return ways;
    }


    /*
    --------------------------------------------------
    2. Memoization (Top Down DP)
    --------------------------------------------------
    */

    public int solveMemo(int dice, int target, int k, int[][] dp) {

        if(dice == 0) {
            return target == 0 ? 1 : 0;
        }

        if(target < 0) {
            return 0;
        }

        if(dp[dice][target] != -1) {
            return dp[dice][target];
        }

        int ways = 0;

        for(int face = 1; face <= k; face++) {

            if(target - face >= 0) {
                ways = (ways + solveMemo(dice - 1, target - face, k, dp)) % MOD;
            }
        }

        return dp[dice][target] = ways;
    }


    /*
    --------------------------------------------------
    3. Tabulation (Bottom Up DP)
    --------------------------------------------------
    */

    public int numRollsToTarget(int n, int k, int target) {

        int[][] dp = new int[n + 1][target + 1];

        dp[0][0] = 1;

        for(int dice = 1; dice <= n; dice++){

            for(int sum = 1; sum <= target; sum++){

                int ways = 0;

                for(int face = 1; face <= k; face++){

                    if(sum - face >= 0){
                        ways = (ways + dp[dice - 1][sum - face]) % MOD;
                    }
                }

                dp[dice][sum] = ways;
            }
        }

        return dp[n][target];
    }


    /*
    --------------------------------------------------
    4. Space Optimized DP
    --------------------------------------------------
    */

    public int numRollsToTargetSpaceOpt(int n, int k, int target) {

        int[] prev = new int[target + 1];
        prev[0] = 1;

        for(int dice = 1; dice <= n; dice++) {

            int[] curr = new int[target + 1];

            for(int sum = 1; sum <= target; sum++) {

                int ways = 0;

                for(int face = 1; face <= k; face++) {

                    if(sum - face >= 0) {
                        ways = (ways + prev[sum - face]) % MOD;
                    }
                }

                curr[sum] = ways;
            }

            prev = curr;
        }

        return prev[target];
    }
}