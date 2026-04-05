import java.util.*;

import javax.swing.tree.TreeNode;

/*
Problem: Number of Good Leaf Node Pairs (LeetCode 1530)

--------------------------------------------------
Problem Description
--------------------------------------------------
Given the root of a binary tree and an integer distance,
return the number of pairs of leaf nodes such that the
shortest path between them is less than or equal to distance.

A leaf node is a node with no children.

--------------------------------------------------
Key Observations
--------------------------------------------------
1. We only care about leaf nodes.
2. Distance between nodes = number of edges.
3. Tree can be converted into an undirected graph.
4. BFS can be used to find nodes within a given distance.

--------------------------------------------------
Intuition
--------------------------------------------------
Instead of solving directly on the tree, we convert the
tree into a graph so that we can move both upwards and
downwards.

Then for every leaf node:
- Run BFS up to the given distance
- Count how many other leaf nodes are reachable

Each pair will be counted twice, so divide by 2.

--------------------------------------------------
Approach
--------------------------------------------------
1. Convert tree → graph using adjacency list.
2. Store all leaf nodes in a set.
3. For each leaf node:
   - Run BFS up to 'distance'
   - Count reachable leaf nodes
4. Return count / 2.

--------------------------------------------------
Time Complexity
--------------------------------------------------
O(L * N)

L = number of leaf nodes  
N = total nodes  

--------------------------------------------------
Space Complexity
--------------------------------------------------
O(N)

--------------------------------------------------
Example
--------------------------------------------------
Input:
Tree = [1,2,3,null,4]
distance = 3

Output:
1
*/

class Solution {

    /*
    --------------------------------------------------
    Step 1: Build Graph + Identify Leaf Nodes
    --------------------------------------------------
    */

    public void makeGraph(TreeNode root, TreeNode prev,
                          Map<TreeNode, List<TreeNode>> adj,
                          Set<TreeNode> leafNodes) {

        if (root == null) return;

        // Identify leaf node
        if (root.left == null && root.right == null) {
            leafNodes.add(root);
        }

        if (prev != null) {

            if (!adj.containsKey(root)) {
                adj.put(root, new ArrayList<>());
            }
            adj.get(root).add(prev);

            if (!adj.containsKey(prev)) {
                adj.put(prev, new ArrayList<>());
            }
            adj.get(prev).add(root);
        }

        makeGraph(root.left, root, adj, leafNodes);
        makeGraph(root.right, root, adj, leafNodes);
    }

    /*
    --------------------------------------------------
    Step 2: BFS from each leaf node
    --------------------------------------------------
    */

    public int countPairs(TreeNode root, int distance) {

        Map<TreeNode, List<TreeNode>> adj = new HashMap<>();
        Set<TreeNode> leafNodes = new HashSet<>();

        makeGraph(root, null, adj, leafNodes);

        int count = 0;

        for (TreeNode leaf : leafNodes) {

            Queue<TreeNode> queue = new LinkedList<>();
            Set<TreeNode> visited = new HashSet<>();

            queue.add(leaf);
            visited.add(leaf);

            for (int level = 0; level <= distance; level++) {

                int size = queue.size();

                while (size-- > 0) {

                    TreeNode curr = queue.poll();

                    if (curr != leaf && leafNodes.contains(curr)) {
                        count++;
                    }

                    List<TreeNode> neighbors = adj.get(curr);

                    if (neighbors != null) {
                        for (TreeNode next : neighbors) {

                            if (!visited.contains(next)) {
                                queue.add(next);
                                visited.add(next);
                            }
                        }
                    }
                }
            }
        }

        return count / 2;
    }
}