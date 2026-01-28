/*
Problem: Cheapest Flights Within K Stops
(LeetCode 787)

Problem Explanation:
You are given:
- n cities numbered from 0 to n-1
- flights[i] = [from, to, price]
- src = source city
- dst = destination city
- k = maximum number of stops allowed

You need to find the minimum cost to travel from src to dst
using at most k stops. If it is not possible, return -1.

Important Clarification:
- k stops means at most (k + 1) edges
- You cannot use normal Dijkstra directly because the cheapest
  path may violate the stop constraint

--------------------------------------------------
Key Intuition:
--------------------------------------------------
- This is a shortest path problem with a constraint on levels (stops)
- BFS-like traversal works because we process level by level (each level = one stop)
- We relax edges only up to k + 1 layers
- We maintain the minimum cost to reach each node so far

--------------------------------------------------
Approach (BFS with Cost Relaxation):
--------------------------------------------------
1. Build adjacency list
2. Use a queue storing (node, cost)
3. Process the graph level by level (each level = one stop)
4. For each level:
   - Relax outgoing edges
   - Update cost if a cheaper path is found
5. Stop after k stops

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(E * K)

- Each edge can be relaxed at most K times

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(V + E)

--------------------------------------------------
Example Test Cases:
--------------------------------------------------

Example 1:
Input:
n = 4
flights = [[0,1,100],[1,2,100],[2,3,100],[0,3,500]]
src = 0, dst = 3, k = 1

Output:
500

Explanation:
Only one stop allowed.
Path: 0 -> 3 (cost 500)

--------------------------------------------------

Example 2:
Input:
n = 4
flights = [[0,1,100],[1,2,100],[2,3,100],[0,3,500]]
src = 0, dst = 3, k = 2

Output:
300

Explanation:
Path: 0 -> 1 -> 2 -> 3 (2 stops, cost 300)

--------------------------------------------------

Example 3:
Input:
n = 3
flights = [[0,1,100],[1,2,100]]
src = 0, dst = 2, k = 0

Output:
-1

--------------------------------------------------
*/

import java.util.*;

class Solution {

    public int findCheapestPrice(int n, int[][] flights,
                                 int src, int dst, int k) {

        // Build adjacency list
        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] edge : flights) {
            adj.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        // Distance array (minimum cost to reach each node)
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Queue for BFS: {node, cost}
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{src, 0});

        int steps = 0;

        // BFS up to k stops
        while (!queue.isEmpty() && steps <= k) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {

                int[] curr = queue.poll();
                int node = curr[0];
                int currCost = curr[1];

                for (int[] neigh : adj.get(node)) {

                    int next = neigh[0];
                    int price = neigh[1];
                    int newCost = currCost + price;

                    if (newCost < dist[next]) {
                        dist[next] = newCost;
                        queue.offer(new int[]{next, newCost});
                    }
                }
            }

            steps++;
        }

        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }
}
