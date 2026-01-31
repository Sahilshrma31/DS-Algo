/*
Problem: Binary Tree Maximum Path Sum
(LeetCode 124)

Problem Explanation:
Given a binary tree where each node contains an integer value
(positive or negative), find the maximum path sum.

A path:
- Can start and end at any node
- Must follow parent-child connections
- Must contain at least one node

--------------------------------------------------
Core Idea:
--------------------------------------------------
At every node, we consider three possibilities:
1. The best path goes through the node and includes both children
2. The best path goes through the node and includes only one child
3. The best path consists of only the node itself

We keep a global variable to store the maximum path sum seen so far.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n), where n is the number of nodes

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(h), recursion stack where h is tree height
--------------------------------------------------
*/

class Solution {

    private int maxSum;

    private int solve(TreeNode root) {

        if (root == null) {
            return 0;
        }

        int left = solve(root.left);
        int right = solve(root.right);

        int neecheHiMilgayaAnswer = left + right + root.val;
        int koiEkAcha = Math.max(left, right) + root.val;
        int onlyRootAcha = root.val;

        maxSum = Math.max(
                    maxSum,
                    Math.max(
                        neecheHiMilgayaAnswer,
                        Math.max(koiEkAcha, onlyRootAcha)
                    )
                );

        // Most important part:
        // return the best single-branch path to parent
        return Math.max(koiEkAcha, onlyRootAcha);
    }

    public int maxPathSum(TreeNode root) {

        maxSum = Integer.MIN_VALUE;
        solve(root);
        return maxSum;
    }
}
