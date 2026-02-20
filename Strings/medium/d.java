import java.util.*;

/*
Problem: Make Largest Special (LeetCode 761)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
A binary string is called special if:
1) It has the same number of '1's and '0's
2) For any prefix of the string, the number of '1's is always
   greater than or equal to the number of '0's

Given a special binary string s, return the lexicographically
largest special string that can be formed by swapping any two
consecutive special substrings.

--------------------------------------------------
Intuition:
--------------------------------------------------
A special string can be decomposed into multiple smaller special
substrings (blocks). Each block is of the form:

1 + innerSpecialString + 0

We can recursively optimize the inner part of each block, then
rearrange these blocks in descending lexicographical order to
maximize the final string.

This is similar to sorting valid parentheses substrings.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Split the string into top-level special blocks using balance count.
2. For each block:
   - Remove outer '1' and '0'
   - Recursively make the inner string largest special
   - Wrap it back with '1' and '0'
3. Sort all optimized blocks in descending order.
4. Join them to get the final answer.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n log n) in practice due to sorting at each recursive level.
Worst case can go higher due to recursion depth and substring creation.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)
Recursion stack + storage of blocks

--------------------------------------------------
Test Cases:
--------------------------------------------------
Input: "11011000"
Output: "11100100"

Input: "10"
Output: "10"

Input: "10110010"
Output: "11001010"

--------------------------------------------------
*/

class Solution {

    public String makeLargestSpecial(String s) {

        if (s.length() <= 2) return s;

        List<String> blocks = new ArrayList<>();
        int balance = 0;
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            balance += s.charAt(i) == '1' ? 1 : -1;

            if (balance == 0) {
                blocks.add(s.substring(start, i + 1));
                start = i + 1;
            }
        }

        List<String> optimized = new ArrayList<>();

        for (String block : blocks) {
            String inner = block.substring(1, block.length() - 1);
            optimized.add("1" + makeLargestSpecial(inner) + "0");
        }

        optimized.sort((a, b) -> b.compareTo(a));

        return String.join("", optimized);
    }
}