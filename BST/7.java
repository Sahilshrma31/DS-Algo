import java.util.*;

/*
Problem: Validate Binary Search Tree
(LeetCode 98)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given the root of a binary tree.
Check whether it is a valid Binary Search Tree (BST).

A valid BST must satisfy:
- Left subtree values < node.val
- Right subtree values > node.val
- This property must hold for all subtrees

--------------------------------------------------
Intuition:
--------------------------------------------------
Checking only parent-child relations is not enough.
A node in the left subtree must be smaller than all ancestors
on the right path.

So we carry a valid range for every node:
- min < node.val < max

--------------------------------------------------
Approach:
--------------------------------------------------
Use recursion with value bounds:
1. Start with range (-∞, +∞)
2. For left child, update max = current node value
3. For right child, update min = current node value
4. If any node violates the range, return false

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)
Each node is visited once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(h)
Recursion stack, h = height of tree

--------------------------------------------------
Example:
--------------------------------------------------
Tree:
        5
       / \
      1   7
         / \
        6   8

Output:
true

Tree:
        5
       / \
      1   4
         / \
        3   6

Output:
false (3 is in right subtree of 5 but < 5)

--------------------------------------------------
*/

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    public boolean isValidBST(TreeNode root) {
        return solver(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean solver(TreeNode root, long min, long max) {
        if (root == null) return true;

        // if out of bounds, invalid BST
        if (root.val <= min || root.val >= max) {
            return false;
        }

        return solver(root.left, min, root.val) &&
               solver(root.right, root.val, max);
    }
}
