/*
    🎯 Problem: 701. Insert into a Binary Search Tree
    🔗 LeetCode Link: https://leetcode.com/problems/insert-into-a-binary-search-tree/

    🧩 Problem Summary:
    You are given the root of a Binary Search Tree (BST) and an integer val.
    Insert val into the BST such that the tree remains a valid BST.
    Return the root node of the BST after the insertion.

    Example:
    Input:
        root = [4,2,7,1,3], val = 5
    Output:
        [4,2,7,1,3,5]
*/

import java.util.*;

public class InsertIntoBST {

    /*
        💡 Intuition:
        - A BST follows a sorted property:
            left < root < right
        - Traverse the tree to find the correct null spot where `val` should be inserted.
        - If `val` < current node → go left
        - If `val` > current node → go right
        - When you hit a null → insert a new node there.
    */

    // ✅ Approach 1: Recursive (cleaner and elegant)
    public TreeNode insertIntoBSTRecursive(TreeNode root, int val) {
        // Base Case: found the insertion spot
        if (root == null)
            return new TreeNode(val);

        if (val < root.val)
            root.left = insertIntoBSTRecursive(root.left, val);
        else
            root.right = insertIntoBSTRecursive(root.right, val);

        return root;
    }

    /*
        🔁 Approach 2: Iterative (simple & efficient)
        - Traverse the tree manually with a pointer (curr)
        - Move left or right until a null child is found
        - Insert the new node there
    */
    public TreeNode insertIntoBSTIterative(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);

        TreeNode curr = root;

        while (true) {
            if (val < curr.val) {
                if (curr.left == null) {
                    curr.left = new TreeNode(val);
                    break;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new TreeNode(val);
                    break;
                }
                curr = curr.right;
            }
        }

        return root;
    }

    /*
        🧮 Time Complexity:
            - Recursive: O(h)
            - Iterative: O(h)
              where h = height of BST (O(log n) for balanced, O(n) for skewed)

        💾 Space Complexity:
            - Recursive: O(h) (stack space)
            - Iterative: O(1)
    */

    // 🧪 Driver Code for testing
    public static void main(String[] args) {
        InsertIntoBST obj = new InsertIntoBST();

        TreeNode root = new TreeNode(8);
        root.left = new TreeNode(4);
        root.right = new TreeNode(12);

        int val = 5;

        System.out.println("Before insertion (Inorder):");
        inorder(root);
        obj.insertIntoBSTIterative(root, val);
        System.out.println("\nAfter insertion (Inorder):");
        inorder(root);
    }

    static void inorder(TreeNode root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }
}

// ⚙️ TreeNode definition
class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}
