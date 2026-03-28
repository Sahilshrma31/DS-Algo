import java.util.*;

/*
Problem: Shortest Common Supersequence (LeetCode 1092)

--------------------------------------------------
Problem Description
--------------------------------------------------
Given two strings str1 and str2, return the shortest
string that has both str1 and str2 as subsequences.

A subsequence is a sequence that can be derived from
another string by deleting some characters without
changing the order of the remaining characters.

If there are multiple answers, return any one.

--------------------------------------------------
Key Observations
--------------------------------------------------
1. The Shortest Common Supersequence (SCS) contains
   both strings as subsequences.
2. If characters match → include only once.
3. If characters differ → include both.
4. The problem is closely related to LCS (Longest
   Common Subsequence).
5. SCS length = n + m - LCS length.

--------------------------------------------------
Intuition
--------------------------------------------------
To build the shortest supersequence, we should avoid
repeating common characters.

So first we find the LCS of both strings.

Then we reconstruct the answer:
- If characters match → take once
- If not → take the larger contributing character

We build the answer from the DP table (reverse
traversal).

--------------------------------------------------
Approach (Using String Reverse Traversal)
--------------------------------------------------
1. Compute LCS DP table.
2. Start from dp[n][m].
3. Traverse backwards:
   - If characters match → add character and move diagonally.
   - Else move in direction of larger dp value:
       * Move up → take char from str1
       * Move left → take char from str2
4. Add remaining characters (if any).
5. Reverse the result.

--------------------------------------------------
State Definition
--------------------------------------------------
dp[i][j] =
length of LCS of first i chars of str1
and first j chars of str2

--------------------------------------------------
Transition
--------------------------------------------------
if(str1[i-1] == str2[j-1]):
    dp[i][j] = 1 + dp[i-1][j-1]
else:
    dp[i][j] = max(dp[i-1][j], dp[i][j-1])

--------------------------------------------------
Time Complexity
--------------------------------------------------
O(n * m)

--------------------------------------------------
Space Complexity
--------------------------------------------------
O(n * m)

--------------------------------------------------
Example
--------------------------------------------------
Input:
str1 = "abac"
str2 = "cab"

Output:
"cabac"

Explanation:
Both strings are subsequences of "cabac".
*/

class Solution {

    /*
    --------------------------------------------------
    Tabulation + Reverse Traversal (Optimal Approach)
    --------------------------------------------------
    */

    public String shortestCommonSupersequence(String str1, String str2) {

        int n = str1.length();
        int m = str2.length();

        // Step 1: Build LCS DP table
        int[][] dp = new int[n + 1][m + 1];

        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= m; j++) {

                if(str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Step 2: Build SCS using reverse traversal
        StringBuilder result = new StringBuilder();

        int i = n, j = m;

        while(i > 0 && j > 0) {

            if(str1.charAt(i - 1) == str2.charAt(j - 1)) {
                result.append(str1.charAt(i - 1));
                i--;
                j--;
            }
            else if(dp[i - 1][j] > dp[i][j - 1]) {
                result.append(str1.charAt(i - 1));
                i--;
            }
            else {
                result.append(str2.charAt(j - 1));
                j--;
            }
        }

        // Add remaining characters
        while(i > 0) {
            result.append(str1.charAt(i - 1));
            i--;
        }

        while(j > 0) {
            result.append(str2.charAt(j - 1));
            j--;
        }

        // Reverse final result
        return result.reverse().toString();
    }
}