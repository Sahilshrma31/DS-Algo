/*
 * ⚖️ Problem: Balanced Binary Tree (LeetCode #110)
 * ------------------------------------------------------------
 * Given a binary tree, determine if it is height-balanced.
 *
 * A height-balanced binary tree is defined as:
 * ➤ A binary tree in which the left and right subtrees of every node 
 *    differ in height by no more than 1.
 *
 * Example:
 * Input Tree:
 *          1
 *        /   \
 *       2     3
 *      / \
 *     4   5
 *    /
 *   8
 * Output: false
 * ------------------------------------------------------------
 * 💡 Intuition:
 *
 * ➤ Naive Approach (O(N²)):
 *    - For every node, calculate the height of left and right subtrees.
 *    - Check if their height difference ≤ 1.
 *    - This recalculates heights multiple times, leading to O(N²).
 *
 * ➤ Optimized Approach (O(N)):
 *    - While calculating height recursively, propagate -1 upward
 *      as soon as an imbalance is found.
 *    - This ensures every node is visited once.
 * ------------------------------------------------------------
 * ⏱️ Time Complexity:
 *    ➤ Naive: O(N²)
 *    ➤ Optimized: O(N)
 *
 * 💾 Space Complexity: O(H) — due to recursion stack, where H = height of the tree.
 * ------------------------------------------------------------
 */

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class BalancedBinaryTree {

    /* ------------------------------------------------------------
       ✅ Naive Approach (O(N²))
       ------------------------------------------------------------ */
    public boolean isBalancedNaive(TreeNode root) {
        if (root == null) return true;

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        if (Math.abs(leftHeight - rightHeight) > 1) return false;

        // Recursively check for left and right subtrees
        return isBalancedNaive(root.left) && isBalancedNaive(root.right);
    }

    private int height(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    /* ------------------------------------------------------------
       ⚡ Optimized Approach (O(N)) — DFS + Height Propagation
       ------------------------------------------------------------ */
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    private int checkHeight(TreeNode root) {
        if (root == null) return 0;

        int lh = checkHeight(root.left);
        if (lh == -1) return -1;

        int rh = checkHeight(root.right);
        if (rh == -1) return -1;

        if (Math.abs(lh - rh) > 1) return -1;

        return 1 + Math.max(lh, rh);
    }

    /* ------------------------------------------------------------
       🧪 MAIN METHOD (For Testing)
       ------------------------------------------------------------ */
    public static void main(String[] args) {
        /*
                 1
               /   \
              2     3
             / \
            4   5
           /
          8
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.left.left = new TreeNode(8);

        BalancedBinaryTree obj = new BalancedBinaryTree();

        System.out.println("Naive Approach:    " + obj.isBalancedNaive(root));
        System.out.println("Optimized Approach: " + obj.isBalanced(root));
    }
}

