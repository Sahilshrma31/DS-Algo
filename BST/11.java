import java.util.*;

/*
Problem: Binary Search Tree Iterator
(LeetCode 173)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Implement an iterator over a Binary Search Tree (BST).
The iterator should return the next smallest number.

Operations:
- next()    : return next smallest value
- hasNext() : return whether there exists a next smallest value

--------------------------------------------------
Intuition:
--------------------------------------------------
Inorder traversal of a BST gives nodes in sorted order.

Two ways to implement iterator:

1) Brute Force:
   - Do full inorder traversal
   - Store all values in a list
   - next() returns next element from the list

2) Optimal (Stack-based, Lazy Inorder):
   - Do not store full traversal
   - Maintain a stack of nodes representing the path to the next smallest
   - At any point, the top of the stack is the next smallest node

The stack simulates the recursion of inorder traversal and only keeps
necessary nodes, reducing memory usage.

--------------------------------------------------
Approach:
--------------------------------------------------
Optimal Iterator:
- In constructor:
    Push all left nodes from root into stack
- next():
    Pop top node
    If it has a right child, push all left nodes of that right subtree
- hasNext():
    Check if stack is non-empty

Brute Iterator:
- Do inorder traversal once
- Store result in list
- Use index pointer for next()

--------------------------------------------------
Time Complexity:
--------------------------------------------------
Optimal Iterator:
- next(): O(1) amortized
- hasNext(): O(1)
- Overall traversal: O(n)

Brute Iterator:
- Constructor: O(n)
- next(): O(1)
- hasNext(): O(1)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
Optimal Iterator:
- O(h), where h is height of BST

Brute Iterator:
- O(n) for storing inorder traversal

--------------------------------------------------
Example:
--------------------------------------------------
BST:
        7
       / \
      3   15
         /  \
        9   20

Calls:
BSTIterator it = new BSTIterator(root);
it.next()    -> 3
it.next()    -> 7
it.hasNext() -> true
it.next()    -> 9
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

/* ---------------- Optimal Stack-based Iterator (Your Logic Unchanged) ---------------- */

class BSTIterator {
    Stack<TreeNode> stack = new Stack<>();

    public BSTIterator(TreeNode root) {
        pushAllLeft(root);
    }

    public void pushAllLeft(TreeNode root) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    public int next() {
        TreeNode node = stack.pop();
        if (node.right != null) {
            pushAllLeft(node.right);
        }
        return node.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }
}

/* ---------------- Brute Force Iterator Using Inorder List ---------------- */

class BSTIteratorBrute {
    private List<Integer> inorder;
    private int idx;

    public BSTIteratorBrute(TreeNode root) {
        inorder = new ArrayList<>();
        idx = 0;
        inorderTraversal(root);
    }

    private void inorderTraversal(TreeNode root) {
        if (root == null) return;
        inorderTraversal(root.left);
        inorder.add(root.val);
        inorderTraversal(root.right);
    }

    public int next() {
        return inorder.get(idx++);
    }

    public boolean hasNext() {
        return idx < inorder.size();
    }
}
