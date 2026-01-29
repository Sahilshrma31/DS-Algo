/*
 * 🌲 Problem: Height (or Maximum Depth) of a Binary Tree
 * ------------------------------------------------------------
 * Given the root of a binary tree, return its height (maximum depth).
 * The height is defined as the number of nodes along the longest path 
 * from the root node down to the farthest leaf node.
 *
 * Example:
 * Input Tree:
 *          1
 *        /   \
 *       2     3
 *      / \   / \
 *     4  5  6   7
 * Output: 3
 * ------------------------------------------------------------
 * 💡 Intuition:
 *
 * ➤ Recursive (DFS approach):
 *    The height of a tree = 1 + max(height(left), height(right))
 *    We explore both left and right subtrees recursively until we reach null nodes.
 *
 * ➤ Iterative (Level Order / BFS approach):
 *    Use a queue to traverse the tree level by level.
 *    The number of levels (iterations) gives the height.
 * ------------------------------------------------------------
 * ⏱️ Time Complexity: O(N)
 *    We visit each node once.
 *
 * 💾 Space Complexity:
 *    ➤ Recursive: O(H) due to call stack (H = height)
 *    ➤ Iterative: O(W) where W = max width of the tree (queue size)
 * ------------------------------------------------------------
 */

import java.util.*;

class Node {
    int data;
    Node left, right;
    Node(int data) {
        this.data = data;
        left = right = null;
    }
}

public class HeightOfBinaryTree {

    /* ------------------------------------------------------------
       ✅ 1. Recursive Approach (DFS)
       ------------------------------------------------------------ */
    public static int heightRecursive(Node root) {
        if (root == null) return 0;
        int leftHeight = heightRecursive(root.left);
        int rightHeight = heightRecursive(root.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /* ------------------------------------------------------------
       ✅ 2. Iterative Approach (BFS / Level Order Traversal)
       ------------------------------------------------------------ */
    public static int heightIterative(Node root) {
        if (root == null) return 0;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        int height = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node curr = queue.poll();
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
            height++; // increment after processing each level
        }
        return height;
    }

    /* ------------------------------------------------------------
       🧪 MAIN METHOD (For Testing)
       ------------------------------------------------------------ */
    public static void main(String[] args) {
        /*
                 1
               /   \
              2     3
             / \   / \
            4  5  6   7
        */
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        System.out.println("Height (Recursive): " + heightRecursive(root));
        System.out.println("Height (Iterative): " + heightIterative(root));
    }
}
