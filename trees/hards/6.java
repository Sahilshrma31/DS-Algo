import java.util.*;

/*
Problem: Burning Tree (Minimum Time to Burn Binary Tree)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given a binary tree and a target node.
The tree starts burning from the target node.

In 1 unit of time, fire spreads to:
- left child
- right child
- parent

Return the minimum time required to burn the entire tree.

--------------------------------------------------
Intuition:
--------------------------------------------------
A binary tree normally allows movement only downward.
But fire spreads in all directions, including upward.

So the tree must be treated like an undirected graph.

To move upward, we first store the parent of every node.
Then we perform BFS starting from the target node.
Each BFS level represents 1 unit of time.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Build a parent mapping using BFS
2. Start BFS from the target node
3. From each node, try to burn:
   - left child
   - right child
   - parent
4. Use a visited map to avoid revisiting nodes
5. If fire spreads in a BFS round, increment time

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)
Each node is visited once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)
Parent map, visited map, and BFS queue.

--------------------------------------------------
Example:
--------------------------------------------------
Tree:
        1
       / \
      2   3
     /
    4

Target = 2

Burn order:
Time 0: 2
Time 1: 1, 4
Time 2: 3

Output:
2
--------------------------------------------------
*/

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
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

    public int minTime(TreeNode root, TreeNode target) {
        Map<TreeNode, TreeNode> par = new HashMap<>();
        makepar(root, par);

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
