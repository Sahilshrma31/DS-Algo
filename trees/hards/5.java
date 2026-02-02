/*
Problem: All Nodes Distance K in Binary Tree
(LeetCode 863)

Problem Explanation:
You are given:
- The root of a binary tree
- A target node
- An integer k

Task:
Return all the values of nodes that are exactly k distance away
from the target node.

Distance is measured as the number of edges.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
A binary tree normally allows movement only downward
(left and right child).

But from the target node, valid moves are:
- left child
- right child
- parent

So effectively, the tree needs to be treated like an
undirected graph.

To do this:
1) First store parent pointers for every node
2) Then perform BFS starting from the target node
3) Expand level by level until distance k

--------------------------------------------------
Approach:
--------------------------------------------------

STEP 1: Build Parent Mapping
- Traverse the tree using BFS
- For every node, store its parent
- This allows upward traversal later

STEP 2: BFS from Target
- Start BFS from the target node
- Use a visited map to avoid revisiting nodes
- From each node, explore:
  - left child
  - right child
  - parent

STEP 3: Stop at Distance K
- Each BFS level corresponds to distance +1
- When distance == k, stop BFS
- Remaining nodes in the queue are the answer

--------------------------------------------------
Why BFS?
--------------------------------------------------
- BFS naturally explores nodes level by level
- Distance in an unweighted graph is shortest path length
- Tree + parent edges becomes an unweighted graph

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

- Parent mapping: O(n)
- BFS traversal: O(n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)

- Parent map
- Visited map
- BFS queue

--------------------------------------------------
Example:
--------------------------------------------------

Tree:
        3
       / \
      5   1
     / \  / \
    6  2 0  8
      / \
     7   4

Target = 5, k = 2

Answer:
[7, 4, 1]

--------------------------------------------------
*/

import java.util.*;

class Solution {

    // Build parent mapping using BFS
    private void buildParentMap(TreeNode root,
                                Map<TreeNode, TreeNode> parent) {

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {

            TreeNode curr = queue.poll();

            if (curr.left != null) {
                parent.put(curr.left, curr);
                queue.offer(curr.left);
            }

            if (curr.right != null) {
                parent.put(curr.right, curr);
                queue.offer(curr.right);
            }
        }
    }

    public List<Integer> distanceK(TreeNode root,
                                   TreeNode target,
                                   int k) {

        // Step 1: parent mapping
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        buildParentMap(root, parent);

        // Step 2: BFS from target
        Queue<TreeNode> queue = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();

        queue.offer(target);
        visited.add(target);

        int distance = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            if (distance == k) break;

            for (int i = 0; i < size; i++) {

                TreeNode curr = queue.poll();

                // left child
                if (curr.left != null && !visited.contains(curr.left)) {
                    visited.add(curr.left);
                    queue.offer(curr.left);
                }

                // right child
                if (curr.right != null && !visited.contains(curr.right)) {
                    visited.add(curr.right);
                    queue.offer(curr.right);
                }

                // parent
                TreeNode par = parent.get(curr);
                if (par != null && !visited.contains(par)) {
                    visited.add(par);
                    queue.offer(par);
                }
            }

            distance++;
        }

        // Step 3: remaining nodes in queue are at distance k
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll().val);
        }

        return result;
    }
}
