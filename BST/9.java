import java.util.*;

/*
Problem: Delete Node in a Binary Search Tree
(LeetCode 450)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given the root of a Binary Search Tree (BST) and a key.
Delete the node with value = key and return the new root.

The BST property must remain valid after deletion.

--------------------------------------------------
Detailed Intuition (Tricky Part Explained):
--------------------------------------------------
When deleting a node in a BST, there are 3 possible cases:

1) Node is a leaf (no children)
   - Just delete it and return null

2) Node has exactly one child
   - Replace the node with its child

3) Node has two children (this is the tricky case)
   - You cannot just delete it because both subtrees exist
   - Strategy:
       a) Find the inorder predecessor (largest value in left subtree)
       b) Copy its value into the current node
       c) Delete that predecessor node from the left subtree

Why predecessor?
- Inorder predecessor is guaranteed to be smaller than current node
- And greater than all other values in the left subtree
- So BST property remains valid

--------------------------------------------------
Approach:
--------------------------------------------------
1. Recursively find the node with value = key
2. Once found:
   - If leaf → return null
   - If only one child → return the non-null child
   - If two children:
        - Find predecessor from left subtree
        - Replace root value with predecessor value
        - Recursively delete predecessor from left subtree
3. Return root

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(h)
h = height of BST  
Worst case: O(n) for skewed tree  
Best case: O(log n) for balanced BST

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(h)
Recursion stack depth

--------------------------------------------------
Example:
--------------------------------------------------
BST:
        5
       / \
      3   6
     / \   \
    2   4   7

Delete key = 3

Step:
- Node 3 has two children
- Predecessor = 2
- Replace 3 with 2
- Delete original 2

Result:
        5
       / \
      2   6
       \   \
        4   7

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

    public TreeNode deleteNode(TreeNode root, int key) {

        // we have 3 cases
        if (root == null) return null;

        if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else {

            // root.val == key
            if (root.left == null && root.right == null) { // leaf node
                return null;
            } 
            else if (root.left == null) { // node has one child
                return root.right;
            } 
            else if (root.right == null) {
                return root.left;
            } 
            else { // node has 2 child
                TreeNode node = predecessor(root.left, key);
                root.val = node.val;
                root.left = deleteNode(root.left, node.val);
            }
        }

        return root;
    }

    public TreeNode predecessor(TreeNode root, int key) { // greatest in left subtree

        TreeNode result = null;

        while (root != null) {
            if (root.val > key) {
                root = root.left;
            } else {
                result = root;
                root = root.right;
            }
        }

        return result;
    }
}
