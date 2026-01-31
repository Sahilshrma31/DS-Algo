import java.util.*;

/**
 * 💡 Problem: Binary Tree Zigzag Level Order Traversal (LeetCode 103)
 * 
 * 🧾 Description:
 * Given the root of a binary tree, return the zigzag level order traversal 
 * of its nodes' values. (i.e., from left to right, then right to left for the next level and alternate between).
 * 
 * Example:
 * Input:
 *         1
 *        / \
 *       2   3
 *      / \   \
 *     4   5   6
 * 
 * Output: [[1], [3, 2], [4, 5, 6]]
 * 
 * -------------------------------------------------------
 * 🧠 Intuition:
 * This is just a normal level order traversal (BFS),
 * but with one small twist — we reverse the order of elements 
 * after every alternate level.
 * 
 * So:
 * - Level 1 → left to right
 * - Level 2 → right to left (reverse)
 * - Level 3 → left to right
 * - and so on…
 * 
 * We can achieve this easily using a queue for BFS and a boolean flag 
 * to track the direction of traversal.
 * 
 * -------------------------------------------------------
 * 🪜 Approach:
 * 1️⃣ Use a queue for normal level order traversal.
 * 2️⃣ Maintain a flag `leftToRight` (initially true).
 * 3️⃣ For each level:
 *     - Take out all nodes of that level.
 *     - Store their values in a temporary list.
 *     - Push their children (left → right).
 *     - If `leftToRight` is false → reverse the list before adding to result.
 *     - Flip the flag for the next level.
 * 
 * -------------------------------------------------------
 * ⏱️ Time Complexity: O(N)
 * - We visit each node exactly once.
 * - Reversing each level costs O(k), but total across all levels ≤ O(N).
 * 
 * 🧮 Space Complexity: O(N)
 * - Queue holds up to N/2 nodes in the worst case (last level).
 * - Result list also stores all N values.
 * 
 * -------------------------------------------------------
 * ✅ Implementation:
 */

public class ZigzagLevelOrderTraversal {
    // Definition for a binary tree node.
    public static class TreeNode {
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

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();

        if (root == null) {
            return res; // handle empty tree
        }

        queue.offer(root);
        boolean leftToRight = true; // true → left→right, false → right→left

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> temp = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                temp.add(curr.val);

                if (curr.left != null) queue.add(curr.left);
                if (curr.right != null) queue.add(curr.right);
            }

            if (!leftToRight) {
                Collections.reverse(temp); // reverse alternate levels
            }

            res.add(temp);
            leftToRight = !leftToRight; // flip the direction
        }

        return res;
    }

    // 🔍 Optional: Example main method for quick local testing
    public static void main(String[] args) {
        ZigzagLevelOrderTraversal sol = new ZigzagLevelOrderTraversal();
        TreeNode root = new TreeNode(1,
                            new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                            new TreeNode(3, null, new TreeNode(6))
                        );
        System.out.println(sol.zigzagLevelOrder(root));
    }
}
