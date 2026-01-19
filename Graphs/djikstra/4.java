/*
====================================================
📌 Problem: Shortest Path in Binary Matrix
(LeetCode 1091)
====================================================

You are given an n x n binary matrix `grid` where:
- 0 represents an empty cell
- 1 represents a blocked cell

You can move in **8 directions**:
↖ ↑ ↗
←   →
↙ ↓ ↘

Task:
Return the length of the **shortest clear path**
from top-left (0,0) to bottom-right (n-1,n-1).

If no such path exists, return -1.

====================================================
🧠 Approach: Dijkstra / BFS with PriorityQueue
====================================================

- Each move has a **uniform cost = 1**
- We still use a PriorityQueue (Dijkstra-style),
  though BFS would also work here

Steps:
1️⃣ If start or end is blocked → return -1  
2️⃣ Use a `dist[][]` matrix to store shortest distance to each cell  
3️⃣ Use a min-heap (PriorityQueue) storing (row, col, distance)  
4️⃣ Explore all 8 directions  
5️⃣ Stop as soon as we reach (n-1, n-1)

====================================================
⏱ Time Complexity
====================================================
O(n² log n²) ≈ O(n² log n)

====================================================
📦 Space Complexity
====================================================
O(n²)

- Distance matrix
- PriorityQueue

====================================================
*/

import java.util.*;

class Solution {

    /*
    ====================================================
    🔹 Pair class to store cell coordinates and distance
    ====================================================
    */
    static class Pair {
        int r;
        int c;
        int dist;

        Pair(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }
    }

    public int shortestPathBinaryMatrix(int[][] grid) {

        int n = grid.length;

        // Edge case: start or end blocked
        if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) {
            return -1;
        }

        /*
        ====================================================
        🔹 Distance matrix
        ====================================================
        */
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, (int) 1e9);
        }

        /*
        ====================================================
        🔹 Min-heap (distance, row, col)
        ====================================================
        */
        PriorityQueue<Pair> pq =
                new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));

        pq.offer(new Pair(0, 0, 1));
        dist[0][0] = 1;

        /*
        ====================================================
        🔹 8 Directions
        ====================================================
        */
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        /*
        ====================================================
        🔹 Dijkstra / BFS traversal
        ====================================================
        */
        while (!pq.isEmpty()) {

            Pair curr = pq.poll();
            int r = curr.r;
            int c = curr.c;
            int d = curr.dist;

            // Destination reached
            if (r == n - 1 && c == n - 1) {
                return d;
            }

            // Lazy deletion
            if (d > dist[r][c]) continue;

            for (int i = 0; i < 8; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nc >= 0 && nr < n && nc < n
                        && grid[nr][nc] == 0) {

                    if (d + 1 < dist[nr][nc]) {
                        dist[nr][nc] = d + 1;
                        pq.offer(new Pair(nr, nc, dist[nr][nc]));
                    }
                }
            }
        }

        return -1;
    }
}
