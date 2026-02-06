import java.util.*;

/*
Problem: Amount of Time for Binary Tree to Be Infected
(LeetCode 2385)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given the root of a binary tree and a starting node value.
In one minute, the infection spreads from a node to:
- its parent
- its left child
- its right child

Return the amount of time needed to infect the entire tree.

--------------------------------------------------
Intuition:
--------------------------------------------------
The tree is an undirected graph if we consider:
- child -> parent edges
- parent -> child edges

So the problem reduces to:
- Perform BFS starting from the target node
- Count the number of BFS levels until all nodes are visited

But trees only have child pointers, so:
- We first create a parent mapping
- Then do BFS in all three directions (left, right, parent)

--------------------------------------------------
Approach:
--------------------------------------------------
1. Create parent mapping using BFS from root
2. Find the target node using DFS
3. Do BFS starting from target:
   - At each level, expand to left, right, and parent
   - Count time by number of BFS layers processed

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)
Each node is visited once

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)
Parent map, visited map, and BFS queue

--------------------------------------------------
Example:
--------------------------------------------------
Tree:
        1
       / \
      5   3
       \
        4
       / \
      9   2

Start = 3

Time to infect entire tree = 4

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

    public void makepar(TreeNode root, Map<TreeNode, TreeNode> par) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();

            if (curr.left != null) {
                par.put(curr.left, curr);
                queue.add(curr.left);
            }
            if (curr.right != null) {
                par.put(curr.right, curr);
                queue.add(curr.right);
            }
        }
    }

    public TreeNode findTarget(TreeNode root, int start) {
        if (root == null) return null;
        if (root.val == start) return root;

        TreeNode left = findTarget(root.left, start);
        if (left != null) {
            return left;
        }

        return findTarget(root.right, start);
    }

    public int amountOfTime(TreeNode root, int start) {
        Map<TreeNode, TreeNode> par = new HashMap<>();
        makepar(root, par);

        TreeNode target = findTarget(root, start);

        Map<TreeNode, Boolean> visited = new HashMap<>();
        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(target);
        visited.put(target, true);

        int time = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean burned = false;

            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();

                if (curr.left != null && !visited.containsKey(curr.left)) {
                    queue.add(curr.left);
                    visited.put(curr.left, true);
                    burned = true;
                }

                if (curr.right != null && !visited.containsKey(curr.right)) {
                    queue.add(curr.right);
                    visited.put(curr.right, true);
                    burned = true;
                }

                if (par.get(curr) != null && !visited.containsKey(par.get(curr))) {
                    queue.add(par.get(curr));
                    visited.put(par.get(curr), true);
                    burned = true;
                }
            }

            if (burned) time++;
        }

        return time;
    }
}
