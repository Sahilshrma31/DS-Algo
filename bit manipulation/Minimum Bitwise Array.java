/*
====================================================
📌 Problem: Minimum Bitwise Array
====================================================

You are given a list of integers `nums`.

For each number `x` in nums:
- You need to find the **minimum integer y**
- Such that:
    y < x
    y | (y + 1) == x   (bitwise OR condition)

If no such integer exists, return `-1` for that position.

Return the resulting array.

====================================================
🧠 Key Bitwise Insight
====================================================

For a number `x`:
- We try to flip the **lowest possible bit** (from left to right)
- The idea is:
    If bit `j` in `x` is 0,
    then toggling bit `(j-1)` using XOR may give a valid `y`

Special Case:
- If `x == 2`, no valid `y` exists → return `-1`

====================================================
🧠 Approach
====================================================

For each number:
1️⃣ If number is 2 → answer = -1  
2️⃣ Iterate bits from 1 to 31:
   - If j-th bit is 0:
       y = x ^ (1 << (j - 1))
       break (minimum y found)
3️⃣ If no such bit found → return -1

====================================================
⏱ Time Complexity
====================================================
O(n * 32) ≈ O(n)

====================================================
📦 Space Complexity
====================================================
O(n)

====================================================
*/

import java.util.*;

class Solution {

    public int[] minBitwiseArray(List<Integer> nums) {

        int n = nums.size();
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {

            int x = nums.get(i);

            // Special case
            if (x == 2) {
                ans[i] = -1;
                continue;
            }

            boolean found = false;

            // Check bits from 1 to 31
            for (int j = 1; j < 32; j++) {

                // If j-th bit is already set, skip
                if ((x & (1 << j)) != 0) {
                    continue;
                }

                // Flip (j-1)-th bit to get minimum y
                ans[i] = x ^ (1 << (j - 1));
                found = true;
                break;
            }

            if (!found) {
                ans[i] = -1;
            }
        }

        return ans;
    }
}
