/**
 * Problem: Children Sum Property in Binary Tree
 * 
 * Description:
 * Modify a given binary tree so that every node's value becomes equal 
 * to the sum of its children's values. 
 * If a node’s children sum is less than its own data, 
 * propagate the parent’s value down to the children.
 * 
 * Intuition:
 * - Use DFS traversal.
 * - While going down, ensure children are at least equal to parent.
 * - While coming back up, update the node’s data as the sum of its children.
 * 
 * This ensures the entire tree follows the Children Sum Property.
 */

class BinaryTreeNode<T> {
    public T data;
    public BinaryTreeNode<T> left;
    public BinaryTreeNode<T> right;

    BinaryTreeNode(T data) {
        this.data = data;
        left = null;
        right = null;
    }
}

public class Solution {

    public static void changeTree(BinaryTreeNode<Integer> root) {
        if (root == null) return;

        int child = 0;

        // Step 1: Calculate children sum
        if (root.left != null) {
            child += root.left.data;
        }
        if (root.right != null) {
            child += root.right.data;
        }

        // Step 2: Top-down correction
        if (child >= root.data) root.data = child;
        else {
            if (root.left != null) root.left.data = root.data;
            else if (root.right != null) root.right.data = root.data;
        }

        // Step 3: Recursive DFS traversal
        changeTree(root.left);
        changeTree(root.right);

        // Step 4: Bottom-up update
        int total = 0;
        if (root.left != null) total += root.left.data;
        if (root.right != null) total += root.right.data;

        if (root.left != null || root.right != null)
            root.data = total;
    }
}

/**
 * Time Complexity: O(N)
 * - Each node is visited once in DFS.
 * 
 * Space Complexity: O(H)
 * - Recursive stack space where H is the height of the tree.
 * - O(log N) for balanced tree, O(N) for skewed tree.
 */
