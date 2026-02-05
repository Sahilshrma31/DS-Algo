import java.util.*;

/*
Problem: Construct Binary Search Tree from Preorder Traversal
(LeetCode 1008)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given an array representing preorder traversal of a BST,
construct the BST and return its root.

--------------------------------------------------
Approaches:
--------------------------------------------------
1) Brute Force:
   - Insert each element into BST one by one
   - Time: O(n^2) in worst case (skewed BST)
   - Space: O(n) recursion stack

2) Better Approach (n log n):
   - Sort preorder to get inorder traversal
   - Build BST from preorder + inorder using hashmap
   - Time: O(n log n) for sorting + O(n) building
   - Space: O(n)

3) Optimal Approach (O(n)):
   - Use bounds while constructing BST from preorder
   - Single pass using global index
   - Time: O(n)
   - Space: O(h) recursion stack

--------------------------------------------------
Example:
--------------------------------------------------
Input:
preorder = [8,5,1,7,10,12]

Output BST:
        8
       / \
      5   10
     / \    \
    1   7    12

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

    // ---------------- BRUTE FORCE ----------------
    // Insert each value into BST
    public TreeNode bstFromPreorderBrute(int[] preorder) {
        TreeNode root = null;
        for (int val : preorder) {
            root = insert(root, val);
        }
        return root;
    }

    private TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        if (val < root.val) {
            root.left = insert(root.left, val);
        } else {
            root.right = insert(root.right, val);
        }
        return root;
    }

    // ---------------- BETTER (N LOG N) ----------------
    public TreeNode bstFromPreorderBetter(int[] preorder) {

        int n = preorder.length;
        int[] inorder = preorder.clone();
        Arrays.sort(inorder);

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(inorder[i], i);
        }

        return buildFromPreIn(preorder, 0, n - 1, inorder, 0, n - 1, map);
    }

    private TreeNode buildFromPreIn(int[] pre, int ps, int pe,
                                    int[] in, int is, int ie,
                                    Map<Integer, Integer> map) {

        if (ps > pe || is > ie) return null;

        TreeNode root = new TreeNode(pre[ps]);
        int idx = map.get(pre[ps]);
        int leftSize = idx - is;

        root.left = buildFromPreIn(pre, ps + 1, ps + leftSize,
                                   in, is, idx - 1, map);

        root.right = buildFromPreIn(pre, ps + leftSize + 1, pe,
                                    in, idx + 1, ie, map);

        return root;
    }

    // ---------------- OPTIMAL O(N) ----------------
    int idx = 0;

    public TreeNode bstFromPreorder(int[] preorder) {
        return build(preorder, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private TreeNode build(int[] preorder, long lb, long ub) {
        if (idx == preorder.length) return null;

        int val = preorder[idx];

        if (val <= lb || val >= ub) return null;

        idx++;
        TreeNode root = new TreeNode(val);

        root.left = build(preorder, lb, val);
        root.right = build(preorder, val, ub);

        return root;
    }
}
