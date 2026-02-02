import java.util.*;

/*
Problem: Maximum Product Subarray
(LeetCode 152)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given an integer array `nums`.
Find the contiguous subarray (containing at least one number)
which has the largest product, and return the product.

--------------------------------------------------
Detailed Intuition:
--------------------------------------------------
This problem looks similar to maximum subarray sum,
but product behaves very differently because of:
1. Negative numbers
2. Zeroes

Key observations:

1) A negative number can flip the sign of the product.
   - A very small (negative) product can suddenly become very large
     when multiplied by another negative number.

2) Zero breaks the subarray.
   - Any product involving zero becomes zero.
   - After zero, we must restart computation.

--------------------------------------------------
Why Prefix + Suffix Works:
--------------------------------------------------
Instead of tracking max and min products at each index,
we scan the array from BOTH directions:

- `pre`  → product from left to right
- `suff` → product from right to left

Why this is powerful:
- Any subarray maximum product must appear in either:
  - a prefix product
  - or a suffix product
- This handles negative numbers automatically
- Zero resets the product naturally

--------------------------------------------------
Algorithm Idea:
--------------------------------------------------
1. Initialize:
   - pre = 1
   - suff = 1
   - maxi = smallest possible integer

2. Traverse the array once:
   - Multiply `pre` by nums[i]
   - Multiply `suff` by nums[n - i - 1]
   - If pre or suff becomes 0, reset it to 1
   - Update `maxi` with max(pre, suff)

3. Return `maxi`

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

Single traversal of the array.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

Only constant extra space used.

--------------------------------------------------
Examples:
--------------------------------------------------

Example 1:
Input:
nums = [2,3,-2,4]

Process:
Prefix products  → [2, 6, -12, -48]
Suffix products  → [4, -8, -24, -48]

Output:
6

--------------------------------------------------

Example 2:
Input:
nums = [-2,0,-1]

Output:
0

Explanation:
Zero breaks subarray, so maximum product is 0.

--------------------------------------------------

Example 3:
Input:
nums = [-2,3,-4]

Output:
24

Explanation:
Subarray [-2,3,-4] gives 24.

--------------------------------------------------
*/

class Solution {

    public int maxProduct(int[] nums) {

        int pre = 1;
        int suff = 1;
        int n = nums.length;
        int maxi = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {

            if (pre == 0) pre = 1;
            if (suff == 0) suff = 1;

            pre *= nums[i];
            suff *= nums[n - i - 1];

            maxi = Math.max(maxi, Math.max(pre, suff));
        }

        return maxi;
    }
}
