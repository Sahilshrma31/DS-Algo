import java.util.*;

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
2. We need distance between every pair of leaf nodes.
3. Distance = number of edges between two nodes.
4. Tree can be converted into an undirected graph.
5. Then we can run BFS from each leaf node.

--------------------------------------------------
Intuition
--------------------------------------------------
Instead of directly computing distances in a tree,
we convert the tree into a graph.

Then:
- Start BFS from each leaf node
- Explore all nodes up to given distance
- Count how many other leaf nodes are reachable

Since each pair is counted twice → divide by 2.

--------------------------------------------------
Approach
--------------------------------------------------
1. Convert tree → graph (adjacency list)
2. Collect all leaf nodes
3. For each leaf:
   - Run BFS up to 'distance'
   - Count reachable leaf nodes
4. Divide total count by 2

--------------------------------------------------
State Definition
--------------------------------------------------
No DP used here.

We use:
- Graph traversal (BFS)
- Visited set for each BFS

--------------------------------------------------
Time Complexity
--------------------------------------------------
O(L * N)

L = number of leaf nodes  
N = total nodes  

Worst case: BFS from each leaf

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

Explanation:
Only one valid pair of leaf nodes within distance.
*/

class Solution {

    /*
    --------------------------------------------------
    Step 1: Convert Tree to Graph + Find Leaf Nodes
    --------------------------------------------------
    */

    public void makeGraph(TreeNode root, TreeNode parent,
                          Map<TreeNode, List<TreeNode>> adj,
                          Set<TreeNode> leafNodes) {

        if (root == null) return;

        // Identify leaf node
        if (root.left == null && root.right == null) {
            leafNodes.add(root);
        }

        // Create bidirectional edge
        if (parent != null) {

            adj.putIfAbsent(root, new ArrayList<>());
            adj.putIfAbsent(parent, new ArrayList<>());

            adj.get(root).add(parent);
            adj.get(parent).add(root);
        }

        makeGraph(root.left, root, adj, leafNodes);
        makeGraph(root.right, root, adj, leafNodes);
    }

    /*
    --------------------------------------------------
    Main Function (BFS from each leaf)
    --------------------------------------------------
    */

    public int countPairs(TreeNode root, int distance) {

        Map<TreeNode, List<TreeNode>> adj = new HashMap<>();
        Set<TreeNode> leafNodes = new HashSet<>();

        // Build graph
        makeGraph(root, null, adj, leafNodes);

        int count = 0;

        // BFS from each leaf
        for (TreeNode leaf : leafNodes) {

            Queue<TreeNode> queue = new LinkedList<>();
            Set<TreeNode> visited = new HashSet<>();

            queue.add(leaf);
            visited.add(leaf);

            for (int level = 0; level <= distance; level++) {

                int size = queue.size();

                while (size-- > 0) {

                    TreeNode curr = queue.poll();

                    // Check if another leaf node found
                    if (curr != leaf && leafNodes.contains(curr)) {
                        count++;
                    }

                    for (TreeNode neighbor : adj.getOrDefault(curr, new ArrayList<>())) {

                        if (!visited.contains(neighbor)) {
                            queue.add(neighbor);
                            visited.add(neighbor);
                        }
                    }
                }
            }
        }

        // Each pair counted twice
        return count / 2;
    }
}