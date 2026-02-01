/*
Problem: Lowest Common Ancestor of a Binary Tree
(LeetCode 236)

Problem Explanation:
You are given a binary tree and two distinct nodes p and q.
The lowest common ancestor (LCA) of p and q is defined as the
lowest node in the tree that has both p and q as descendants
(a node can be a descendant of itself).

--------------------------------------------------
Core Intuition:
--------------------------------------------------
There are two classic ways to solve this problem:

1) Path-Based Approach
   - Find path from root to p
   - Find path from root to q
   - Compare both paths until they diverge
   - The last common node is the LCA

2) Recursive DFS Approach (Optimal)
   - Traverse the tree bottom-up
   - If p and q lie in different subtrees of a node,
     that node is the LCA
   - If both are in one subtree, propagate that subtree result

--------------------------------------------------
Time Complexity:
--------------------------------------------------
Both approaches: O(n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
Path-based: O(n) (paths + recursion)
Recursive DFS: O(h) (recursion stack)

--------------------------------------------------
*/

import java.util.*;

class Solution {

    /*
    --------------------------------------------------
    APPROACH 1: PATH-BASED METHOD
    --------------------------------------------------
    */

    private boolean findPath(TreeNode root, int target, List<TreeNode> path) {

        if (root == null) return false;

        path.add(root);

        if (root.val == target) return true;

        if (findPath(root.left, target, path) ||
            findPath(root.right, target, path)) {
            return true;
        }

        // backtrack
        path.remove(path.size() - 1);
        return false;
    }

    private TreeNode lcaUsingPaths(TreeNode root, TreeNode p, TreeNode q) {

        List<TreeNode> pathP = new ArrayList<>();
        List<TreeNode> pathQ = new ArrayList<>();

        findPath(root, p.val, pathP);
        findPath(root, q.val, pathQ);

        int i = 0;
        TreeNode lca = null;

        while (i < pathP.size() && i < pathQ.size()) {
            if (pathP.get(i) == pathQ.get(i)) {
                lca = pathP.get(i);
            } else {
                break;
            }
            i++;
        }

        return lca;
    }

    /*
    --------------------------------------------------
    APPROACH 2: RECURSIVE DFS (OPTIMAL)
    --------------------------------------------------
    */

    private TreeNode lcaDFS(TreeNode root, TreeNode p, TreeNode q) {

        // base case
        if (root == null) return null;

        // if current node is p or q
        if (root == p || root == q) return root;

        // search left and right
        TreeNode left = lcaDFS(root.left, p, q);
        TreeNode right = lcaDFS(root.right, p, q);

        // p and q found in different subtrees
        if (left != null && right != null) {
            return root;
        }

        // propagate non-null result
        return left != null ? left : right;
    }

    /*
    --------------------------------------------------
    MAIN FUNCTION
    --------------------------------------------------
    */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // Use the optimal DFS approach
        return lcaDFS(root, p, q);

        /*
        // If you want to use path-based approach instead:
        // return lcaUsingPaths(root, p, q);
        */
    }
}
