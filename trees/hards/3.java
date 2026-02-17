import java.util.*;

/*
Problem: Width of Binary Tree (LeetCode 662)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given the root of a binary tree, return the maximum width of the tree.

The width of a level is defined as the length between the leftmost and
rightmost non-null nodes at that level, including null nodes between them
as they would appear in a complete binary tree representation.

--------------------------------------------------
Intuition:
--------------------------------------------------
If we assign indices to nodes as in a complete binary tree:
- root at index 0
- left child at 2*i + 1
- right child at 2*i + 2

Then, for any level:
width = (lastIndex - firstIndex + 1)

However, raw indices can grow very large and cause overflow.
So at every level, we normalize indices by subtracting the minimum index
of that level (mmin). This keeps values small and safe.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Perform level order traversal using a queue.
2. Store for each node:
   - the TreeNode
   - its index in the complete binary tree representation
3. At each level:
   - record the first and last normalized index
   - compute width = last - first + 1
4. Track the maximum width.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)
Each node is visited once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)
Queue stores nodes of a level in the worst case.

--------------------------------------------------
Example:
--------------------------------------------------
Tree:
        1
       / \
      3   2
     /     \
    5       9

Level 2 indices: 1 and 4
Width = 4 - 1 + 1 = 4

--------------------------------------------------
*/

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    class Pair {
        TreeNode node;
        int idx;

        Pair(TreeNode node, int idx) {
            this.node = node;
            this.idx = idx;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, 0));

        int maxi = 0;

        while (!queue.isEmpty()) {

            int first = 0;
            int last = 0;

            int mmin = queue.peek().idx;  // normalize indices at each level
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Pair curr = queue.poll();
                int curridx = curr.idx - mmin;
                TreeNode node = curr.node;

                if (i == 0) first = curridx;
                if (i == size - 1) last = curridx;

                if (node.left != null) {
                    queue.add(new Pair(node.left, 2 * curridx + 1));
                }

                if (node.right != null) {
                    queue.add(new Pair(node.right, 2 * curridx + 2));
                }
            }

            maxi = Math.max(maxi, last - first + 1);
        }

        return maxi;
    }
}
