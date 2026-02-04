import java.util.*;

/*
Problem: Floor and Ceil in a Binary Search Tree (BST)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given the root of a Binary Search Tree (BST) and an integer x:

- Floor of x = greatest value in BST that is <= x
- Ceil of x  = smallest value in BST that is >= x

If floor or ceil does not exist, return -1.

--------------------------------------------------
Intuition:
--------------------------------------------------
In a BST:
- Left subtree values < root
- Right subtree values > root

For Floor:
- If current node value == x → exact match, floor = x
- If current node value < x → this can be a candidate floor,
  but there might be a closer value on the right side
- If current node value > x → move to left subtree

For Ceil:
- If current node value == x → exact match, ceil = x
- If current node value > x → this can be a candidate ceil,
  but there might be a closer value on the left side
- If current node value < x → move to right subtree

--------------------------------------------------
Approach:
--------------------------------------------------
Floor:
1. Initialize result = -1
2. Traverse BST:
   - If node.data == x → return x
   - If node.data < x → update result, go right
   - Else → go left

Ceil:
1. Initialize result = -1
2. Traverse BST:
   - If node.data == x → return x
   - If node.data > x → update result, go left
   - Else → go right

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(h)
h = height of BST

Worst case: O(n) for skewed tree  
Best case: O(log n) for balanced BST

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)
Iterative traversal

--------------------------------------------------
Example:
--------------------------------------------------
BST:
        8
       / \
      4   12
     / \   \
    2   6   14

x = 5

Floor = 4  
Ceil  = 6

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

    // Your logic for Floor (unchanged)
    public static int floor(Node root, int x) {

        int result = -1;

        while (root != null) {
            if (root.data == x) {
                return x;
            } else if (root.data < x) {
                result = root.data;
                root = root.right;
            } else {
                root = root.left;
            }
        }

        return result;
    }

    // Ceil in BST (same style and logic)
    public static int ceil(Node root, int x) {

        int result = -1;

        while (root != null) {
            if (root.data == x) {
                return x;
            } else if (root.data > x) {
                result = root.data;
                root = root.left;
            } else {
                root = root.right;
            }
        }

        return result;
    }
}
