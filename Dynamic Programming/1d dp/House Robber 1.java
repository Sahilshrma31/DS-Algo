import java.util.*;

/*
Problem: House Robber
(LeetCode 198)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given an integer array nums where nums[i] represents the amount of money
in the i-th house. You cannot rob two adjacent houses.

Return the maximum amount of money you can rob.

--------------------------------------------------
Intuition:
--------------------------------------------------
At every house, you have two choices:
- Skip the current house → take best till previous house
- Rob the current house → add current value to best till i-2

So the decision becomes:
dp[i] = max(dp[i-1], nums[i] + dp[i-2])

--------------------------------------------------
Approach 1: Recursion (Brute Force)
--------------------------------------------------
At each index, choose:
- rob current house
- skip current house

Time Complexity:
O(2^n)

Space Complexity:
O(n) recursion stack

--------------------------------------------------
Approach 2: Recursion + Memoization
--------------------------------------------------
Store results of subproblems to avoid recomputation.

Time Complexity:
O(n)

Space Complexity:
O(n) memo + recursion stack

--------------------------------------------------
Approach 3: Tabulation (Bottom-Up DP)
--------------------------------------------------
Build dp array from left to right.

Time Complexity:
O(n)

Space Complexity:
O(n)

--------------------------------------------------
Approach 4: Space Optimized DP
--------------------------------------------------
Only last two states are required.

Time Complexity:
O(n)

Space Complexity:
O(1)

--------------------------------------------------
Example:
--------------------------------------------------
nums = [2, 7, 9, 3, 1]
Output = 12

Explanation:
Rob houses 2 + 9 + 1 = 12

--------------------------------------------------
Test Cases:
--------------------------------------------------
1) nums = [1,2,3,1]     -> 4
2) nums = [2,7,9,3,1]   -> 12
3) nums = [2]           -> 2

--------------------------------------------------
*/

class Solution {

    // ----------------- Recursion (Brute Force) -----------------
    public int robRecursion(int[] nums) {
        return solveRec(nums, nums.length - 1);
    }

    private int solveRec(int[] nums, int idx) {
        if (idx < 0) return 0;
        if (idx == 0) return nums[0];

        int pick = nums[idx] + solveRec(nums, idx - 2);
        int notPick = solveRec(nums, idx - 1);

        return Math.max(pick, notPick);
    }

    // ----------------- Memoization -----------------
    public int robMemo(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return solveMemo(nums, n - 1, dp);
    }

    private int solveMemo(int[] nums, int idx, int[] dp) {
        if (idx < 0) return 0;
        if (idx == 0) return nums[0];

        if (dp[idx] != -1) return dp[idx];

        int pick = nums[idx] + solveMemo(nums, idx - 2, dp);
        int notPick = solveMemo(nums, idx - 1, dp);

        return dp[idx] = Math.max(pick, notPick);
    }

    // ----------------- Tabulation (Your Logic - Unchanged) -----------------
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }

        int dp[] = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], nums[i] + dp[i - 2]);
        }
        return dp[n - 1];
    }

    // ----------------- Space Optimized -----------------
    public int robSpaceOptimized(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];

        int prev2 = nums[0];
        int prev1 = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            int curr = Math.max(prev1, nums[i] + prev2);
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }
}
