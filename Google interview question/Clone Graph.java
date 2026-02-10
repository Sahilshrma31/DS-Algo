import java.util.*;

/*
Problem: Clone Graph
(LeetCode 133)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given a reference of a node in a connected undirected graph.
Return a deep copy (clone) of the graph.

Each node contains:
- an integer value
- a list of neighbors

--------------------------------------------------
Intuition:
--------------------------------------------------
The graph may contain cycles.

So if we try to clone neighbors blindly, we may end up:
- cloning the same node multiple times
- or stuck in infinite recursion

To avoid this:
- Maintain a HashMap<Node, Node> to map original nodes to their clones
- Whenever we encounter a node:
  - If already cloned, reuse it
  - Otherwise, create a clone and DFS its neighbors

--------------------------------------------------
Approach:
--------------------------------------------------
1) If input node is null, return null
2) Create a clone of the starting node
3) Store mapping: original -> clone
4) Run DFS:
   - For every neighbor:
     - If not cloned yet, clone it and DFS
     - Otherwise, connect the existing clone
5) Return the clone of the starting node

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(V + E)
V = number of nodes
E = number of edges

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(V)
HashMap + recursion stack

--------------------------------------------------
Example:
--------------------------------------------------
Graph:
1 -- 2
|    |
4 -- 3

Clone should reproduce the same structure

--------------------------------------------------
Test Cases:
--------------------------------------------------
1)
Input: node = null
Output: null

2)
Input: 1
Neighbors: [2, 4]
Output: Deep copy of the graph starting at 1

--------------------------------------------------
*/

/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {

    HashMap<Node, Node> map = new HashMap<>();

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        Node clone_node = new Node(node.val);
        map.put(node, clone_node);

        dfs(node, clone_node);

        return clone_node;
    }

    public void dfs(Node node, Node clone_node) {
        for (Node neigh : node.neighbors) {

            if (!map.containsKey(neigh)) {   // neighbor not cloned yet
                Node clone = new Node(neigh.val);
                map.put(neigh, clone);
                clone_node.neighbors.add(clone);

                dfs(neigh, clone);

            } else {   // neighbor already cloned
                clone_node.neighbors.add(map.get(neigh));
            }
        }
    }
}
