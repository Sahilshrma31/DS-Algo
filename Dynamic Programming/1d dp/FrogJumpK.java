/*
Frog Jump with K-distance jumps - Striver-style approaches
Filename: FrogJumpK.java

Problem Summary:
Given an integer array height[] of size n where height[i] is the height of the i-th stair, a frog starts from stair 0 and wants to reach stair n-1.
From any stair i the frog can jump to any stair i + j where 1 <= j <= k (k is given). The cost of a jump from i to i+j is |height[i+j] - height[i]|.
Return the minimum total cost to reach stair n-1.

Constraints:
1 <= n <= 1e5
0 <= height[i] <= 1e4
1 <= k <= n-1

This file contains four approaches (Striver style):
1) Recursion (brute force)
2) Recursion + Memoization (Top-down DP)
3) Tabulation (Bottom-up DP)
4) Space-optimized DP (keep only last k values in circular buffer) - reduces space from O(n) to O(k)

Each approach includes intuition, code, time complexity and space complexity.

Example:
heights = [30, 20, 50, 10, 40], k = 2
Minimum cost = 30 (0 -> 2 cost 20, 2 -> 4 cost 10)

Notes on space-optimized approach:
- Since dp[i] depends only on dp[i-1], dp[i-2], ..., dp[i-k], we can keep only last k dp values rather than entire dp array.
- This gives O(k) extra space. If k is O(1) this is constant; worst-case k = n so O(n).

Author: Generated for GitHub (contains full explanations + tested Java implementations)
*/

import java.util.Arrays;
import java.util.Scanner;

public class FrogJumpK {

    // ------------------ 1) Recursion (Brute force) ------------------
    // Intuition:
    // From index idx we try all possible jumps j in [1..k] and recursively compute min cost to reach idx.
    // Base case: idx == 0 -> cost 0.
    // Exponential time: O(k^n) in worst-case (very bad). Use only for tiny n when learning.
    // Time Complexity: O(k^n) (exponential)
    // Space Complexity: O(n) recursion stack
    public static int minCostRec(int[] height, int k, int idx) {
        if (idx == 0) return 0;
        int ans = Integer.MAX_VALUE;
        for (int j = 1; j <= k; j++) {
            if (idx - j >= 0) {
                int cost = minCostRec(height, k, idx - j) + Math.abs(height[idx] - height[idx - j]);
                ans = Math.min(ans, cost);
            }
        }
        return ans;
    }

    // ------------------ 2) Memoization (Top-down DP) ------------------
    // Intuition:
    // Same recurrence as recursion but store results in dp[] to avoid recomputation.
    // Time Complexity: O(n * k) because for each idx (n states) we iterate up to k transitions.
    // Space Complexity: O(n) for dp + O(n) recursion stack.
    public static int minCostMemo(int[] height, int k) {
        int n = height.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return memoHelper(height, k, n - 1, dp);
    }

    private static int memoHelper(int[] height, int k, int idx, int[] dp) {
        if (idx == 0) return 0;
        if (dp[idx] != -1) return dp[idx];
        int ans = Integer.MAX_VALUE;

        
        for (int j = 1; j <= k; j++) {
            if (idx - j >= 0) {
                int cost = memoHelper(height, k, idx - j, dp) + Math.abs(height[idx] - height[idx - j]);
                ans = Math.min(ans, cost);
            }
        }
        dp[idx] = ans;
        return ans;
    }

    // ------------------ 3) Tabulation (Bottom-up DP) ------------------
    // Intuition:
    // Build dp[0..n-1] iteratively. dp[0] = 0. For i from 1..n-1, dp[i] = min over j=1..k of (dp[i-j] + cost(i, i-j)).
    // Time Complexity: O(n * k)
    // Space Complexity: O(n)
    public static int minCostTab(int[] height, int k) {
        int n = height.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // starting point
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                if (i - j >= 0) {
                    int cost = dp[i - j] + Math.abs(height[i] - height[i - j]);
                    dp[i] = Math.min(dp[i], cost);
                }
            }
        }
        return dp[n - 1];
    }

    // ------------------ 4) Space-optimized (keep only last k dp values) ------------------
    // Intuition:
    // dp[i] only depends on the previous k values: dp[i-1], dp[i-2], ..., dp[i-k].
    // We can store a circular buffer of size k+1 (or k) to keep recent dp values and reuse them.
    // This reduces auxiliary space from O(n) to O(k).
    // Time Complexity: O(n * k)
    // Space Complexity: O(k)
    public static int minCostSpaceOpt(int[] height, int k) {
        int n = height.length;
        if (n == 0) return 0;
        if (n == 1) return 0;
        // buffer size k (we need to access up to k previous values). Use size = k to store dp for last k indices.
        int bufSize = Math.min(k, n); // if k >= n then storing n values is fine
        int[] buf = new int[bufSize];
        // initialize for index 0
        // we'll map dp[i] to buf[i % bufSize]
        for (int i = 0; i < bufSize; i++) buf[i] = Integer.MAX_VALUE;
        buf[0 % bufSize] = 0; // dp[0] = 0

        for (int i = 1; i < n; i++) {
            int best = Integer.MAX_VALUE;
            for (int j = 1; j <= k; j++) {
                if (i - j >= 0) {
                    int prevDp = buf[(i - j) % bufSize];
                    // prevDp should be valid (but if bufSize < n it's possible earlier entries were overwritten -
                    // using modulo works only because we always overwrite dp[i] after computing all indices < i,
                    // and we only ever need last k values. Since bufSize == k (or >=k), values needed will still be present.
                    int cost = prevDp + Math.abs(height[i] - height[i - j]);
                    best = Math.min(best, cost);
                }
            }
            buf[i % bufSize] = best;
        }
        return buf[(n - 1) % bufSize];
    }

    // ------------------ Utility: simple runner / sample main ------------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter n (number of stairs):");
        int n = sc.nextInt();
        int[] height = new int[n];
        System.out.println("Enter heights:");
        for (int i = 0; i < n; i++) height[i] = sc.nextInt();
        System.out.println("Enter k (max jump distance):");
        int k = sc.nextInt();

        System.out.println("\n--- Results ---");
        // Recursion (careful: only for small n)
        if (n <= 20) { // safe guard: avoid huge exponential runs
            int ansRec = minCostRec(height, k, n - 1);
            System.out.println("Recursion answer: " + ansRec);
        } else {
            System.out.println("Recursion answer: skipped (n too large for naive recursion)");
        }

        int ansMemo = minCostMemo(height, k);
        System.out.println("Memoization (Top-down) answer: " + ansMemo);

        int ansTab = minCostTab(height, k);
        System.out.println("Tabulation (Bottom-up) answer: " + ansTab);

        int ansSpace = minCostSpaceOpt(height, k);
        System.out.println("Space-optimized answer (O(k) space): " + ansSpace);

        System.out.println("\nTime/Space summary:");
        System.out.println("Recursion: Time = exponential, Space = O(n) recursion stack");
        System.out.println("Memoization: Time = O(n * k), Space = O(n) + O(n) recursion stack");
        System.out.println("Tabulation: Time = O(n * k), Space = O(n)");
        System.out.println("Space-optimized: Time = O(n * k), Space = O(k) (or O(n) worst-case if k ~= n)");

        sc.close();
    }
}
