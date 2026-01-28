/*
Problem: Convert Sorted List to Binary Search Tree
(LeetCode 109)

Problem Explanation:
You are given the head of a singly linked list where elements
are sorted in ascending order.

Convert this sorted linked list into a height-balanced
Binary Search Tree (BST).

A height-balanced BST means:
- The depth of the two subtrees of every node never differs by more than 1.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
- In a BST, inorder traversal gives sorted order
- Since the linked list is already sorted, we want:
  - The middle element as the root
  - Left half to form the left subtree
  - Right half to form the right subtree
- This is similar to converting a sorted array to BST,
  but here we must find the middle using pointers

--------------------------------------------------
Approach: Slow and Fast Pointer (Divide and Conquer)
--------------------------------------------------
1. If the list is empty, return null
2. If the list has one element, create a tree node and return
3. Use slow and fast pointers to find the middle node
4. The middle node becomes the root
5. Break the list into two halves
6. Recursively build left and right subtrees

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n log n)

- Finding the middle takes O(n)
- This happens for each level of recursion

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(log n)

- Recursion stack for balanced BST

--------------------------------------------------
Example Test Case:
--------------------------------------------------

Input:
head = [-10, -3, 0, 5, 9]

Output (one possible BST):
        0
       / \
     -3   9
     /   /
   -10  5

--------------------------------------------------
*/

class Solution {

    public TreeNode sortedListToBST(ListNode head) {

        // Base case: empty list
        if (head == null) {
            return null;
        }

        // Base case: single node list
        if (head.next == null) {
            return new TreeNode(head.val);
        }

        // Find middle using slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow is the middle element
        TreeNode root = new TreeNode(slow.val);

        // Disconnect left half from middle
        prev.next = null;

        // Recursively build left and right subtrees
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(slow.next);

        return root;
    }
}
