import java.util.*;

/*
Problem: Magnetic Force Between Two Balls
(LeetCode 1552)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given positions of baskets on a line and an integer m representing
the number of balls to place.

Place m balls into these baskets such that the minimum distance between
any two balls is maximized.

Return the maximum possible minimum distance.

--------------------------------------------------
Intuition:
--------------------------------------------------
This is a classic "maximize the minimum" problem.

We are not asked to find exact positions directly.
Instead, we binary search on the answer (minimum distance).

For any guessed distance 'mid':
- Check if it is possible to place m balls such that
  the distance between consecutive balls is at least 'mid'.

If it is possible:
- Try to increase the distance.

If it is not possible:
- Decrease the distance.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Sort the positions array.
2. Binary search on the minimum distance:
   - left = 1 (minimum possible distance)
   - right = max(position) - min(position)
3. For each mid:
   - Greedily place balls starting from the first position.
   - If we can place m balls, mid is feasible.
4. Return the maximum feasible mid.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
Sorting: O(n log n)
Binary Search: O(log(maxDistance))
Check feasibility: O(n)

Total:
O(n log n + n log(maxDistance))

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1) extra space (sorting is in-place)

--------------------------------------------------
Examples:
--------------------------------------------------
1)
position = [1, 2, 3, 4, 7]
m = 3
Output = 3

Explanation:
Place balls at positions 1, 4, and 7.
Minimum distance = min(3, 3) = 3

2)
position = [5, 4, 3, 2, 1, 1000000000]
m = 2
Output = 999999999

Explanation:
Place balls at 1 and 1000000000.

--------------------------------------------------
*/

class Solution {

    public int maxDistance(int[] position, int m) {
        int n = position.length;
        Arrays.sort(position);

        int l = 1;
        int r = position[n - 1] - position[0];
        int max = Integer.MIN_VALUE;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (works(mid, position, m)) {
                max = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return max;
    }

    public boolean works(int mid, int arr[], int m) {
        int cnt = 1;
        int prev = arr[0];
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            if (arr[i] - prev >= mid) {
                cnt++;
                prev = arr[i];
            }
            if (cnt == m) {
                break;
            }
        }
        return cnt == m;
    }
}
