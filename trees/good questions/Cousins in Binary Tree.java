/*
Problem: Cousins in Binary Tree
(LeetCode 993)

Problem Explanation:
Two nodes in a binary tree are cousins if:
- They are at the same depth (level)
- They have different parents

You are given the root of a binary tree and two values x and y.
Return true if the nodes with values x and y are cousins,
otherwise return false.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
- Cousins must be found on the same level
- They must NOT share the same parent
- Level order traversal (BFS) is ideal because it processes the tree
  level by level

--------------------------------------------------
Approach: Level Order Traversal (BFS)
--------------------------------------------------
1. Traverse the tree level by level
2. For each level:
   - Check how many of x and y are found
   - Track if they share the same parent
3. If both x and y are found at the same level and
   do not share a parent → cousins
4. If only one is found at a level → cannot be cousins

--------------------------------------------------
Why Parent Check is Needed:
--------------------------------------------------
Even if x and y are at the same level,
they are NOT cousins if they have the same parent.
This must be checked explicitly.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

Each node is visited once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)

Queue used for BFS.

--------------------------------------------------
Example Test Cases:
--------------------------------------------------

Example 1:
Input:
root = [1,2,3,4]
x = 4, y = 3

Output:
false

--------------------------------------------------

Example 2:
Input:
root = [1,2,3,null,4,null,5]
x = 4, y = 5

Output:
true

--------------------------------------------------

Example 3:
Input:
root = [1,2,3,null,4]
x = 2, y = 3

Output:
false

--------------------------------------------------
*/

import java.util.*;

class Solution {

    public boolean isCousins(TreeNode root, int x, int y) {

        if (root == null) return false;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {

            int size = queue.size();
            boolean foundX = false;
            boolean foundY = false;

            for (int i = 0; i < size; i++) {

                TreeNode curr = queue.poll();

                // Check if children are siblings
                if (curr.left != null && curr.right != null) {
                    if ((curr.left.val == x && curr.right.val == y) ||
                        (curr.left.val == y && curr.right.val == x)) {
                        return false;
                    }
                }

                if (curr.left != null) {
                    if (curr.left.val == x) foundX = true;
                    if (curr.left.val == y) foundY = true;
                    queue.offer(curr.left);
                }

                if (curr.right != null) {
                    if (curr.right.val == x) foundX = true;
                    if (curr.right.val == y) foundY = true;
                    queue.offer(curr.right);
                }
            }

            if (foundX && foundY) return true;
            if (foundX || foundY) return false;
        }

        return false;
    }
}
