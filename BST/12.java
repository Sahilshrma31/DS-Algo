import java.util.*;

/*
Problem: Two Sum IV - Input is a BST
(LeetCode 653)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given the root of a Binary Search Tree (BST) and an integer k,
return true if there exist two elements in the BST such that
their sum is equal to k.

--------------------------------------------------
Intuition:
--------------------------------------------------
Inorder traversal of BST gives a sorted sequence.

Two ways to solve:

1) Brute Force (Inorder + Two Pointers):
   - Convert BST to sorted list using inorder traversal
   - Use two pointers on the sorted list to find target sum
   - Uses O(n) extra space

2) Optimal (Two BST Iterators):
   - Use two iterators directly on BST
   - One from smallest, one from largest
   - Uses only O(h) space

--------------------------------------------------
Approach:
--------------------------------------------------
Brute:
- Do inorder traversal
- Store values in list
- Apply two-pointer technique

Optimal:
- Use forward and backward iterators on BST
- Simulate two pointers without extra array

--------------------------------------------------
Time Complexity:
--------------------------------------------------
Both:
O(n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
Brute:
O(n)

Optimal:
O(h)

--------------------------------------------------
Example:
--------------------------------------------------
BST:
        5
       / \
      3   6
     / \   \
    2   4   7

k = 9

Output: true

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

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

/* ---------------- BST Iterator (Forward / Next) ---------------- */

class BSTIteratorForward {
    Stack<TreeNode> stack = new Stack<>();

    public BSTIteratorForward(TreeNode root) {
        pushAllLeft(root);
    }

    private void pushAllLeft(TreeNode root) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public int next() {
        TreeNode node = stack.pop();
        if (node.right != null) {
            pushAllLeft(node.right);
        }
        return node.val;
    }
}

/* ---------------- BST Iterator (Backward / Before) ---------------- */

class BSTIteratorBackward {
    Stack<TreeNode> stack = new Stack<>();

    public BSTIteratorBackward(TreeNode root) {
        pushAllRight(root);
    }

    private void pushAllRight(TreeNode root) {
        while (root != null) {
            stack.push(root);
            root = root.right;
        }
    }

    public boolean hasPrev() {
        return !stack.isEmpty();
    }

    public int prev() {
        TreeNode node = stack.pop();
        if (node.left != null) {
            pushAllRight(node.left);
        }
        return node.val;
    }
}

/* ---------------- Main Solution (Optimal Iterator) ---------------- */

class Solution {

    public boolean findTarget(TreeNode root, int k) {

        if (root == null) return false;

        BSTIteratorForward left = new BSTIteratorForward(root);
        BSTIteratorBackward right = new BSTIteratorBackward(root);

        if (!left.hasNext() || !right.hasPrev()) return false;

        int i = left.next();
        int j = right.prev();

        while (i < j) {

            int sum = i + j;

            if (sum == k) {
                return true;
            } 
            else if (sum < k) {
                if (left.hasNext()) i = left.next();
                else break;
            } 
            else {
                if (right.hasPrev()) j = right.prev();
                else break;
            }
        }

        return false;
    }
}

/* ---------------- Brute Force (Inorder + Two Pointers) ---------------- */

class SolutionBrute {

    public boolean findTarget(TreeNode root, int k) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);

        int l = 0;
        int r = inorder.size() - 1;

        while (l < r) {
            int sum = inorder.get(l) + inorder.get(r);

            if (sum == k) return true;
            else if (sum < k) l++;
            else r--;
        }

        return false;
    }

    private void inorderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) return;

        inorderTraversal(root.left, list);
        list.add(root.val);
        inorderTraversal(root.right, list);
    }
}
