import java.util.*;

/*
Problem: Longest Balanced Substring 2

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given a string s consisting of lowercase English letters.
A substring is called balanced if all distinct characters in the
substring appear the same number of times.

Return the length of the longest balanced substring.

--------------------------------------------------
Intuition:
--------------------------------------------------
A substring is balanced if:
1) It contains only one distinct character (aaaa)
2) It contains exactly two distinct characters with equal frequency (aabb, bbaa)
3) It contains three characters a, b, c with equal frequency (abcabc)

So we split the problem into 3 cases:

Case 1: Only one character
Case 2: Exactly two characters balanced
Case 3: All three characters balanced

--------------------------------------------------
Approach:
--------------------------------------------------
Case 1:
Find the longest streak of the same character.

Case 2:
For every pair among (a,b), (a,c), (b,c):
Use prefix difference technique:
If count(ch1) - count(ch2) is same at two indices,
the substring between them is balanced.

Case 3:
Use prefix differences:
(countA - countB, countA - countC)
If this pair repeats, substring in between is balanced.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)
Each pass is linear

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)
HashMaps store prefix states

--------------------------------------------------
Test Cases:
--------------------------------------------------
Input: "abbac"
Output: 4   // "abba"

Input: "zzabccy"
Output: 4   // "abcc"

Input: "abcabc"
Output: 6

Input: "aaaa"
Output: 4

--------------------------------------------------
*/

class Solution {

    // Case-2 helper: longest balanced substring using exactly 2 characters
    public int helper(String s, char ch1, char ch2) {
        int n = s.length();
        HashMap<Integer, Integer> diffMap = new HashMap<>();

        int maxL = 0;
        int count1 = 0;
        int count2 = 0;

        for (int i = 0; i < n; i++) {
            char curr = s.charAt(i);

            // If third character comes, reset everything
            if (curr != ch1 && curr != ch2) {
                diffMap.clear();
                count1 = 0;
                count2 = 0;
                continue;
            }

            if (curr == ch1) count1++;
            if (curr == ch2) count2++;

            // Whole prefix is balanced
            if (count1 == count2) {
                maxL = Math.max(maxL, count1 + count2);
            }

            int diff = count1 - count2;

            if (diffMap.containsKey(diff)) {
                maxL = Math.max(maxL, i - diffMap.get(diff));
            } else {
                diffMap.put(diff, i);
            }
        }

        return maxL;
    }

    public int longestBalanced(String s) {
        int n = s.length();
        int maxL = 0;

        // Case-1: only one character (aaaa / bbbb / cccc)
        int count = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                maxL = Math.max(maxL, count);
                count = 1;
            }
        }
        maxL = Math.max(maxL, count);

        // Case-2: exactly two characters balanced
        maxL = Math.max(maxL, helper(s, 'a', 'b'));
        maxL = Math.max(maxL, helper(s, 'a', 'c'));
        maxL = Math.max(maxL, helper(s, 'b', 'c'));

        // Case-3: all three characters balanced (a, b, c)
        int countA = 0, countB = 0, countC = 0;
        HashMap<String, Integer> diffMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            char curr = s.charAt(i);
            if (curr == 'a') countA++;
            if (curr == 'b') countB++;
            if (curr == 'c') countC++;

            // Whole prefix is balanced
            if (countA == countB && countA == countC) {
                maxL = Math.max(maxL, countA + countB + countC);
            }

            int diffAB = countA - countB;
            int diffAC = countA - countC;
            String key = diffAB + "_" + diffAC;

            if (diffMap.containsKey(key)) {
                maxL = Math.max(maxL, i - diffMap.get(key));
            } else {
                diffMap.put(key, i);
            }
        }

        return maxL;
    }
}
