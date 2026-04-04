import java.util.*;

/*
Problem: Best Time to Buy and Sell Stock IV (LeetCode 188)

--------------------------------------------------
Problem Description
--------------------------------------------------
You are given an array prices where prices[i] is the
price of a stock on day i.

You are also given an integer k.

You can perform at most k transactions.

Each transaction consists of:
- Buying a stock
- Selling that stock

You must sell before buying again.

Return the maximum profit.

--------------------------------------------------
Key Observations
--------------------------------------------------
1. We track number of transactions left (cap).
2. A transaction is completed ONLY when we SELL.
3. So we decrease cap when selling, not buying.
4. State depends on:
   - index
   - buy (can buy or not)
   - cap (remaining transactions)

--------------------------------------------------
Intuition
--------------------------------------------------
At every day:
- If we can buy:
    → either buy OR skip
- If we cannot buy:
    → either sell OR skip

Important:
- Buying does NOT reduce transactions
- Selling reduces transactions (cap - 1)

--------------------------------------------------
Approach
--------------------------------------------------
1. Use DP with state (i, buy, cap)
2. If buy == 1:
      take = -price + next state (buy=0)
      skip = move ahead
3. If buy == 0:
      sell = +price + next state (buy=1, cap-1)
      skip = move ahead
4. Return max profit

--------------------------------------------------
State Definition
--------------------------------------------------
dp[i][buy][cap] =
maximum profit from day i
buy = 1 → can buy
buy = 0 → can sell
cap = transactions remaining

--------------------------------------------------
Transition
--------------------------------------------------
if(buy == 1):
    dp[i][1][cap] = max(
        -prices[i] + dp[i+1][0][cap],
        dp[i+1][1][cap]
    )
else:
    dp[i][0][cap] = max(
        prices[i] + dp[i+1][1][cap-1],
        dp[i+1][0][cap]
    )

--------------------------------------------------
Time Complexity
--------------------------------------------------
Recursion      : Exponential
Memoization    : O(n * 2 * k)
Tabulation     : O(n * 2 * k)
Space Optimized: O(2 * k)

--------------------------------------------------
Space Complexity
--------------------------------------------------
Recursion      : O(n)
Memoization    : O(n * 2 * k)
Tabulation     : O(n * 2 * k)
Space Optimized: O(2 * k)

--------------------------------------------------
Example
--------------------------------------------------
Input:
prices = [3,2,6,5,0,3], k = 2

Output:
7

Explanation:
Buy at 2 → sell at 6 → profit = 4  
Buy at 0 → sell at 3 → profit = 3  
Total = 7
*/

class Solution {

    /*
    --------------------------------------------------
    1. Recursion (Brute Force)
    --------------------------------------------------
    */

    public int solveRec(int i, int buy, int cap, int[] prices) {

        if(i == prices.length || cap == 0) return 0;

        if(buy == 1) {

            int take = -prices[i] + solveRec(i + 1, 0, cap, prices);
            int skip = solveRec(i + 1, 1, cap, prices);

            return Math.max(take, skip);
        }
        else {

            int sell = prices[i] + solveRec(i + 1, 1, cap - 1, prices);
            int skip = solveRec(i + 1, 0, cap, prices);

            return Math.max(sell, skip);
        }
    }

    /*
    --------------------------------------------------
    2. Memoization (Top Down DP)
    --------------------------------------------------
    */

    public int solveMemo(int i, int buy, int cap, int[] prices, int[][][] dp) {

        if(i == prices.length || cap == 0) return 0;

        if(dp[i][buy][cap] != -1) return dp[i][buy][cap];

        if(buy == 1) {

            int take = -prices[i] + solveMemo(i + 1, 0, cap, prices, dp);
            int skip = solveMemo(i + 1, 1, cap, prices, dp);

            return dp[i][buy][cap] = Math.max(take, skip);
        }
        else {

            int sell = prices[i] + solveMemo(i + 1, 1, cap - 1, prices, dp);
            int skip = solveMemo(i + 1, 0, cap, prices, dp);

            return dp[i][buy][cap] = Math.max(sell, skip);
        }
    }

    /*
    --------------------------------------------------
    3. Tabulation (Bottom Up DP)
    --------------------------------------------------
    */

    public int maxProfitTab(int k, int[] prices) {

        int n = prices.length;

        int[][][] dp = new int[n + 1][2][k + 1];

        for(int i = n - 1; i >= 0; i--) {

            for(int buy = 0; buy <= 1; buy++) {

                for(int cap = 1; cap <= k; cap++) {

                    if(buy == 1) {

                        int take = -prices[i] + dp[i + 1][0][cap];
                        int skip = dp[i + 1][1][cap];

                        dp[i][buy][cap] = Math.max(take, skip);
                    }
                    else {

                        int sell = prices[i] + dp[i + 1][1][cap - 1];
                        int skip = dp[i + 1][0][cap];

                        dp[i][buy][cap] = Math.max(sell, skip);
                    }
                }
            }
        }

        return dp[0][1][k];
    }

    /*
    --------------------------------------------------
    4. Space Optimized DP
    --------------------------------------------------
    */

    public int maxProfit(int k, int[] prices) {

        int n = prices.length;

        int[][] ahead = new int[2][k + 1];
        int[][] curr = new int[2][k + 1];

        for(int i = n - 1; i >= 0; i--) {

            for(int buy = 0; buy <= 1; buy++) {

                for(int cap = 1; cap <= k; cap++) {

                    if(buy == 1) {

                        int take = -prices[i] + ahead[0][cap];
                        int skip = ahead[1][cap];

                        curr[buy][cap] = Math.max(take, skip);
                    }
                    else {

                        int sell = prices[i] + ahead[1][cap - 1];
                        int skip = ahead[0][cap];

                        curr[buy][cap] = Math.max(sell, skip);
                    }
                }
            }

            ahead = new int[2][k + 1];
            for(int b = 0; b < 2; b++) {
                for(int c = 0; c <= k; c++) {
                    ahead[b][c] = curr[b][c];
                }
            }
        }

        return ahead[1][k];
    }
}