import java.util.*;

/*
Problem: Edit Distance (LeetCode 72)

--------------------------------------------------
Problem Description
--------------------------------------------------
Given two strings word1 and word2, return the minimum
number of operations required to convert word1 into word2.

You are allowed the following operations:
1. Insert a character
2. Delete a character
3. Replace a character

--------------------------------------------------
Key Observations
--------------------------------------------------
1. If characters match → no operation needed.
2. If characters don't match → we have 3 choices:
   - Insert
   - Delete
   - Replace
3. We need to minimize the total number of operations.
4. This is a classic DP problem similar to LCS.

--------------------------------------------------
Intuition
--------------------------------------------------
We compare both strings from the end.

At each step:
- If characters match → move both pointers
- Else → try all 3 operations and take minimum

This forms a recursive structure with overlapping
subproblems → perfect for DP.

--------------------------------------------------
Approach
--------------------------------------------------
1. Start from last indices of both strings.
2. If characters match → move both pointers.
3. If not:
   - Insert → stay at same i, move j
   - Delete → move i, stay at same j
   - Replace → move both
4. Take minimum of all operations + 1
5. Apply memoization and then tabulation.

--------------------------------------------------
State Definition
--------------------------------------------------
dp[i][j] =
minimum operations to convert first i characters
of word1 to first j characters of word2

--------------------------------------------------
Transition
--------------------------------------------------
if(word1[i-1] == word2[j-1]):
    dp[i][j] = dp[i-1][j-1]
else:
    dp[i][j] = 1 + min(
        dp[i-1][j],     // delete
        dp[i][j-1],     // insert
        dp[i-1][j-1]    // replace
    )

--------------------------------------------------
Time Complexity
--------------------------------------------------
Recursion      : O(3^n)
Memoization    : O(n * m)
Tabulation     : O(n * m)

--------------------------------------------------
Space Complexity
--------------------------------------------------
Recursion      : O(n + m)
Memoization    : O(n * m)
Tabulation     : O(n * m)

--------------------------------------------------
Example
--------------------------------------------------
Input:
word1 = "horse"
word2 = "ros"

Output:
3

Explanation:
horse → rorse (replace h → r)
rorse → rose (delete r)
rose → ros (delete e)
*/

class Solution {

    /*
    --------------------------------------------------
    1. Recursion (Brute Force)
    --------------------------------------------------
    */

    public int solveRec(int i, int j, String s1, String s2) {

        // if s1 exhausted → insert remaining chars of s2
        if(i < 0) return j + 1;

        // if s2 exhausted → delete remaining chars of s1
        if(j < 0) return i + 1;

        if(s1.charAt(i) == s2.charAt(j)) {
            return solveRec(i - 1, j - 1, s1, s2);
        }

        int insert = solveRec(i, j - 1, s1, s2);
        int delete = solveRec(i - 1, j, s1, s2);
        int replace = solveRec(i - 1, j - 1, s1, s2);

        return 1 + Math.min(insert, Math.min(delete, replace));
    }

    /*
    --------------------------------------------------
    2. Memoization (Top Down DP)
    --------------------------------------------------
    */

    public int solveMemo(int i, int j, String s1, String s2, int[][] dp) {

        if(i < 0) return j + 1;
        if(j < 0) return i + 1;

        if(dp[i][j] != -1) return dp[i][j];

        if(s1.charAt(i) == s2.charAt(j)) {
            return dp[i][j] = solveMemo(i - 1, j - 1, s1, s2, dp);
        }

        int insert = solveMemo(i, j - 1, s1, s2, dp);
        int delete = solveMemo(i - 1, j, s1, s2, dp);
        int replace = solveMemo(i - 1, j - 1, s1, s2, dp);

        return dp[i][j] =
            1 + Math.min(insert, Math.min(delete, replace));
    }

    /*
    --------------------------------------------------
    3. Tabulation (Bottom Up DP)
    --------------------------------------------------
    */

    public int minDistance(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        int[][] dp = new int[n + 1][m + 1];

        // Base cases
        for(int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        for(int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= m; j++) {

                if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {

                    int insert = dp[i][j - 1];
                    int delete = dp[i - 1][j];
                    int replace = dp[i - 1][j - 1];

                    dp[i][j] =
                        1 + Math.min(insert, Math.min(delete, replace));
                }
            }
        }

        return dp[n][m];
    }

    /*
    --------------------------------------------------
    4. Space Optimized DP
    --------------------------------------------------
    */

    public int minDistanceSpaceOptimized(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        int[] prev = new int[m + 1];

        for(int j = 0; j <= m; j++) {
            prev[j] = j;
        }

        for(int i = 1; i <= n; i++) {

            int[] curr = new int[m + 1];
            curr[0] = i;

            for(int j = 1; j <= m; j++) {

                if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {

                    int insert = curr[j - 1];
                    int delete = prev[j];
                    int replace = prev[j - 1];

                    curr[j] =
                        1 + Math.min(insert, Math.min(delete, replace));
                }
            }

            prev = curr;
        }

        return prev[m];
    }
}