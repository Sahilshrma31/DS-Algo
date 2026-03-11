import java.util.*;

/*
Problem: Asteroid Collision (LeetCode 735)

--------------------------------------------------
Problem Summary
--------------------------------------------------
We are given an array of integers representing asteroids.

Each asteroid has:
• Absolute value = size
• Sign = direction
    Positive  → moving right
    Negative  → moving left

When two asteroids collide:
• The smaller asteroid explodes
• If both are equal, both explode
• Asteroids moving in the same direction never collide

Return the final state of asteroids after all collisions.

--------------------------------------------------
Intuition
--------------------------------------------------
Collisions only happen when:
previous asteroid → moving right
current asteroid → moving left

That means:
stack.peek() > 0 AND current < 0

We simulate collisions using a stack.

The stack represents asteroids that are still alive.

--------------------------------------------------
Approach
--------------------------------------------------
1. Traverse the asteroid array.
2. Maintain a stack of surviving asteroids.
3. For each asteroid:
   while collision condition holds:
        stack top smaller → pop it
        equal size → pop stack and destroy current
        stack larger → destroy current
4. If current asteroid survives → push to stack.
5. Finally convert stack to result array.

--------------------------------------------------
Time Complexity
--------------------------------------------------
O(n)

Each asteroid is pushed and popped at most once.

--------------------------------------------------
Space Complexity
--------------------------------------------------
O(n)

Stack may hold all asteroids.

--------------------------------------------------
Example 1
--------------------------------------------------
Input:
[5, 10, -5]

Process:
5 → stack
10 → stack
-5 → collides with 10 → destroyed

Output:
[5, 10]

--------------------------------------------------
Example 2
--------------------------------------------------
Input:
[8, -8]

Process:
8 collides with -8 → both destroyed

Output:
[]

--------------------------------------------------
Example 3
--------------------------------------------------
Input:
[10, 2, -5]

Process:
2 collides with -5 → destroyed
10 collides with -5 → survives

Output:
[10]

--------------------------------------------------
*/

class Solution {

    public int[] asteroidCollision(int[] asteroids) {

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < asteroids.length; i++) {

            int curr = asteroids[i];

            while (!stack.isEmpty() && stack.peek() > 0 && curr < 0) {

                if (stack.peek() < -curr) {
                    stack.pop();     
                    continue;
                }

                else if (stack.peek() == -curr) {
                    stack.pop();     
                }

                curr = 0;            
                break;
            }

            if (curr != 0) {
                stack.push(curr);
            }
        }

        int[] result = new int[stack.size()];

        for (int i = stack.size() - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }

        return result;
    }
}