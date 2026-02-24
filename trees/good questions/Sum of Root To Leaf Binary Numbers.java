import java.util.*;

/*
Problem: Sum of Root To Leaf Binary Numbers (LeetCode 1022)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given the root of a binary tree where each node has a value
either 0 or 1.

Each root-to-leaf path represents a binary number.
Return the sum of all these binary numbers.

--------------------------------------------------
Intuition:
--------------------------------------------------
While traversing from root to leaf, we build the binary number
bit by bit.

If current value is val (0 or 1), then:
newValue = previousValue * 2 + val

When we reach a leaf node, we add the built number to the total sum.

--------------------------------------------------
Approach:
--------------------------------------------------
Use DFS traversal:
1. Start with currentValue = 0
2. At each node:
   currentValue = currentValue * 2 + node.val
3. If leaf node:
   add currentValue to result
4. Recurse on left and right child

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)
Each node is visited once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(h)
Recursion stack, h = height of tree.

--------------------------------------------------
Test Cases:
--------------------------------------------------
Tree:
        1
       / \
      0   1
     / \   \
    0   1   1

Binary numbers:
100 = 4
101 = 5
111 = 7

Output: 16

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
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    public int sumRootToLeaf(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int curr) {
        if (node == null) return 0;

        curr = (curr << 1) | node.val;

        if (node.left == null && node.right == null) {
            return curr;
        }

        return dfs(node.left, curr) + dfs(node.right, curr);
    }
}