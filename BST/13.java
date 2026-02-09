import java.util.*;

/*
Problem: Recover Binary Search Tree
(LeetCode 99)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given the root of a Binary Search Tree (BST) where
two nodes have been swapped by mistake. Recover the tree
without changing its structure.

--------------------------------------------------
Intuition:
--------------------------------------------------
Inorder traversal of a BST gives a sorted sequence.

When two nodes are swapped:
- The inorder sequence will have violations
- There are two cases:
  1) Adjacent swapped nodes -> one violation
  2) Non-adjacent swapped nodes -> two violations

--------------------------------------------------
Approaches:
--------------------------------------------------
1) Optimal (O(1) extra space ignoring recursion stack):
   - Do inorder traversal
   - Track previous node
   - Identify first, middle, last nodes based on violations
   - Swap values to fix BST

2) Brute Force:
   - Do inorder traversal
   - Store all node values in a list
   - Sort the list
   - Do another inorder traversal and replace node values from sorted list

--------------------------------------------------
Approach Details:
--------------------------------------------------
Optimal:
- On first violation: record first = prev, middle = current
- On second violation: record last = current
- After traversal:
    - If last != null -> swap first and last
    - Else -> swap first and middle

Brute:
- Get inorder list
- Sort it
- Reassign values back in inorder order

--------------------------------------------------
Time Complexity:
--------------------------------------------------
Optimal:
O(n)

Brute:
O(n log n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
Optimal:
O(h) recursion stack

Brute:
O(n)

--------------------------------------------------
Example:
--------------------------------------------------
Input BST (wrong):
        3
       / \
      1   4
         /
        2

Correct BST:
        2
       / \
      1   4
         /
        3

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

/* ---------------- Optimal Solution (Your Logic Unchanged) ---------------- */

class Solution {

    TreeNode first = null;
    TreeNode middle = null;
    TreeNode last = null;
    TreeNode prev = new TreeNode(Integer.MIN_VALUE);

    public void recoverTree(TreeNode root) {
        inorder(root);

        if (last != null) {
            // non-adjacent swapped nodes
            int temp = first.val;
            first.val = last.val;
            last.val = temp;
        } else {
            // adjacent swapped nodes
            int temp = first.val;
            first.val = middle.val;
            middle.val = temp;
        }
    }

    private void inorder(TreeNode root) {
        if (root == null) return;

        inorder(root.left);

        // violation check
        if (prev.val > root.val) {
            if (first == null) {
                first = prev;
                middle = root;
            } else {
                last = root;
            }
        }

        prev = root;

        inorder(root.right);
    }
}

/* ---------------- Brute Force Solution (Inorder + Sort + Replace) ---------------- */

class Solution {
    List<TreeNode> nodes=new ArrayList<>();  //for reference of node
    List<Integer> values=new ArrayList<>(); //for values

    public void recoverTree(TreeNode root) {
        inorder(root);

        List<Integer> sorted=new ArrayList<>(values);
        Collections.sort(sorted);

        TreeNode first=null; TreeNode second=null;

        for(int i=0;i<sorted.size();i++){
            if(values.get(i)!=sorted.get(i)){
                if(first==null){
                    first=nodes.get(i);
                }else{
                    second=nodes.get(i);
                }
            }
        }

        int temp=first.val;
        first.val=second.val;
        second.val=temp;
    }

    public void inorder(TreeNode root){
        if(root==null){
            return;
        }
        inorder(root.left);
        values.add(root.val);
        nodes.add(root);
        inorder(root.right);

    }
}
