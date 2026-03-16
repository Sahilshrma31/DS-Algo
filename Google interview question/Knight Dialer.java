import java.util.*;

/*
Problem: Knight Dialer (LeetCode 935)

--------------------------------------------------
Problem Description
--------------------------------------------------
The digits 0–9 are arranged on a phone keypad:

    1   2   3
    4   5   6
    7   8   9
        0

A chess knight is placed on any digit of the keypad.

The knight moves using the same rules as in chess:
- 2 steps in one direction
- 1 step perpendicular

From its current digit, the knight can jump to certain
other digits.

Given an integer n, return the total number of distinct
phone numbers of length n that can be formed.

The knight can start from any digit.

Return the answer modulo 1e9 + 7.

--------------------------------------------------
Key Observations
--------------------------------------------------
1. Each digit can move only to certain digits.
2. Digit 5 has no valid moves.
3. We can represent possible knight moves using an
   adjacency list.
4. At every step we move from current digit to the
   next valid digit.

--------------------------------------------------
Knight Moves Mapping
--------------------------------------------------

0 -> 4,6
1 -> 6,8
2 -> 7,9
3 -> 4,8
4 -> 3,9,0
5 -> (no moves)
6 -> 1,7,0
7 -> 2,6
8 -> 1,3
9 -> 2,4

--------------------------------------------------
Intuition
--------------------------------------------------
If the knight is currently on a digit and we want
to build a number of length n, we recursively move
to all digits reachable via a knight move.

At every step we decrease the remaining length.

This leads to a recursive tree where each node
branches to valid knight moves.

Since many states repeat (same digit, same length),
we optimize using Dynamic Programming.

--------------------------------------------------
Approach
--------------------------------------------------
1. Start the knight from every digit (0-9).
2. Recursively explore all valid moves.
3. Reduce remaining length at every step.
4. Sum all valid sequences.
5. Use memoization to avoid recomputation.
6. Convert the solution to tabulation for a
   bottom-up approach.

--------------------------------------------------
State Definition
--------------------------------------------------
dp[len][digit] =
number of sequences of length 'len'
starting from digit.

--------------------------------------------------
Transition
--------------------------------------------------

dp[len][digit] =
sum(dp[len-1][nextDigit])

for all nextDigit reachable from digit.

--------------------------------------------------
Time Complexity
--------------------------------------------------
Recursion      : O(3^n)
Memoization    : O(n * 10)
Tabulation     : O(n * 10)
Space Optimized: O(10)

--------------------------------------------------
Space Complexity
--------------------------------------------------
Recursion      : O(n)
Memoization    : O(n * 10)
Tabulation     : O(n * 10)
Space Optimized: O(10)

--------------------------------------------------
Example
--------------------------------------------------
Input:
n = 2

Output:
20

Explanation:
Each digit moves to its valid knight moves,
forming 20 distinct numbers of length 2.
*/

class Solution {

    int MOD = (int)1e9 + 7;

    int[][] adj = {
        {4,6},
        {6,8},
        {7,9},
        {4,8},
        {3,9,0},
        {},
        {1,7,0},
        {2,6},
        {1,3},
        {2,4}
    };

    /*
    --------------------------------------------------
    1. Recursion (Brute Force)
    --------------------------------------------------
    */

    public int solveRec(int digit, int remaining) {

        if(remaining == 1) {
            return 1;
        }

        int ways = 0;

        for(int next : adj[digit]) {
            ways = (ways + solveRec(next, remaining - 1)) % MOD;
        }

        return ways;
    }

    /*
    --------------------------------------------------
    2. Memoization (Top Down DP)
    --------------------------------------------------
    */

    public int solveMemo(int digit, int remaining, int[][] dp) {

        if(remaining == 1) {
            return 1;
        }

        if(dp[digit][remaining] != -1) {
            return dp[digit][remaining];
        }

        int ways = 0;

        for(int next : adj[digit]) {
            ways = (ways + solveMemo(next, remaining - 1, dp)) % MOD;
        }

        return dp[digit][remaining] = ways;
    }

    /*
    --------------------------------------------------
    3. Tabulation (Bottom Up DP)
    --------------------------------------------------
    */

    public int knightDialerTab(int n) {

        long[][] dp = new long[n + 1][10];

        for(int digit = 0; digit <= 9; digit++) {
            dp[1][digit] = 1;
        }

        for(int len = 2; len <= n; len++) {

            for(int digit = 0; digit <= 9; digit++) {

                for(int next : adj[digit]) {

                    dp[len][digit] =
                        (dp[len][digit] + dp[len - 1][next]) % MOD;
                }
            }
        }

        long result = 0;

        for(int digit = 0; digit <= 9; digit++) {
            result = (result + dp[n][digit]) % MOD;
        }

        return (int)result;
    }

    /*
    --------------------------------------------------
    4. Space Optimized DP
    --------------------------------------------------
    */

    public int knightDialer(int n) {

        long[] prev = new long[10];
        Arrays.fill(prev, 1);

        for(int len = 2; len <= n; len++) {

            long[] curr = new long[10];

            for(int digit = 0; digit <= 9; digit++) {

                for(int next : adj[digit]) {

                    curr[digit] =
                        (curr[digit] + prev[next]) % MOD;
                }
            }

            prev = curr;
        }

        long result = 0;

        for(long val : prev) {
            result = (result + val) % MOD;
        }

        return (int)result;
    }
}