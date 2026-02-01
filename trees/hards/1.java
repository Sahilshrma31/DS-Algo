/**
 * 🔹 Problem: Path to Given Node in a Binary Tree
 * ------------------------------------------------
 * Given the root of a binary tree and an integer B,
 * return the path from the root node to the node having value B.
 *
 * If multiple paths exist (which happens only if values repeat),
 * return any one valid path.
 *
 * 🔹 Example:
 * Input:
 *           1
 *         /   \
 *        2     3
 *       / \
 *      4   5
 * B = 5
 *
 * Output: [1, 2, 5]
 *
 * 🔹 Approach / Intuition:
 * ------------------------
 * We use **Depth-First Search (DFS)** and **backtracking** to find the
 * path from the root node to the target node.
 *
 * ✅ Steps:
 * 1. Start from the root and add the current node to a list.
 * 2. If the current node’s value matches B, return true.
 * 3. Otherwise, recursively search in the left and right subtrees.
 * 4. If neither subtree returns true, remove the current node 
 *    (backtrack) and return false.
 *
 * When recursion returns true, the list contains the full path 
 * from root to the target node.
 *
 * 🔹 Time Complexity:  O(N)
 *      → Each node is visited once.
 *
 * 🔹 Space Complexity: O(H)
 *      → For recursion stack and path list (H = height of tree).
 */

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int x) {
        val = x;
        left = null;
        right = null;
    }
}

public class Solution {

    // ✅ Main function: returns path from root to node with value B
    public ArrayList<Integer> solve(TreeNode A, int B) {
        ArrayList<Integer> res = new ArrayList<>();
        getPath(A, res, B);
        return res;
    }

    // 🔹 Helper function: recursive DFS + backtracking
    private boolean getPath(TreeNode root, ArrayList<Integer> res, int value) {
        // Base Case: null node → not found
        if (root == null) {
            return false;
        }

        // Add current node value to path
        res.add(root.val);

        // If we found the target node, stop recursion
        if (root.val == value) {
            return true;
        }

        // Recursively search in left and right subtrees
        if (getPath(root.left, res, value) || getPath(root.right, res, value)) {
            return true;
        }

        // ❌ Target not found in this path → backtrack
        res.remove(res.size() - 1);
        return false;
    }
}
