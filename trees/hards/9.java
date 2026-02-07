import java.util.*;

/*
Problem: Construct Binary Tree from Preorder and Inorder Traversal
(LeetCode 105)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given two integer arrays preorder and inorder representing the preorder
and inorder traversal of a binary tree, construct and return the binary tree.

--------------------------------------------------
Intuition:
--------------------------------------------------
Preorder traversal gives the root first.
Inorder traversal tells us how many nodes go to the left subtree and right subtree.

Using the root position in inorder:
- Left subtree size = index of root in inorder - inStart
- This tells us how to split preorder into left and right parts.

We use a hashmap to quickly find the index of any value in inorder.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Build a map from value to index for inorder traversal.
2. Recursively construct the tree:
   - Root = preorder[preStart]
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
preorder = [3, 9, 20, 15, 7]
inorder  = [9, 3, 15, 20, 7]

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
preorder = [3, 9, 20, 15, 7]
inorder  = [9, 3, 15, 20, 7]

2)
preorder = [1]
inorder  = [1]

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

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.put(inorder[i], i);
        }

        TreeNode root = build(preorder, 0, n - 1, inorder, 0, n - 1, map);
        return root;
    }

    public TreeNode build(int[] preorder, int preStart, int preEnd,
                          int[] inorder, int inStart, int inEnd,
                          Map<Integer, Integer> map) {

        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[preStart]);

        int rootidx = map.get(root.val);
        int diff = rootidx - inStart;

        root.left = build(preorder, preStart + 1, preStart + diff,
                          inorder, inStart, rootidx - 1, map);

        root.right = build(preorder, preStart + diff + 1, preEnd,
                           inorder, rootidx + 1, inEnd, map);

        return root;
    }
}

