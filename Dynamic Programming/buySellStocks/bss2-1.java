import java.util.*;

/*
Problem: Best Time to Buy and Sell Stock II (LeetCode 122)

--------------------------------------------------
Problem Description
--------------------------------------------------
You are given an array prices where prices[i] is the
price of a given stock on the i-th day.

You can buy and/or sell the stock multiple times.

However:
- You can only hold one stock at a time.
- You must sell before you buy again.

Return the maximum profit you can achieve.

--------------------------------------------------
Key Observations
--------------------------------------------------
1. You can perform multiple transactions.
2. Buy low → sell high whenever possible.
3. If price[i] < price[i+1], profit can be made.
4. This is a DP problem with 2 states:
   - Buy state
   - Sell state

--------------------------------------------------
Intuition
--------------------------------------------------
At every index, we have 2 choices:
- Buy (if allowed)
- Sell (if holding stock)

We track whether we are allowed to buy or not.

This creates a state:
(index, canBuy)

--------------------------------------------------
Approach
--------------------------------------------------
1. If canBuy:
   - Buy → -price[i] + next state (can't buy)
   - Skip → move ahead

2. If can't buy:
   - Sell → +price[i] + next state (can buy)
   - Skip → move ahead

3. Take maximum of choices.

--------------------------------------------------
State Definition
--------------------------------------------------
dp[i][buy] =
maximum profit from index i
buy = 1 → can buy
buy = 0 → cannot buy (must sell)

--------------------------------------------------
Transition
--------------------------------------------------
if(buy == 1):
    dp[i][1] = max(
        -prices[i] + dp[i+1][0],
        dp[i+1][1]
    )
else:
    dp[i][0] = max(
        prices[i] + dp[i+1][1],
        dp[i+1][0]
    )

--------------------------------------------------
Time Complexity
--------------------------------------------------
Recursion      : O(2^n)
Memoization    : O(n * 2)
Tabulation     : O(n * 2)
Space Optimized: O(1)

--------------------------------------------------
Space Complexity
--------------------------------------------------
Recursion      : O(n)
Memoization    : O(n * 2)
Tabulation     : O(n * 2)
Space Optimized: O(1)

--------------------------------------------------
Example
--------------------------------------------------
Input:
prices = [7,1,5,3,6,4]

Output:
7

Explanation:
Buy at 1, sell at 5 → profit = 4  
Buy at 3, sell at 6 → profit = 3  
Total = 7
*/

class Solution {

    /*
    --------------------------------------------------
    1. Recursion (Brute Force)
    --------------------------------------------------
    */

    public int solveRec(int i, int buy, int[] prices) {

        if(i == prices.length) return 0;

        if(buy == 1) {

            int take = -prices[i] + solveRec(i + 1, 0, prices);
            int skip = solveRec(i + 1, 1, prices);

            return Math.max(take, skip);
        }
        else {

            int sell = prices[i] + solveRec(i + 1, 1, prices);
            int skip = solveRec(i + 1, 0, prices);

            return Math.max(sell, skip);
        }
    }

    /*
    --------------------------------------------------
    2. Memoization (Top Down DP)
    --------------------------------------------------
    */

    public int solveMemo(int i, int buy, int[] prices, int[][] dp) {

        if(i == prices.length) return 0;

        if(dp[i][buy] != -1) return dp[i][buy];

        if(buy == 1) {

            int take = -prices[i] + solveMemo(i + 1, 0, prices, dp);
            int skip = solveMemo(i + 1, 1, prices, dp);

            return dp[i][buy] = Math.max(take, skip);
        }
        else {

            int sell = prices[i] + solveMemo(i + 1, 1, prices, dp);
            int skip = solveMemo(i + 1, 0, prices, dp);

            return dp[i][buy] = Math.max(sell, skip);
        }
    }

    /*
    --------------------------------------------------
    3. Tabulation (Bottom Up DP)
    --------------------------------------------------
    */

    public int maxProfitTab(int[] prices) {

        int n = prices.length;
        int[][] dp = new int[n + 1][2];

        for(int i = n - 1; i >= 0; i--) {

            for(int buy = 0; buy <= 1; buy++) {

                if(buy == 1) {

                    int take = -prices[i] + dp[i + 1][0];
                    int skip = dp[i + 1][1];

                    dp[i][buy] = Math.max(take, skip);
                }
                else {

                    int sell = prices[i] + dp[i + 1][1];
                    int skip = dp[i + 1][0];

                    dp[i][buy] = Math.max(sell, skip);
                }
            }
        }

        return dp[0][1];
    }

    /*
    --------------------------------------------------
    4. Space Optimized DP
    --------------------------------------------------
    */

    public int maxProfit(int[] prices) {

        int n = prices.length;

        int[] ahead = new int[2];
        int[] curr = new int[2];

        for(int i = n - 1; i >= 0; i--) {

            for(int buy = 0; buy <= 1; buy++) {

                if(buy == 1) {

                    int take = -prices[i] + ahead[0];
                    int skip = ahead[1];

                    curr[buy] = Math.max(take, skip);
                }
                else {

                    int sell = prices[i] + ahead[1];
                    int skip = ahead[0];

                    curr[buy] = Math.max(sell, skip);
                }
            }

            ahead = curr.clone();
        }

        return ahead[1];
    }
}