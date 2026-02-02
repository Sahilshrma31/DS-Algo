/*
Problem: Maximum Width of Binary Tree
(LeetCode 662)

Problem Explanation:
You are given the root of a binary tree.
The width of one level is defined as the length between the
leftmost and rightmost non-null nodes at that level,
counting the null nodes in between.

You need to return the maximum width among all levels.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
This problem is NOT about counting nodes at a level.

Instead, it is about:
- Treating the tree as if it were a complete binary tree
- Assigning each node an index like in array representation

Index rules:
- root index = 0
- left child  = 2 * index + 1
- right child = 2 * index + 2

Then:
Width of a level =
(lastIndex - firstIndex + 1)

--------------------------------------------------
Why Indexing Works:
--------------------------------------------------
Even if nodes are missing in between,
their "virtual positions" still matter for width.

Using indices preserves the horizontal distance
between nodes correctly.

--------------------------------------------------
Approach: Level Order Traversal (BFS)
--------------------------------------------------
1. Use a queue storing (TreeNode, index)
2. For each level:
   - Note index of first and last node
   - Update maximum width
3. Push children with calculated indices
4. Use long to avoid overflow

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

Each node is visited once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)

Queue storage in worst case.

--------------------------------------------------
Example:
--------------------------------------------------

Tree:
        1
       / \
      3   2
     / \   \
    5   3   9

Indices:
Level 0: [0]
Level 1: [1, 2]
Level 2: [3, 4, 6]

Width at level 2 = 6 - 3 + 1 = 4

--------------------------------------------------
*/

import java.util.*;

class Solution {

    static class Pair {
        TreeNode node;
        long idx;

        Pair(TreeNode node, long idx) {
            this.node = node;
            this.idx = idx;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {

        if (root == null) return 0;

        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 0L));

        long maxWidth = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();
            long firstIdx = queue.peek().idx;
            long lastIdx = firstIdx;

            for (int i = 0; i < size; i++) {

                Pair curr = queue.poll();
                TreeNode node = curr.node;
                long idx = curr.idx;

                lastIdx = idx;

                if (node.left != null) {
                    queue.offer(new Pair(node.left, 2 * idx + 1));
                }

                if (node.right != null) {
                    queue.offer(new Pair(node.right, 2 * idx + 2));
                }
            }

            maxWidth = Math.max(maxWidth, lastIdx - firstIdx + 1);
        }

        return (int) maxWidth;
    }
}
