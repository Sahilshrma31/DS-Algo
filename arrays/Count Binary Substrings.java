import java.util.*;

/*
Problem: Count Binary Substrings (LeetCode 696)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given a binary string s, return the number of non-empty substrings
that have the same number of consecutive 0s and 1s.

Each such substring must have:
- All the 0s grouped together
- All the 1s grouped together

Example valid substrings:
"01", "10", "0011", "1100"

--------------------------------------------------
Intuition:
--------------------------------------------------
Any valid substring is formed at the boundary of two groups:
- a group of consecutive 0s
- followed by a group of consecutive 1s (or vice versa)

If the lengths of these two groups are:
prevGroup and currGroup

Then the number of valid substrings formed at this boundary is:
min(prevGroup, currGroup)

So we just need to track:
- length of previous group
- length of current group

--------------------------------------------------
Approach:
--------------------------------------------------
1. Traverse the string once.
2. Count consecutive equal characters.
3. When character changes:
   - add min(previous group size, current group size) to result
   - move current group size into previous
4. After loop, add the last group contribution.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

--------------------------------------------------
Test Cases:
--------------------------------------------------
Input: "00110011"
Output: 6

Input: "10101"
Output: 4

Input: "000111"
Output: 3

Input: "01"
Output: 1

--------------------------------------------------
*/

class Solution {

    public int countBinarySubstrings(String s) {

        int prevcnt = 0;
        int currcnt = 1;
        int res = 0;

        for (int i = 1; i < s.length(); i++) {

            if (s.charAt(i) == s.charAt(i - 1)) {
                currcnt++;
            } else {
                res += Math.min(prevcnt, currcnt);
                prevcnt = currcnt;
                currcnt = 1;
            }
        }

        return res + Math.min(prevcnt, currcnt);
    }
}

