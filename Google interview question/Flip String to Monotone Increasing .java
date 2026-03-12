import java.util.*;

/*
Problem: Flip String to Monotone Increasing (LeetCode 926)

--------------------------------------------------
Problem Summary
--------------------------------------------------
Given a binary string s, return the minimum number of flips
required to make the string monotone increasing.

A string is monotone increasing if it consists of:
000...111

You may flip any character:
0 -> 1
1 -> 0

--------------------------------------------------
Intuition
--------------------------------------------------
At each position we decide:

1) Flip the current character
2) Keep the current character

But we must maintain monotonic order.

So we track:
prevVal = the last chosen value (0 or 1)

If prevVal = 1
→ all future values must be 1.

State:
dp[index][prevVal]

--------------------------------------------------
State Meaning
--------------------------------------------------
index  = current position
prevVal = value chosen at previous position

Return:
minimum flips needed from index onward.

--------------------------------------------------
Time Complexity
--------------------------------------------------
O(n)

--------------------------------------------------
Space Complexity
--------------------------------------------------
O(n)

--------------------------------------------------
Example
--------------------------------------------------
Input:
s = "00110"

Output:
1

Explanation:
Flip the last '1' to '0'
or flip middle '0' to '1'
--------------------------------------------------
*/

class Solution {

    /*
    --------------------------------------------------
    1. Recursion (Brute Force)
    --------------------------------------------------
    Time:  O(2^n)
    Space: O(n)
    */

    public int solveRec(int index, int prevVal, String s) {

        if(index == s.length()) {
            return 0;
        }

        int flip = Integer.MAX_VALUE;
        int noFlip = Integer.MAX_VALUE;

        char ch = s.charAt(index);

        if(ch == '0') {

            if(prevVal == 1) {
                flip = 1 + solveRec(index + 1, 1, s);
            } else {
                flip = 1 + solveRec(index + 1, 1, s);
                noFlip = solveRec(index + 1, 0, s);
            }

        } else {

            if(prevVal == 1) {
                noFlip = solveRec(index + 1, 1, s);
            } else {
                flip = 1 + solveRec(index + 1, 0, s);
                noFlip = solveRec(index + 1, 1, s);
            }
        }

        return Math.min(flip, noFlip);
    }


    /*
    --------------------------------------------------
    2. Memoization (Top Down DP)
    --------------------------------------------------
    Time:  O(n)
    Space: O(n)
    */

    public int solveMemo(int index, int prevVal, String s, int[][] dp) {

        if(index == s.length()) {
            return 0;
        }

        if(dp[index][prevVal] != -1) {
            return dp[index][prevVal];
        }

        int flip = Integer.MAX_VALUE;
        int noFlip = Integer.MAX_VALUE;

        char ch = s.charAt(index);

        if(ch == '0') {

            if(prevVal == 1) {
                flip = 1 + solveMemo(index + 1, 1, s, dp);
            } else {
                flip = 1 + solveMemo(index + 1, 1, s, dp);
                noFlip = solveMemo(index + 1, 0, s, dp);
            }

        } else {

            if(prevVal == 1) {
                noFlip = solveMemo(index + 1, 1, s, dp);
            } else {
                flip = 1 + solveMemo(index + 1, 0, s, dp);
                noFlip = solveMemo(index + 1, 1, s, dp);
            }
        }

        return dp[index][prevVal] = Math.min(flip, noFlip);
    }


    /*
    --------------------------------------------------
    3. Tabulation (Bottom Up DP)
    --------------------------------------------------
    Time:  O(n)
    Space: O(n)
    */

    public int minFlipsMonoIncr(String s) {

        int n = s.length();

        int[][] dp = new int[n + 1][2];

        for(int index = n - 1; index >= 0; index--) {

            for(int prevVal = 0; prevVal <= 1; prevVal++) {

                int flip = Integer.MAX_VALUE;
                int noFlip = Integer.MAX_VALUE;

                if(s.charAt(index) == '0') {

                    if(prevVal == 1) {
                        flip = 1 + dp[index + 1][1];
                    } 
                    else {
                        flip = 1 + dp[index + 1][1];
                        noFlip = dp[index + 1][0];
                    }

                } 
                else {

                    if(prevVal == 1) {
                        noFlip = dp[index + 1][1];
                    } 
                    else {
                        flip = 1 + dp[index + 1][0];
                        noFlip = dp[index + 1][1];
                    }
                }

                dp[index][prevVal] = Math.min(flip, noFlip);
            }
        }

        return dp[0][0];
    }
}