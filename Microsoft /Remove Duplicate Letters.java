/*
Problem: Remove Duplicate Letters
(LeetCode 316)

Problem Explanation:
You are given a string `s` consisting of lowercase English letters.
You need to remove duplicate letters so that:
- Every letter appears exactly once
- The resulting string is the smallest in lexicographical order
  among all possible results

--------------------------------------------------
Detailed Intuition:
--------------------------------------------------

This problem is about building the lexicographically smallest string
while ensuring that every character appears only once.

Key constraints:
- You can remove characters, but you cannot reorder the remaining ones
- You must keep exactly one occurrence of each character

So this becomes:
"Choose the best position for each character such that the final
 string is lexicographically smallest."

--------------------------------------------------

Greedy + Monotonic Stack Idea:
--------------------------------------------------

We process characters from left to right and maintain a stack that
always represents the best lexicographical result so far.

At each character:
1. If the character is already used (`seen`), skip it.
2. Otherwise, try to place it as far left as possible:
   - While the top of the stack is:
     - lexicographically larger than the current character AND
     - it appears again later in the string
   - We can safely remove it and reinsert it later.
3. Push the current character into the stack.

Why the condition `i < lastSeen[top]` is critical:
- It guarantees that removing the top character is safe because
  we will encounter it again later.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

Each character is pushed and popped at most once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

- Stack size is at most 26
- Arrays of size 26 for tracking

--------------------------------------------------
Example Walkthrough:
--------------------------------------------------

Input:
s = "cbacdcbc"

lastSeen:
c → 7
b → 6
a → 2
d → 4

Processing:
c → stack: c
b → pop c (c appears later) → stack: b
a → pop b (b appears later) → stack: a
c → stack: a c
d → stack: a c d
c → already used → skip
b → pop d? no (d does not appear later)
    pop c? yes → stack: a b
c → stack: a b c
d → stack: a b c d

Output:
"abcd"

--------------------------------------------------
*/

import java.util.*;

class Solution {

    public String removeDuplicateLetters(String s) {

        boolean[] seen = new boolean[26];
        int[] lastSeen = new int[26];
        int n = s.length();

        // Record last occurrence of each character
        for (int i = 0; i < n; i++) {
            lastSeen[s.charAt(i) - 'a'] = i;
        }

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < n; i++) {

            char ch = s.charAt(i);
            int idx = ch - 'a';

            // Skip if already used
            if (seen[idx]) continue;

            // Maintain lexicographical order
            while (!stack.isEmpty()
                    && stack.peek() > ch
                    && i < lastSeen[stack.peek() - 'a']) {

                seen[stack.pop() - 'a'] = false;
            }

            stack.push(ch);
            seen[idx] = true;
        }

        // Build result
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.reverse().toString();
    }
}
