import java.util.*;

/*
Problem: Lowest Common Ancestor of a Binary Search Tree
(LeetCode 235)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given a Binary Search Tree (BST) and two nodes p and q,
find their Lowest Common Ancestor (LCA).

The LCA of two nodes p and q is the lowest node in the tree
that has both p and q as descendants.

--------------------------------------------------
Intuition:
--------------------------------------------------
In a BST:
- All left subtree values are smaller than root.val
- All right subtree values are greater than root.val

So:
- If both p and q are smaller than root → LCA lies in left subtree
- If both p and q are greater than root → LCA lies in right subtree
- Otherwise, current root is the LCA

--------------------------------------------------
Approach:
--------------------------------------------------
Iterative:
- Traverse from root
- Move left or right depending on p and q values
- Stop when they split or match current node

Recursive:
- Apply the same logic using recursion

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(h)
h = height of BST  
Worst case: O(n) for skewed BST  
Best case: O(log n) for balanced BST

--------------------------------------------------
Space Complexity:
--------------------------------------------------
Iterative: O(1)  
Recursive: O(h) recursion stack

--------------------------------------------------
Example:
--------------------------------------------------
BST:
        6
       / \
      2   8
     / \ / \
    0  4 7  9
      / \
     3   5

p = 2, q = 8  
LCA = 6

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

    // Your iterative solution (logic unchanged)
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        while (root != null) {
            if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else {
                return root;
            }
        }

        return root;
    }

    // Recursive solution (added as requested)
    public TreeNode lowestCommonAncestorRecursive(TreeNode root, TreeNode p, TreeNode q) {

        if (root == null) return null;

        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestorRecursive(root.right, p, q);
        } 
        else if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestorRecursive(root.left, p, q);
        } 
        else {
            return root;
        }
    }
}
