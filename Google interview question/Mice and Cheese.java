import java.util.*;

/*
Problem: Mice and Cheese
(LeetCode 2611)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
There are two mice and n pieces of cheese.
reward1[i] is the reward if mouse1 eats the i-th cheese.
reward2[i] is the reward if mouse2 eats the i-th cheese.

Mouse1 must eat exactly k cheeses.
Mouse2 eats the remaining cheeses.

Return the maximum total reward.

--------------------------------------------------
Intuition:
--------------------------------------------------
Each cheese can go to either mouse1 or mouse2.

Brute force:
- Try all possibilities
- Keep track of how many cheeses mouse1 has taken

Optimized:
- Assume all cheeses go to mouse2 first
- For each cheese, compute extra gain if given to mouse1 instead of mouse2
- Pick top k gains

--------------------------------------------------
Approach 1: Recursion (Brute Force)
--------------------------------------------------
Try all combinations with recursion.
Track:
- index i
- number of cheeses taken by mouse1 so far

Only valid if mouse1 takes exactly k cheeses.

Time Complexity:
O(2^n)

Space Complexity:
O(n) recursion stack

--------------------------------------------------
Approach 2: Greedy Optimization
--------------------------------------------------
Base reward = sum of reward2 (all cheeses to mouse2)
For each cheese:
  diff[i] = reward1[i] - reward2[i]
Sort diff
Pick largest k diffs and add to base reward

Time Complexity:
O(n log n)

Space Complexity:
O(n)

--------------------------------------------------
Examples:
--------------------------------------------------
1)
reward1 = [1, 1, 3, 4]
reward2 = [4, 4, 1, 1]
k = 2
Output = 15

2)
reward1 = [5, 3, 4]
reward2 = [1, 2, 3]
k = 1
Output = 10

--------------------------------------------------
*/

class Solution {

    // ---------------- Brute Force Recursion (Your Logic - Unchanged) ----------------
    public int miceAndCheeseBrute(int[] reward1, int[] reward2, int k) {
        int n = reward1.length;
        return solve(0, 0, reward1, reward2, k, n);
    }

    // i = current cheese index
    // taken = mouse1 ne abhi tak kitni cheese khayi
    private int solve(int i, int taken, int[] reward1, int[] reward2, int k, int n) {

        if (i == n) {
            if (taken == k) return 0;
            else return (int) -1e9;
        }

        int ans = (int) -1e9;

        int pick1 = (int) -1e9;
        if (taken < k) {
            pick1 = reward1[i] + solve(i + 1, taken + 1, reward1, reward2, k, n);
        }

        int pick2 = reward2[i] + solve(i + 1, taken, reward1, reward2, k, n);
        ans = Math.max(pick1, pick2);

        return ans;
    }

    // ---------------- Optimized Greedy Solution ----------------
    public int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int n = reward1.length;

        int tot = 0;
        int diff[] = new int[n];

        for (int i = 0; i < n; i++) {
            tot += reward2[i];
            diff[i] = reward1[i] - reward2[i];
        }

        Arrays.sort(diff);

        for (int i = n - 1; i > n - 1 - k; i--) {
            tot += diff[i];
        }

        return tot;
    }
}
