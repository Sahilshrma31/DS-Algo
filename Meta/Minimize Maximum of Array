/*
Problem: Minimize Maximum of Array
(LeetCode 2439)

Problem Explanation:
You are given an integer array `nums`.
You are allowed to perform the following operation any number of times:

- Choose an index i (1 ≤ i < n)
- Decrease nums[i] by 1
- Increase nums[i-1] by 1

The goal is to minimize the maximum value in the array after any number
of operations.

Return the minimum possible maximum value.

Key Observations:
- You can only move value from right to left
- You cannot move value from left to right
- This creates a prefix-based constraint
- The answer is monotonic: if a maximum value X is achievable,
  then any value ≥ X is also achievable

This monotonicity allows Binary Search on the answer.

Approach:
1. Binary search on the possible maximum value
2. For a candidate maximum `mid`, check if we can make all elements
   ≤ mid by pushing extra values to the right
3. Traverse from left to right and maintain balance

Validity Check Logic:
- If nums[i] > mid → impossible
- Otherwise, extra capacity (mid - nums[i]) can be used to absorb
  value from nums[i+1]

Time Complexity:
- Binary Search Range: O(log max(nums))
- Validation per step: O(n)
Overall: O(n log max(nums))

Space Complexity:
O(n) due to temporary array (can be optimized to O(1))

--------------------------------------------------
Example Test Cases
--------------------------------------------------

Example 1:
Input:
nums = [3, 7, 1, 6]

Process:
Optimal final array = [5, 5, 5, 2]

Output:
5

--------------------------------------------------

Example 2:
Input:
nums = [10, 1]

Process:
Move 4 from index 0 to index 1
Final array = [6, 5]

Output:
6

--------------------------------------------------

Example 3:
Input:
nums = [13, 13, 20, 0, 8, 9]

Output:
16

--------------------------------------------------
*/

import java.util.*;

class Solution {

    int n;

    public int minimizeArrayValue(int[] nums) {

        n = nums.length;

        int left = 0;
        int right = Integer.MIN_VALUE;

        for (int x : nums) {
            right = Math.max(right, x);
        }

        int answer = right;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (isValid(nums, mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }

    private boolean isValid(int[] arr, int maxVal) {

        long[] nums = new long[n];
        for (int i = 0; i < n; i++) {
            nums[i] = arr[i];
        }

        for (int i = 0; i < n - 1; i++) {

            if (nums[i] > maxVal) {
                return false;
            }

            long buffer = maxVal - nums[i];
            nums[i + 1] -= buffer;
        }

        return nums[n - 1] <= maxVal;
    }
}
