import java.util.*;

/*
Problem: Find Minimum and Maximum in a Binary Search Tree (BST)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given the root of a Binary Search Tree (BST), find:
- The minimum value in the BST
- The maximum value in the BST

--------------------------------------------------
Intuition:
--------------------------------------------------
BST property:
- All values in the left subtree are smaller than root.data
- All values in the right subtree are greater than root.data

So:
- The minimum value lies at the leftmost node
- The maximum value lies at the rightmost node

--------------------------------------------------
Approach:
--------------------------------------------------
Minimum:
- Keep moving left until left child is null

Maximum:
- Keep moving right until right child is null

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(h)
h = height of BST

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

--------------------------------------------------
Example:
--------------------------------------------------
BST:
        10
       /  \
      5   15
     / \    \
    2   7    20

Min = 2  
Max = 20

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

    public static int findMin(Node root) {

        if (root == null) return -1;

        while (root.left != null) {
            root = root.left;
        }

        return root.data;
    }

    public static int findMax(Node root) {

        if (root == null) return -1;

        while (root.right != null) {
            root = root.right;
        }

        return root.data;
    }
}
