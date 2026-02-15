import java.util.*;

/*
Problem: House Robber V (LeetCode 3840 - Premium)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given two arrays:
- nums[i]: money in the i-th house
- colors[i]: color of the i-th house

You cannot rob two adjacent houses if they have the same color.

Return the maximum amount of money that can be robbed.

--------------------------------------------------
Intuition:
--------------------------------------------------
This is a variation of the classic House Robber problem.

In the classic version:
- You cannot rob two adjacent houses.

Here:
- You CAN rob adjacent houses only if their colors are different.
- You CANNOT rob adjacent houses if their colors are the same.

So at every index, the choice depends on:
1) Skipping the current house
2) Taking the current house, but:
   - If current color == previous color, you must skip the previous house
   - Else, you can take the best till previous house

--------------------------------------------------
State Definition:
--------------------------------------------------
skipPrev = max money till previous house if previous was skipped
takePrev = max money till previous house if previous was taken

--------------------------------------------------
Transition:
--------------------------------------------------
skipNow = max(skipPrev, takePrev)

takeNow =
    if colors[i] == colors[i-1]:
        nums[i] + skipPrev
    else:
        nums[i] + max(skipPrev, takePrev)

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

--------------------------------------------------
Test Cases:
--------------------------------------------------
Input:
nums   = [1,4,3,5]
colors = [1,1,2,2]
Output: 9

Input:
nums   = [3,1,2,4]
colors = [2,3,2,2]
Output: 8

Input:
nums   = [10,1,3,9]
colors = [1,1,1,2]
Output: 22

--------------------------------------------------
*/

class Solution {

    public long rob(int[] nums, int[] colors) {
        if (nums == null || nums.length == 0) return 0;

        long skipprev = 0;            // previous house skipped
        long takeprev = nums[0];     // previous house taken

        for (int i = 1; i < nums.length; i++) {

            long skipnow = Math.max(skipprev, takeprev);

            long takenow =
                (colors[i] == colors[i - 1])
                    ? nums[i] + skipprev
                    : nums[i] + Math.max(skipprev, takeprev);

            skipprev = skipnow;
            takeprev = takenow;
        }

        return Math.max(skipprev, takeprev);
    }
}
