import java.util.*;

/*
Problem: House Robber II
(LeetCode 213)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given an integer array nums representing the amount of money in each house.
All houses are arranged in a circle, meaning the first and last house are adjacent.

You cannot rob two adjacent houses.

Return the maximum amount of money you can rob.

--------------------------------------------------
Intuition:
--------------------------------------------------
Because houses are in a circle:
- You cannot take both first and last house together.

So the problem is reduced to two linear House Robber problems:
1) Rob houses from index 0 to n-2 (exclude last house)
2) Rob houses from index 1 to n-1 (exclude first house)

Take the maximum of both.

--------------------------------------------------
Approach:
--------------------------------------------------
1) If n == 1, return nums[0]
2) Create two arrays:
   - temp1: nums[0..n-2]
   - temp2: nums[1..n-1]
3) Apply House Robber I logic on both
4) Return the maximum of the two results

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)

--------------------------------------------------
Examples:
--------------------------------------------------
1)
nums = [2, 3, 2]
Output = 3

2)
nums = [1, 2, 3, 1]
Output = 4

--------------------------------------------------
*/

class Solution {

    public int max(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];

        int dp[] = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            int pick = nums[i] + dp[i - 2];
            int notpick = 0 + dp[i - 1];
            dp[i] = Math.max(pick, notpick);
        }
        return dp[n - 1];
    }

    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }

        int temp1[] = new int[n - 1];
        int temp2[] = new int[n - 1];

        for (int i = 0; i < n - 1; i++) {
            temp1[i] = nums[i];
        }
        for (int i = 0; i < n - 1; i++) {
            temp2[i] = nums[i + 1];
        }

        return Math.max(max(temp1), max(temp2));
    }
}
