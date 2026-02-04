import java.util.*;

/*
Problem: Inorder Successor and Predecessor in a BST

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given a Binary Search Tree (BST) and a node x:
- Inorder Successor  = smallest value greater than x
- Inorder Predecessor = largest value smaller than x

If successor or predecessor does not exist, return -1.

--------------------------------------------------
Intuition:
--------------------------------------------------
In a BST:
- Left subtree contains smaller values
- Right subtree contains larger values

So while traversing:
For Successor:
- If current node value > x, it can be a candidate successor
  but we try to find a smaller one on the left
- Else move right

For Predecessor:
- If current node value < x, it can be a candidate predecessor
  but we try to find a larger one on the right
- Else move left

--------------------------------------------------
Approach:
--------------------------------------------------
Successor:
- Initialize ans = -1
- Traverse BST:
  - If root.data > x.data → update ans, move left
  - Else move right

Predecessor:
- Initialize ans = -1
- Traverse BST:
  - If root.data < x.data → update ans, move right
  - Else move left

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(h)
h = height of BST

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)
Iterative traversal

--------------------------------------------------
Example:
--------------------------------------------------
BST:
        20
       /  \
      8   22
     / \
    4  12
      /  \
     10  14

x = 10

Successor   = 12  
Predecessor = 8

--------------------------------------------------
*/

/**
 * Definition for a BST node.
 */
class Node {
    int data;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
    }
}

class Solution {

    // Your inorder successor logic (unchanged)
    public int inorderSuccessor(Node root, Node x) {

        int ans = -1;

        while (root != null) {
            if (root.data > x.data) {
                ans = root.data;
                root = root.left;
            } else {
                root = root.right;
            }
        }

        return ans;
    }

    // Inorder predecessor (same style, no logic change to successor)
    public int inorderPredecessor(Node root, Node x) {

        int ans = -1;

        while (root != null) {
            if (root.data < x.data) {
                ans = root.data;
                root = root.right;
            } else {
                root = root.left;
            }
        }

        return ans;
    }
}
