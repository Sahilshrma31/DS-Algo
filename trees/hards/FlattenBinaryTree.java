import java.util.*;

/*
Problem: Flatten Binary Tree to Linked List
(LeetCode 114)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given the root of a binary tree, flatten the tree into a "linked list" in-place.
The linked list should use the right child pointer to point to the next node,
and the left child pointer should always be null.

The order of nodes in the linked list should follow preorder traversal.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
Preorder traversal order is:
Root -> Left -> Right

After flattening, the tree should become:
Root -> Right -> Right -> Right ... (linked list)

We want:
root.right = next node in preorder sequence

But while flattening in-place, once we change pointers,
we lose access to the original structure.

So instead of processing in preorder (Root, Left, Right),
we process in reverse preorder:
Right -> Left -> Root

This allows us to:
- Build the linked list from the end backwards
- Always know what the "next" node should be using a `prev` pointer

--------------------------------------------------
Why Reverse Preorder Works (and Forward Preorder Fails):
--------------------------------------------------
If we try forward preorder:
- We would set root.right = previous node
- But we don't yet know what comes next in preorder
- Also, modifying pointers early breaks traversal to children

Reverse preorder:
- We process the last node first
- Then attach current node in front of the already flattened list
- `prev` always points to the already processed part (the tail of final list)

So:
- root.right = prev (correct next node in preorder)
- root.left = null
- prev = root

--------------------------------------------------
Approach:
--------------------------------------------------
1. Maintain a global pointer `prev` initially null
2. Recursively traverse:
   - flatten(root.right)
   - flatten(root.left)
3. Rewire pointers:
   - root.right = prev
   - root.left = null
   - prev = root

This effectively constructs the linked list in reverse order.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)
Each node is visited once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(h)
Recursion stack, where h is height of tree.

--------------------------------------------------
Example:
--------------------------------------------------
Input Tree:
        1
       / \
      2   5
     / \   \
    3   4   6

Output Linked List:
1 -> 2 -> 3 -> 4 -> 5 -> 6

Structure after flatten:
1
 \
  2
   \
    3
     \
      4
       \
        5
         \
          6

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

class Solution {

    TreeNode prev = null;

    public void flatten(TreeNode root) {
        if (root == null) return;

        flatten(root.right);
        flatten(root.left);

        root.right = prev;
        root.left = null;
        prev = root;
    }
}
