/*
 * 🔹 Problem: Dijkstra’s Algorithm (Single Source Shortest Path)
 *
 * Given a weighted, connected graph with V vertices and adjacency list adj,
 * find the shortest path from a source vertex S to all other vertices.
 *
 * The adjacency list is structured as:
 * adj[u] = list of {v, weight} pairs, meaning an edge from u → v with given weight.
 *
 * -----------------------------------------------------
 * 🔹 Intuition:
 * - Dijkstra’s Algorithm finds the shortest distance from a source node to all other nodes
 *   in a weighted graph (with non-negative weights).
 * - It works using a **Greedy + BFS-like approach** with a priority queue (min-heap).
 * - We always pick the node with the smallest known distance,
 *   and relax all its outgoing edges.
 *
 * -----------------------------------------------------
 * 🔹 Steps:
 * 1. Initialize distance array with "infinity" (1e9 here).
 * 2. Set dist[source] = 0 and push it into a min-heap.
 * 3. While the priority queue is not empty:
 *      - Extract the node with the smallest distance.
 *      - For each adjacent node, if going via the current node gives a shorter path,
 *        update distance and push into the queue.
 * 4. Return the dist[] array.
 *
 * -----------------------------------------------------
 * 🔹 Time Complexity:
 * - Using PriorityQueue (Min-Heap): O((V + E) * log V)
 *   because each node is inserted into the heap and each edge relaxation costs log V.
 *
 * 🔹 Space Complexity:
 * - O(V) for distance array + O(V) for priority queue + O(V + E) for adjacency list.
 * - Overall: O(V + E)
 *
 * -----------------------------------------------------
 * 🔹 Example:
 * Input:
 * V = 5, E = 6, source = 0
 * Edges: 
 * 0 → 1 (2), 0 → 2 (4), 1 → 2 (1), 1 → 3 (7),
 * 2 → 4 (3), 3 → 4 (1)
 *
 * Output: [0, 2, 3, 9, 6]
 * (distances from node 0 to every other node)
 */

import java.util.*;

class Solution {

    // Pair class for storing (distance, node)
    static class Pair {
        int distance, node;
        Pair(int distance, int node) {
            this.distance = distance;
            this.node = node;
        }
    }

    // Function to find shortest path from source S to all vertices
    static int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S) {

        // Min-heap based on distance
        PriorityQueue<Pair> pq = new PriorityQueue<>((x, y) -> x.distance - y.distance);

        // Distance array
        int[] dist = new int[V];
        Arrays.fill(dist, (int) 1e9);

        // Source distance = 0
        dist[S] = 0;
        pq.add(new Pair(0, S));

        // Process nodes
        while (!pq.isEmpty()) {
            int dis = pq.peek().distance;
            int node = pq.peek().node;
            pq.remove();

            // Relaxation of edges
            for (int i = 0; i < adj.get(node).size(); i++) {
                int adjNode = adj.get(node).get(i).get(0);
                int edgeWeight = adj.get(node).get(i).get(1);

                if (dis + edgeWeight < dist[adjNode]) {
                    dist[adjNode] = dis + edgeWeight;
                    pq.add(new Pair(dist[adjNode], adjNode));
                }
            }
        }

        return dist;
    }
}
