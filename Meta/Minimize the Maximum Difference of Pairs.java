/*
Problem: Minimize the Maximum Difference of Pairs
(LeetCode 2616)

Problem Explanation:
You are given an integer array `nums` and an integer `p`.
You must form exactly `p` pairs such that:
- Each element is used at most once
- The maximum absolute difference among all chosen pairs is minimized

Return the minimum possible value of that maximum difference.

Key Intuition:
- To minimize differences, pair elements that are closest to each other
- Sorting allows greedy pairing of adjacent elements
- The answer space is monotonic, so Binary Search on the answer works

Binary Search Logic:
- Search range: [0, max(nums) - min(nums)]
- For a candidate maximum difference `mid`,
  greedily try to form pairs using adjacent elements
- If we can form at least `p` pairs, try a smaller value

Time Complexity:
- Sorting: O(n log n)
- Binary search: O(log(maxDiff))
- Pair validation: O(n)
Overall: O(n log n + n log D)
where D = max(nums) - min(nums)

Space Complexity:
O(1) extra space (excluding sorting)

--------------------------------------------------
Example Test Cases
--------------------------------------------------

Example 1:
Input:
nums = [10, 1, 2, 7, 1, 3]
p = 2

Sorted nums = [1, 1, 2, 3, 7, 10]
Pairs chosen:
(1,1) -> diff = 0
(2,3) -> diff = 1

Output:
1

--------------------------------------------------

Example 2:
Input:
nums = [4, 2, 1, 2]
p = 1

Sorted nums = [1, 2, 2, 4]
Pairs chosen:
(2,2) -> diff = 0

Output:
0

--------------------------------------------------

Example 3:
Input:
nums = [8, 9, 1, 5, 4, 3, 6]
p = 3

Sorted nums = [1, 3, 4, 5, 6, 8, 9]
Pairs chosen:
(3,4) -> diff = 1
(5,6) -> diff = 1
(8,9) -> diff = 1

Output:
1
--------------------------------------------------
*/

import java.util.*;

class Solution {

    int n;

    public int minimizeMax(int[] nums, int p) {

        Arrays.sort(nums);
        n = nums.length;

        int left = 0;
        int right = nums[n - 1] - nums[0];
        int answer = Integer.MAX_VALUE;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (isValid(nums, mid, p)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }

    private boolean isValid(int[] nums, int maxDiff, int p) {

        int pairs = 0;
        int i = 1;

        while (i < n) {

            if (nums[i] - nums[i - 1] <= maxDiff) {
                pairs++;
                i += 2;
            } else {
                i++;
            }
        }

        return pairs >= p;
    }
}
