import java.util.*;

/*
Problem: Count Complete Tree Nodes
(LeetCode 222)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given the root of a COMPLETE binary tree.
Return the number of nodes in the tree.

A complete binary tree:
- All levels are completely filled except possibly the last
- Last level nodes are filled from left to right

--------------------------------------------------
Intuition:
--------------------------------------------------
In a complete binary tree:
- If the height of the leftmost path equals the height of the rightmost path,
  then the tree is a PERFECT binary tree.

For a perfect binary tree of height h:
Number of nodes = (2^h) - 1

So:
- If left height == right height → compute directly
- Else → recursively count nodes in left and right subtrees

This avoids traversing all nodes and improves performance.

--------------------------------------------------
Approach:
--------------------------------------------------
1. If root is null, return 0
2. Compute left height by going only left
3. Compute right height by going only right
4. If both heights are equal:
     return (2^height) - 1
5. Otherwise:
     return 1 + count(left subtree) + count(right subtree)

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O((log n)^2)

- Height computation takes O(log n)
- Done at most O(log n) times

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(log n)

- Recursion stack depth

--------------------------------------------------
Example:
--------------------------------------------------
Tree:
        1
       / \
      2   3
     / \  /
    4  5 6

Output:
6
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

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int lh = lefth(root);   // height from left side
        int rh = righth(root);  // height from right side

        if (lh == rh) {
            return (1 << lh) - 1;
        }

        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    public int lefth(TreeNode root) {
        int height = 0;
        while (root != null) {
            height++;
            root = root.left;
        }
        return height;
    }

    public int righth(TreeNode root) {
        int height = 0;
        while (root != null) {
            height++;
            root = root.right;
        }
        return height;
    }
}
