/**
 * 🔹 Problem: Diameter of Binary Tree
 * -----------------------------------
 * The diameter of a binary tree is the length of the longest path between any two nodes.
 * This path may or may not pass through the root.
 *
 * Example:
 *          1
 *         / \
 *        2   3
 *       / \
 *      4   5
 *
 * Longest path = 4 → 2 → 1 → 3 or 4 → 2 → 5
 * Diameter (in edges) = 3
 *
 * -----------------------------------
 * 🧠 Intuition:
 * The diameter at a node = height(left subtree) + height(right subtree)
 * So we need to calculate height for each node.
 * 
 * We can do this in:
 *   1️⃣ Naive Approach (O(N²)): Recompute height at every node.
 *   2️⃣ Optimized Approach (O(N)): Compute height and diameter together in one DFS.
 *
 * -----------------------------------
 * 📊 Time & Space Complexity:
 *   Naive Approach:
 *      ⏱️ Time: O(N²)
 *      💾 Space: O(H)   (due to recursion)
 *
 *   Optimized Approach:
 *      ⏱️ Time: O(N)
 *      💾 Space: O(H)
 */

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) {
        this.val = val;
        left = right = null;
    }
}

public class DiameterOfBinaryTree {

    // -------------------------------------------------------
    // 🧩 1️⃣ Naive Approach (O(N²))
    // -------------------------------------------------------
    public int diameterNaive(TreeNode root) {
        if (root == null) return 0;

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        int leftDiameter = diameterNaive(root.left);
        int rightDiameter = diameterNaive(root.right);

        // Diameter at this node = left height + right height
        return Math.max(leftHeight + rightHeight, Math.max(leftDiameter, rightDiameter));
    }

    private int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    // -------------------------------------------------------
    // ⚡ 2️⃣ Optimized Approach (O(N))
    // -------------------------------------------------------
    private int maxDiameter = 0;

    public int diameterOptimized(TreeNode root) {
        maxDiameter = 0;
        dfsHeight(root);
        return maxDiameter;
    }

    private int dfsHeight(TreeNode node) {
        if (node == null) return 0;

        int left = dfsHeight(node.left);
        int right = dfsHeight(node.right);

        // Update global diameter if this node has a longer path
        maxDiameter = Math.max(maxDiameter, left + right);

        // Return height of this subtree
        return 1 + Math.max(left, right);
    }

    // -------------------------------------------------------
    // 🧪 Example Usage
    // -------------------------------------------------------
    public static void main(String[] args) {
        DiameterOfBinaryTree obj = new DiameterOfBinaryTree();

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        System.out.println("Diameter (Naive): " + obj.diameterNaive(root));      // Output: 3
        System.out.println("Diameter (Optimized): " + obj.diameterOptimized(root)); // Output: 3
    }
}
