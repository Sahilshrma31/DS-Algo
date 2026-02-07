/*
 * Problem: Check if Two Binary Trees are Identical
 *
 * Given two binary trees, determine if they are identical.
 * Two binary trees are identical if they have the same structure and node values.
 *
 * Example:
 * Tree 1:       Tree 2:
 *     1             1
 *    / \           / \
 *   2   3         2   3
 * Output: true
 *
 * Approaches:
 * 1. Recursive Approach (DFS)
 *    - Compare nodes recursively
 *    - Time: O(n), Space: O(h) recursion stack
 *
 * 2. BFS/Iterative Approach
 *    - Use two queues to traverse level by level
 *    - Time: O(n), Space: O(n)
 */

import java.util.LinkedList;
import java.util.Queue;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    // 1. Recursive DFS Approach
    public boolean isSameTreeDFS(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;

        return isSameTreeDFS(p.left, q.left) && isSameTreeDFS(p.right, q.right);
    }

    // 2. BFS/Iterative Approach
    public boolean isSameTreeBFS(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        Queue<TreeNode> que1 = new LinkedList<>();
        Queue<TreeNode> que2 = new LinkedList<>();

        que1.offer(p);
        que2.offer(q);

        while (!que1.isEmpty() && !que2.isEmpty()) {
            TreeNode node1 = que1.poll();
            TreeNode node2 = que2.poll();

            if (node1.val != node2.val) return false;

            // Check left children
            if (node1.left != null && node2.left != null) {
                que1.offer(node1.left);
                que2.offer(node2.left);
            } else if (node1.left != null || node2.left != null) {
                return false;
            }

            // Check right children
            if (node1.right != null && node2.right != null) {
                que1.offer(node1.right);
                que2.offer(node2.right);
            } else if (node1.right != null || node2.right != null) {
                return false;
            }
        }

        return true;
    }
}

/*
 * Complexity Analysis:
 *
 * DFS/Recursive:
 * - Time: O(n) → each node visited once
 * - Space: O(h) → recursion stack, h = tree height
 *
 * BFS/Iterative:
 * - Time: O(n) → each node visited once
 * - Space: O(n) → queue can hold nodes from a level in worst-case
 *
 * Notes:
 * - Recursive approach is simple and readable
 * - BFS approach avoids stack overflow for very deep trees
 */
