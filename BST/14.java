import java.util.*;

/*
Problem: Largest BST in a Binary Tree
(GFG)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given the root of a binary tree, find the size of the largest
subtree which is also a Binary Search Tree (BST).

--------------------------------------------------
Intuition:
--------------------------------------------------
We want to check every subtree and see if it forms a BST.
Among all BST subtrees, return the maximum size.

Two approaches:

1) Optimal (Bottom-Up DP on Tree):
   - For every node, return:
       min value in subtree
       max value in subtree
       size of subtree
       whether subtree is BST
   - Use postorder traversal
   - Combine left and right info to decide if current subtree is BST
   - Track maximum size globally

2) Brute Force:
   - For every node:
       - Check if subtree rooted at that node is a valid BST
       - If yes, count nodes in that subtree
   - Return maximum count

--------------------------------------------------
Approach:
--------------------------------------------------
Optimal:
- Do DFS postorder
- For null node: return isBST=true, size=0, min=+inf, max=-inf
- For current node:
    If left and right are BST and values satisfy:
       left.max < root.data < right.min
    Then:
       current subtree is BST
       update global answer
    Else:
       mark as not BST

Brute:
- For every node:
    - Validate BST using min/max bounds
    - If valid, count nodes
- Return max count found

--------------------------------------------------
Time Complexity:
--------------------------------------------------
Optimal:
O(n)

Brute:
O(n^2) worst case

--------------------------------------------------
Space Complexity:
--------------------------------------------------
Optimal:
O(h) recursion stack

Brute:
O(h)

--------------------------------------------------
Example:
--------------------------------------------------
Tree:
        5
       / \
      2   4
     / \
    1   3

Largest BST subtree size = 3 (subtree rooted at 2)

--------------------------------------------------
*/

/**
 * Definition for a binary tree node.
 */
class Node {
    int data;
    Node left, right;

    public Node(int d) {
        data = d;
        left = right = null;
    }
}

/* ---------------- Optimal Solution (Your Logic Unchanged) ---------------- */

class Solution {

    static class info {
        int min;
        int max;
        int size;
        boolean isBst;

        info(int min, int max, int size, boolean isBst) {
            this.min = min;
            this.max = max;
            this.size = size;
            this.isBst = isBst;
        }
    }

    static int ans;

    // Return the size of the largest sub-tree which is also a BST
    static int largestBst(Node root) {
        ans = 0;
        dfs(root);
        return ans;
    }

    static info dfs(Node root) {

        if (root == null) {
            return new info(Integer.MAX_VALUE, Integer.MIN_VALUE, 0, true);
        }

        info left = dfs(root.left);
        info right = dfs(root.right);

        // if the subtree is bst
        if (left.isBst && right.isBst && left.max < root.data && root.data < right.min) {

            int currSize = 1 + left.size + right.size;
            ans = Math.max(ans, currSize);

            int currMin = Math.min(root.data, left.min);
            int currMax = Math.max(root.data, right.max);

            return new info(currMin, currMax, currSize, true);
        }

        // else not a bst
        return new info(Integer.MIN_VALUE, Integer.MAX_VALUE, Math.max(left.size, right.size), false);
    }
}

/* ---------------- Brute Force Solution ---------------- */

class SolutionBrute {

    public static int largestBst(Node root) {
        if (root == null) return 0;

        if (isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE)) {
            return countNodes(root);
        }

        return Math.max(largestBst(root.left), largestBst(root.right));
    }

    private static boolean isValidBST(Node root, long min, long max) {
        if (root == null) return true;

        if (root.data <= min || root.data >= max) return false;

        return isValidBST(root.left, min, root.data) &&
               isValidBST(root.right, root.data, max);
    }

    private static int countNodes(Node root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}
