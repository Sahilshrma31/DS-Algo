/*
Problem: Partition Labels
(LeetCode 763)

Problem Explanation:
You are given a string `s`.
You need to partition the string into as many parts as possible such that:
- Each letter appears in at most one part
- Return a list of sizes of these parts

The partitions must be contiguous.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
- If a character appears multiple times, all its occurrences
  must lie within the same partition.
- So for every character, we should know its **last occurrence**.
- While forming a partition, we keep expanding the current segment
  until it covers the last occurrence of all characters inside it.

--------------------------------------------------
Approach:
--------------------------------------------------
1. First, store the last index of each character in a map.
2. Traverse the string:
   - Start a partition at index `i`
   - Set `end` as the last occurrence of `s[i]`
   - Move forward and keep extending `end` if any character
     inside the window has a later last occurrence
3. When the current index reaches `end`,
   we finalize the partition.

--------------------------------------------------
Why the Inner Loop is Still O(n):
--------------------------------------------------
Although there is a nested loop, the pointer `j`
never moves backward.
Across all partitions, `j` traverses the string only once.

So total traversal = O(n), not O(n²).

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

- One pass to compute last occurrences
- One pass to create partitions

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

- HashMap stores at most 26 characters

--------------------------------------------------
Example Test Cases:
--------------------------------------------------

Example 1:
Input:
s = "ababcbacadefegdehijhklij"

Output:
[9, 7, 8]

Explanation:
Partitions:
"ababcbaca" -> size 9
"defegde"   -> size 7
"hijhklij"  -> size 8

--------------------------------------------------

Example 2:
Input:
s = "eccbbbbdec"

Output:
[10]

Explanation:
All characters are connected through overlapping occurrences,
so only one partition is possible.

--------------------------------------------------
*/

import java.util.*;

class Solution {

    public List<Integer> partitionLabels(String s) {

        List<Integer> result = new ArrayList<>();
        int n = s.length();

        // Step 1: Store last occurrence of each character
        HashMap<Character, Integer> lastIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            lastIndex.put(s.charAt(i), i);
        }

        // Step 2: Create partitions
        int i = 0;
        while (i < n) {

            int end = lastIndex.get(s.charAt(i));

            for (int j = i; j <= end; j++) {
                char curr = s.charAt(j);
                end = Math.max(end, lastIndex.get(curr));
            }

            result.add(end - i + 1);
            i = end + 1;
        }

        return result;
    }
}

