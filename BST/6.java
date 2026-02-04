import java.util.*;

/*
Problem: Kth Smallest Element in a BST
(LeetCode 230)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given the root of a Binary Search Tree (BST) and an integer k.
Return the k-th smallest element in the BST.

--------------------------------------------------
Intuition:
--------------------------------------------------
Inorder traversal of a BST produces values in sorted order.
So, the k-th smallest element is simply the (k-1)-th index
in the inorder traversal result list.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Perform inorder traversal of BST
2. Store all node values in a list
3. Return list.get(k - 1)

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)
All nodes are visited once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)
Extra list to store inorder traversal values.

--------------------------------------------------
Example:
--------------------------------------------------
BST:
        5
       / \
      3   6
     / \
    2   4
   /
  1

k = 3

Inorder traversal = [1, 2, 3, 4, 5, 6]
Output = 3

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

    public void inorder(TreeNode root, ArrayList<Integer> list) {
        if (root == null) {
            return;
        }
        inorder(root.left, list);
        list.add(root.val);
        inorder(root.right, list);
    }

    public int kthSmallest(TreeNode root, int k) {

        // inorder traversal gives sorted order
        ArrayList<Integer> list = new ArrayList<>();
        inorder(root, list);

        return list.get(k - 1);
    }
}
