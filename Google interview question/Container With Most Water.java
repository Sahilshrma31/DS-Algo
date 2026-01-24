/*
====================================================
📌 Problem: Container With Most Water
(LeetCode 11)
====================================================

You are given an integer array `height` where
`height[i]` represents the height of a vertical line
drawn at position `i`.

Find two lines that together with the x-axis form a
container that holds the **maximum amount of water**.

====================================================
🧠 Key Insight (Two Pointer Greedy)
====================================================

- The area is determined by:
  area = width × min(height[left], height[right])

- Start with the widest container (left = 0, right = n-1)

- At every step:
  - Move the pointer with the **smaller height**
  - Moving the taller pointer cannot increase area
    because width decreases and height remains bounded

This greedy choice is **provably optimal**.

====================================================
🧠 Approach: Two Pointers
====================================================

1️⃣ Initialize two pointers:
   left = 0, right = n - 1

2️⃣ Compute area at each step

3️⃣ Update maximum area

4️⃣ Move the pointer pointing to the smaller height

5️⃣ Continue until pointers meet

====================================================
⏱ Time Complexity
====================================================
O(n)

====================================================
📦 Space Complexity
====================================================
O(1)

====================================================
*/

class Solution {

    public int maxArea(int[] height) {

        int n = height.length;
        int left = 0;
        int right = n - 1;

        int maxArea = 0;

        while (left < right) {

            int width = right - left;
            int currHeight = Math.min(height[left], height[right]);
            int area = width * currHeight;

            maxArea = Math.max(maxArea, area);

            // Move pointer with smaller height
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
