import java.util.*;

/*
Problem: Construct Binary Tree from Inorder and Postorder Traversal
(LeetCode 106)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given two integer arrays inorder and postorder representing the inorder
and postorder traversal of a binary tree, construct and return the binary tree.

--------------------------------------------------
Intuition:
--------------------------------------------------
Postorder traversal gives the root at the end.
Inorder traversal tells us how many nodes go to the left subtree and right subtree.

Using the root position in inorder:
- Left subtree size = rootIdx - inStart
- This helps split the postorder array into left and right parts.

A HashMap is used to quickly find the index of any value in inorder.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Build a map from value to index for inorder traversal.
2. Recursively construct the tree:
   - Root = postorder[postEnd]
   - Find root index in inorder
   - Number of nodes in left subtree = rootIdx - inStart
   - Recursively build left and right subtrees using calculated ranges.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)
Each node is processed once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)
HashMap + recursion stack.

--------------------------------------------------
Example:
--------------------------------------------------
Input:
inorder   = [9, 3, 15, 20, 7]
postorder = [9, 15, 7, 20, 3]

Constructed Tree:
        3
       / \
      9  20
         / \
        15  7

--------------------------------------------------
Test Cases:
--------------------------------------------------
1)
inorder   = [9, 3, 15, 20, 7]
postorder = [9, 15, 7, 20, 3]

2)
inorder   = [1]
postorder = [1]

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

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n = inorder.length;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.put(inorder[i], i);
        }

        TreeNode root = build(inorder, 0, n - 1, postorder, 0, n - 1, map);
        return root;
    }

    public TreeNode build(int[] inorder, int inStart, int inEnd,
                          int[] postorder, int postStart, int postEnd,
                          Map<Integer, Integer> map) {

        if (inStart > inEnd || postStart > postEnd) {
            return null;
        }

        TreeNode root = new TreeNode(postorder[postEnd]);

        int rootIdx = map.get(root.val);
        int diff = rootIdx - inStart;

        root.left = build(inorder, inStart, rootIdx - 1,
                          postorder, postStart, postStart + diff - 1, map);

        root.right = build(inorder, rootIdx + 1, inEnd,
                           postorder, postStart + diff, postEnd - 1, map);

        return root;
    }
}

