/*
Problem: Binary Tree Maximum Path Sum
(LeetCode 124)

Problem Explanation:
You are given a binary tree where each node contains an integer value
(which can be negative).

A path is defined as any sequence of nodes where:
- Each adjacent pair has a parent-child relationship
- The path does NOT need to pass through the root
- The path can start and end at any node
- The path must contain at least one node

The path sum is the sum of node values along the path.

Task:
Return the maximum possible path sum.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
At every node, there are two different questions to answer:

1. What is the maximum path sum that passes THROUGH this node
   and can include both left and right children?
   (This is used to update the global answer.)

2. What is the maximum path sum that starts at this node
   and can be extended to its parent?
   (This is returned to the caller.)

We must consider negative values carefully and avoid
propagating paths that reduce the sum.

--------------------------------------------------
Approach: DFS + Postorder Traversal
--------------------------------------------------
For each node:
- Compute maximum contribution from left subtree
- Compute maximum contribution from right subtree

Possible cases at the current node:
1. Path uses left + root + right
2. Path uses root + one child
3. Path uses only root

Update the global maximum using all possibilities.

Return only the best single-branch path to the parent.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

Each node is visited exactly once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(h)

Where h is the height of the tree (recursion stack).
Worst case O(n), best case O(log n).

--------------------------------------------------
Example:
--------------------------------------------------

Input Tree:
        -10
        /  \
       9   20
           / \
          15  7

Output:
42

Explanation:
Path = 15 -> 20 -> 7
--------------------------------------------------
*/

class Solution {

    private int maxSum;

    private int dfs(TreeNode root) {

        if (root == null) {
            return 0;
        }

        // Compute max contribution from left and right subtrees
        int left = dfs(root.left);
        int right = dfs(root.right);

        // Case 1: Path passes through current node (left + root + right)
        int throughRoot = left + right + root.val;

        // Case 2: Path uses root and one child
        int oneSide = Math.max(left, right) + root.val;

        // Case 3: Path uses only root
        int onlyRoot = root.val;

        // Update global maximum path sum
        maxSum = Math.max(
                maxSum,
                Math.max(throughRoot, Math.max(oneSide, onlyRoot))
        );

        // Return best path that can be extended to parent
        return Math.max(oneSide, onlyRoot);
    }

    public int maxPathSum(TreeNode root) {

        maxSum = Integer.MIN_VALUE;
        dfs(root);
        return maxSum;
    }
}
