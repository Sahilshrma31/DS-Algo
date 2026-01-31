import java.util.*;

/**
 * 💡 Problem: Boundary Traversal of Binary Tree
 * 
 * 🧾 Description:
 * Given a binary tree, return the boundary nodes in anti-clockwise order:
 * 1. Root node
 * 2. Left boundary (excluding leaves)
 * 3. All leaf nodes (left to right)
 * 4. Right boundary (excluding leaves, bottom-up)
 * 
 * Example:
 *          1
 *        /   \
 *       2     3
 *      / \   / \
 *     4   5 6   7
 *        / \
 *       8   9
 * 
 * Boundary Traversal Output: [1, 2, 4, 8, 9, 6, 7, 3]
 * 
 * 🧠 Intuition:
 * - Think of walking along the “edge” of the tree anti-clockwise.
 * - Divide into three parts: left boundary, leaves, right boundary.
 * - Left boundary → top-down, right boundary → bottom-up, leaves → left-to-right.
 * 
 * ⏱️ Time Complexity: O(N) → each node visited once
 * 💾 Space Complexity: O(H) → recursion stack + stack for right boundary, H = tree height
 */

public class BoundaryTraversal {
    
    // Definition for a binary tree node
    public static class TreeNode {
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

    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        //  Add root if not leaf
        if (!isLeaf(root)) res.add(root.val);

        //  Add left boundary
        addLeftBoundary(root.left, res);
        //  Add leaf nodes
        addLeaves(root, res);
        //  Add right boundary in bottom-up order
        addRightBoundary(root.right, res);

        return res;
    }

    // Check if node is leaf
    private boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

    // Add left boundary (top-down)
    private void addLeftBoundary(TreeNode node, List<Integer> res) {
        while (node != null) {
            if (!isLeaf(node)) res.add(node.val);
            if (node.left != null) node = node.left;
            else node = node.right;
        }
    }

    // Add leaf nodes (DFS)
    private void addLeaves(TreeNode node, List<Integer> res) {
        if (node == null) return;
        if (isLeaf(node)) {
            res.add(node.val);
            return;
        }
        addLeaves(node.left, res);
        addLeaves(node.right, res);
    }

    // Add right boundary (bottom-up)
    private void addRightBoundary(TreeNode node, List<Integer> res) {
        Stack<Integer> st = new Stack<>();
        while (node != null) {
            if (!isLeaf(node)) st.push(node.val);
            if (node.right != null) node = node.right;
            else node = node.left;
        }
        while (!st.isEmpty()) res.add(st.pop());
    }

    // Optional: Example main method to test
    public static void main(String[] args) {
        BoundaryTraversal sol = new BoundaryTraversal();
        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5, new TreeNode(8), new TreeNode(9))),
                new TreeNode(3, new TreeNode(6), new TreeNode(7))
        );
        System.out.println(sol.boundaryOfBinaryTree(root));
        // Output: [1, 2, 4, 8, 9, 6, 7, 3]
    }
}
