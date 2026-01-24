/*
====================================================
📌 Problem: Count Unreachable Pairs of Nodes in an Undirected Graph
(LeetCode 2316)
====================================================

You are given:
- `n`     → number of nodes labeled from 0 to n-1
- `edges` → undirected edges

Task:
Count the number of **unordered pairs (u, v)** such that
there is **no path** between u and v.

====================================================
🧠 Key Insight
====================================================

- The graph may have multiple **connected components**
- Any node in one component is unreachable from
  any node in another component

If component sizes are:
c1, c2, c3, ...

Answer =
c1*(n-c1) + c2*(n-c1-c2) + c3*(n-c1-c2-c3) + ...

This avoids double counting.

====================================================
🧠 Approach: DFS + Component Sizes
====================================================

1️⃣ Build adjacency list  
2️⃣ Use DFS to find size of each connected component  
3️⃣ Maintain:
   - `remaining` = nodes not yet processed
   - For each component of size `count`:
       pairs += count * remaining
       remaining -= count

====================================================
⏱ Time Complexity
====================================================
O(n + e)

n = number of nodes  
e = number of edges  

====================================================
📦 Space Complexity
====================================================
O(n + e)

- Adjacency list
- Visited array
- Recursion stack

====================================================
*/

import java.util.*;

class Solution {

    private int count;   // size of current component

    public long countPairs(int n, int[][] edges) {

        // Build adjacency list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n];
        long remaining = n;   // nodes not yet counted
        long ans = 0;

        // Traverse each connected component
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count = 0;
                dfs(i, adj, visited);

                remaining -= count;
                ans += (long) count * remaining;
            }
        }

        return ans;
    }

    // DFS to compute size of a connected component
    private void dfs(int node,
                     ArrayList<ArrayList<Integer>> adj,
                     boolean[] visited) {

        visited[node] = true;
        count++;

        for (int neigh : adj.get(node)) {
            if (!visited[neigh]) {
                dfs(neigh, adj, visited);
            }
        }
    }
}
