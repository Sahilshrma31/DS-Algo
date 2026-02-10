import java.util.*;

/*
Problem: Peak Index in a Mountain Array
(LeetCode 852)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given an integer array arr that is guaranteed to be a mountain array.

A mountain array:
- Strictly increases
- Reaches a peak
- Then strictly decreases

Your task is to return the index of the peak element.

--------------------------------------------------
Intuition:
--------------------------------------------------
Since the array first increases and then decreases, the peak is the point
where the direction changes.

Binary search can be applied because:
- If arr[mid] < arr[mid + 1], we are on the increasing slope, so peak is on the right.
- Otherwise, we are on the decreasing slope or at the peak, so peak is on the left or mid.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Initialize two pointers:
   - left = 0
   - right = n - 1

2. While left < right:
   - mid = (left + right) / 2
   - If arr[mid] < arr[mid + 1]:
        move left to mid + 1
     Else:
        move right to mid

3. When loop ends, left == right and points to peak index.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(log n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

--------------------------------------------------
Examples:
--------------------------------------------------
1)
Input: arr = [0, 2, 5, 3, 1]
Output: 2

2)
Input: arr = [1, 3, 8, 12, 4, 2]
Output: 3

--------------------------------------------------
*/

class Solution {

    public int peakIndexInMountainArray(int[] arr) {
        int n = arr.length;
        int l = 0;
        int r = n - 1;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (arr[mid] < arr[mid + 1]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return r;
    }
}
