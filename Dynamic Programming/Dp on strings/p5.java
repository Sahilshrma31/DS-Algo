/*
====================================================
📌 Problem: Minimum Insertion Steps to Make a String Palindrome
(LeetCode 1312)
====================================================

Given a string s, return the minimum number of
insertions needed to make s a palindrome.

----------------------------------------------------
🧠 Key Insight (Striver Style)
----------------------------------------------------
To make a string palindrome with minimum insertions:

👉 Minimum Insertions =
   length(s) - Longest Palindromic Subsequence (LPS)
f
Why?
- Characters that are already part of the LPS
  don’t need any insertion.
- All remaining characters must be inserted
  to mirror the palindrome.

So the problem reduces to finding LPS.

----------------------------------------------------
🧩 Approach Used
----------------------------------------------------
We compute LPS using:
LPS(s) = LCS(s, reverse(s))

And then:
answer = n - LPS

----------------------------------------------------
🛠 Approaches Covered
----------------------------------------------------
1️⃣ Recursion (LCS-based)
2️⃣ Memoization (Top-Down DP)
3️⃣ Tabulation (Bottom-Up DP)
4️⃣ Space Optimized DP

----------------------------------------------------
⏱ Time & Space Complexity
----------------------------------------------------
All DP approaches:
Time: O(n²)

Space:
- Recursion: O(n)
- Memoization: O(n²)
- Tabulation: O(n²)
- Space Optimized: O(n)

====================================================
*/

import java.util.*;

class Solution {

    /* =================================================
       Helper: Reverse String
       ================================================= */

    private String reverse(String s) {
        char[] arr = s.toCharArray();
        int l = 0, r = s.length() - 1;
        while (l < r) {
            char temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            l++;
            r--;
        }
        return new String(arr);
    }

    /* =================================================
       1️⃣ RECURSION (Brute Force)
       ================================================= */

    public int minInsertionsRecursion(String s) {
        String rev = reverse(s);
        int lps = lcsRec(s, rev, s.length() - 1, s.length() - 1);
        return s.length() - lps;
    }

    private int lcsRec(String s1, String s2, int i, int j) {
        if (i < 0 || j < 0) return 0;

        if (s1.charAt(i) == s2.charAt(j)) {
            return 1 + lcsRec(s1, s2, i - 1, j - 1);
        }

        return Math.max(
            lcsRec(s1, s2, i - 1, j),
            lcsRec(s1, s2, i, j - 1)
        );
    }

    /* =================================================
       2️⃣ MEMOIZATION (Top-Down DP)
       ================================================= */

    public int minInsertionsMemo(String s) {
        String rev = reverse(s);
        int n = s.length();

        int[][] dp = new int[n][n];
        for (int[] row : dp) Arrays.fill(row, -1);

        int lps = lcsMemo(s, rev, n - 1, n - 1, dp);
        return n - lps;
    }

    private int lcsMemo(String s1, String s2, int i, int j, int[][] dp) {
        if (i < 0 || j < 0) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        if (s1.charAt(i) == s2.charAt(j)) {
            return dp[i][j] =
                1 + lcsMemo(s1, s2, i - 1, j - 1, dp);
        }

        return dp[i][j] = Math.max(
            lcsMemo(s1, s2, i - 1, j, dp),
            lcsMemo(s1, s2, i, j - 1, dp)
        );
    }

    /* =================================================
       3️⃣ TABULATION (Bottom-Up DP)
       ================================================= */

    public int minInsertionsTabulation(String s) {
        String rev = reverse(s);
        int n = s.length();

        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {

                if (s.charAt(i - 1) == rev.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(
                        dp[i - 1][j],
                        dp[i][j - 1]
                    );
                }
            }
        }

        int lps = dp[n][n];
        return n - lps;
    }

    /* =================================================
       4️⃣ SPACE OPTIMIZED DP
       ================================================= */

    public int minInsertionsSpaceOptimized(String s) {
        String rev = reverse(s);
        int n = s.length();

        int[] prev = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[n + 1];

            for (int j = 1; j <= n; j++) {

                if (s.charAt(i - 1) == rev.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(
                        prev[j],
                        curr[j - 1]
                    );
                }
            }
            prev = curr;
        }

        int lps = prev[n];
        return n - lps;
    }
}
