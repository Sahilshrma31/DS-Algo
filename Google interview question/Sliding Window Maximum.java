/*
====================================================
📌 Problem: Sliding Window Maximum
(LeetCode 239)
====================================================

You are given an integer array `nums` and an integer `k`
representing the size of the sliding window.

For each window of size `k`, return the **maximum element**.

====================================================
🧠 Key Insight: Monotonic Deque
====================================================

We maintain a **deque of indices** such that:
- Elements are in **decreasing order of values**
- The **front** of deque always stores the index of
  the maximum element for the current window

Why indices?
- To check whether an element is **out of the window**

====================================================
🧠 Approach: Deque (Monotonic Queue)
====================================================

For each index `i`:
1️⃣ Remove indices from front if they are outside the window  
2️⃣ Remove indices from back if their value ≤ current value  
3️⃣ Add current index to deque  
4️⃣ Once the first window is formed (i ≥ k-1),
   take the element at the front as the maximum

====================================================
⏱ Time Complexity
====================================================
O(n)

Each index is added and removed at most once.

====================================================
📦 Space Complexity
====================================================
O(k)

Deque stores at most `k` indices.

====================================================
*/

import java.util.*;

class Solution {

    public int[] maxSlidingWindow(int[] nums, int k) {

        int n = nums.length;
        int[] result = new int[n - k + 1];

        Deque<Integer> dq = new ArrayDeque<>();
        int idx = 0;

        for (int i = 0; i < n; i++) {

            // Remove indices out of current window
            if (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            // Maintain decreasing order in deque
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) {
                dq.pollLast();
            }

            dq.addLast(i);

            // Store result once first window is formed
            if (i >= k - 1) {
                result[idx++] = nums[dq.peekFirst()];
            }
        }

        return result;
    }
}
