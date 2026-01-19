/*
====================================================
📌 Problem: Shortest Path in Undirected Weighted Graph
(GFG / Interview Standard)
====================================================

You are given:
- `n` → number of nodes (1 to n)
- `m` → number of edges
- `edges[i] = {u, v, w}` → undirected edge with weight w

Task:
- Find the **shortest path from node 1 to node n**
- If no path exists, return `[-1]`
- Otherwise return a list:
  [ shortestDistance, path nodes from 1 to n ]

====================================================
🧠 Approach: Dijkstra + Parent Tracking
====================================================

- Since all weights are non-negative → use **Dijkstra**
- Maintain:
  - `dist[]`   → shortest distance from node 1
  - `parent[]` → to reconstruct path
- Use a **min-heap (PriorityQueue)** to always expand
  the node with the smallest distance

Path reconstruction:
- Start from node `n`
- Keep moving to `parent[node]`
- Reverse the path at the end

====================================================
⏱ Time Complexity
====================================================
O((V + E) log V)

====================================================
📦 Space Complexity
====================================================
O(V + E)

- Adjacency list
- Distance array
- Parent array
- PriorityQueue

====================================================
*/

import java.util.*;

class Solution {

    public List<Integer> shortestPath(int n, int m, int[][] edges) {

        /*
        ====================================================
        🔹 Step 1: Build Adjacency List (Undirected)
        ====================================================
        */
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int w = edges[i][2];

            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w});
        }

        /*
        ====================================================
        🔹 Step 2: Distance & Parent Initialization
        ====================================================
        */
        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0;

        int[] parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        /*
        ====================================================
        🔹 Step 3: Dijkstra using PriorityQueue
        ====================================================
        */
        PriorityQueue<long[]> pq =
                new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));

        pq.offer(new long[]{0, 1}); // {distance, node}

        while (!pq.isEmpty()) {

            long[] cur = pq.poll();
            long currDist = cur[0];
            int node = (int) cur[1];

            // Lazy deletion
            if (currDist > dist[node]) continue;

            for (int[] edge : adj.get(node)) {

                int nbr = edge[0];
                int wt = edge[1];

                if (dist[node] + wt < dist[nbr]) {
                    dist[nbr] = dist[node] + wt;
                    parent[nbr] = node;
                    pq.offer(new long[]{dist[nbr], nbr});
                }
            }
        }

        /*
        ====================================================
        🔹 Step 4: If no path exists
        ====================================================
        */
        if (dist[n] == Long.MAX_VALUE) {
            return Arrays.asList(-1);
        }

        /*
        ====================================================
        🔹 Step 5: Reconstruct Path
        ====================================================
        */
        List<Integer> path = new ArrayList<>();
        int currNode = n;

        while (parent[currNode] != currNode) {
            path.add(currNode);
            currNode = parent[currNode];
        }
        path.add(currNode);

        Collections.reverse(path);

        /*
        ====================================================
        🔹 Step 6: Prepare Final Answer
        ====================================================
        */
        List<Integer> ans = new ArrayList<>();
        ans.add((int) dist[n]); // shortest distance
        ans.addAll(path);       // path nodes

        return ans;
    }
}
