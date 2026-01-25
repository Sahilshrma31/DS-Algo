/*
Minimum Spanning Tree (Prim's Algorithm)
------------------------------------------------------------

📌 Problem Statement:
Given an undirected, connected, weighted graph with V vertices and E edges, 
find the weight of the Minimum Spanning Tree (MST).

MST is a subset of edges that connects all vertices with minimum total edge weight,
without forming any cycles.

------------------------------------------------------------

💡 Intuition:
We use **Prim’s Algorithm**:
- Start from an arbitrary node (say node 0).
- Use a min-heap (priority queue) to always pick the smallest edge leading to an unvisited node.
- Keep adding edges until all nodes are included in the MST.
- Sum of these chosen edges = weight of MST.

------------------------------------------------------------

🛠️ Approach:
1. Build an adjacency list for the graph.
2. Use a min-heap storing `{weight, node}`.
3. Start from node 0, push `{0, 0}` into heap.
4. While heap is not empty:
   - Pop the smallest weight edge.
   - If node already visited → skip.
   - Otherwise, mark visited, add edge weight to MST sum.
   - Push all adjacent edges into heap if neighbor not visited.
5. Return total MST weight.

------------------------------------------------------------

⏱️ Time Complexity:
- Building adjacency list: O(E)
- Each edge pushed/popped from heap at most once: O(E log E)
- Overall: **O(E log V)**

💾 Space Complexity:
- Adjacency list: O(V + E)
- Visited array + heap: O(V + E)
- Overall: **O(V + E)**

------------------------------------------------------------
*/

import java.util.*;

class Solution {
    public int spanningTree(int V, int[][] edges) {
        // Step 1: Build adjacency list
        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], wt = edge[2];
            adj.get(u).add(new int[]{v, wt});
            adj.get(v).add(new int[]{u, wt});
        }

        // Step 2: Min-heap (weight, node)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.add(new int[]{0, 0}); // (weight=0, start node=0)

        // Step 3: MST construction
        boolean[] vis = new boolean[V];
        int sum = 0;

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int wt = curr[0];
            int node = curr[1];

            if (vis[node]) continue;
            vis[node] = true;
            sum += wt;

            for (int[] neigh : adj.get(node)) {
                int adjNode = neigh[0];
                int edgeWt = neigh[1];
                if (!vis[adjNode]) {
                    pq.add(new int[]{edgeWt, adjNode});
                }
            }
        }

        return sum;
    }

    // Example usage
    public static void main(String[] args) {
        int V = 5;
        int[][] edges = {
            {0, 1, 2}, {0, 3, 6}, {1, 2, 3}, 
            {1, 3, 8}, {1, 4, 5}, {2, 4, 7}, {3, 4, 9}
        };

        Solution sol = new Solution();
        int mstWeight = sol.spanningTree(V, edges);
        System.out.println("Weight of MST = " + mstWeight);
    }
}


 //for printing the whole mst edges

    static class Pair {
        int node, wt;
        Pair(int node, int wt) {
            this.node = node;
            this.wt = wt;
        }
    }

    static class Edge {
        int wt, node, parent;
        Edge(int wt, int node, int parent) {
            this.wt = wt;
            this.node = node;
            this.parent = parent;
        }
    }

    public static ArrayList<ArrayList<Integer>> calculatePrimsMST(
            int n, int m, ArrayList<ArrayList<Integer>> g) {

        // adjacency list (1-based indexing)
        ArrayList<ArrayList<Pair>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        // build graph
        for (ArrayList<Integer> edge : g) {
            int u = edge.get(0);
            int v = edge.get(1);
            int wt = edge.get(2);

            adj.get(u).add(new Pair(v, wt));
            adj.get(v).add(new Pair(u, wt));
        }

        boolean[] vis = new boolean[n + 1];

        PriorityQueue<Edge> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.wt, b.wt)
        );

        // result MST edges
        ArrayList<ArrayList<Integer>> mst = new ArrayList<>();

        // start from node 1
        pq.offer(new Edge(0, 1, -1));

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();
            int node = cur.node;
            int wt = cur.wt;
            int parent = cur.parent;

            if (vis[node]) continue;

            vis[node] = true;

            // add edge to MST (ignore starting node)
            if (parent != -1) {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(parent);
                temp.add(node);
                temp.add(wt);
                mst.add(temp);
            }

            for (Pair neigh : adj.get(node)) {
                if (!vis[neigh.node]) {
                    pq.offer(new Edge(neigh.wt, neigh.node, node));
                }
            }
        }

        return mst;
    }
}
