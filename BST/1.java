import java.util.*;

/*
Problem: Search in a Binary Search Tree
(LeetCode 700)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given the root of a Binary Search Tree (BST) and an integer value `val`.
Return the subtree rooted at the node with value `val`.
If such a node does not exist, return null.

--------------------------------------------------
Intuition:
--------------------------------------------------
In a BST:
- All values in the left subtree are smaller than root.val
- All values in the right subtree are greater than root.val

So:
- If current node value is smaller than val, the answer must lie in right subtree
- If current node value is greater than val, the answer must lie in left subtree

This allows us to eliminate half of the tree at each step.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Start from the root
2. While root is not null and root.val != val:
   - If root.val < val, move to right child
   - Else move to left child
3. Return root (either the found node or null)

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(h)
h = height of the BST  
Worst case: O(n) for skewed BST  
Best case: O(log n) for balanced BST

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)
Iterative traversal, no recursion stack

--------------------------------------------------
Example:
--------------------------------------------------
BST:
        4
       / \
      2   7
     / \
    1   3

val = 2

Output:
Subtree rooted at node 2:
    2
   / \
  1   3

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

    public TreeNode searchBST(TreeNode root, int val) {

        while (root != null && root.val != val) {
            root = root.val < val ? root.right : root.left;
        }

        return root;
    }
}
