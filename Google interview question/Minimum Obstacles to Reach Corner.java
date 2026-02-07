import java.util.*;

/*
Problem: Minimum Obstacles to Reach Corner
(LeetCode 2290)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given a 0-indexed 2D grid of size m x n.
Each cell is either:
- 0: empty cell
- 1: obstacle

You can move in 4 directions (up, down, left, right).
If you move into an obstacle cell, you must remove it (cost = 1).

Return the minimum number of obstacles to remove to reach
the bottom-right corner from the top-left corner.

--------------------------------------------------
Intuition:
--------------------------------------------------
This is a shortest path problem on a grid:
- Moving to a free cell has cost 0
- Moving to an obstacle cell has cost 1

We need to minimize total cost from (0,0) to (m-1,n-1).

This is exactly:
- Dijkstra’s Algorithm on a grid with edge weights 0 or 1

--------------------------------------------------
Approach:
--------------------------------------------------
1. Maintain a result[][] matrix storing minimum obstacle removals to reach each cell.
2. Use a min-heap (PriorityQueue) to always expand the cell with smallest cost.
3. For each popped cell:
   - Try moving in 4 directions
   - Cost = currentCost + (grid[x][y] == 1 ? 1 : 0)
   - If newCost is smaller, update result and push to PQ
4. The answer is result[m-1][n-1]

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(m * n * log(m * n))

Each cell can be pushed into the priority queue multiple times.
Worst case: every cell is processed once with log factor.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(m * n)

Result matrix + priority queue

--------------------------------------------------
Example:
--------------------------------------------------
Input:
grid = [
  [0,1,1],
  [1,1,0],
  [1,1,0]
]

Output:
2

Explanation:
One optimal path removes 2 obstacles.

--------------------------------------------------
More Test Cases:
--------------------------------------------------

1)
grid = [
  [0,1,0,0],
  [0,1,0,1],
  [0,0,0,1]
]
Output: 1

2)
grid = [
  [0,0,0],
  [0,0,0],
  [0,0,0]
]
Output: 0

--------------------------------------------------
*/

class Solution {

    public int minimumObstacles(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] result = new int[m][n];
        for (int[] row : result) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        result[0][0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        pq.offer(new int[]{0, 0, 0}); // cost, i, j

        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0];
            int i = cur[1];
            int j = cur[2];

            // skip outdated entries
            if (cost > result[i][j]) continue;

            for (int[] d : directions) {
                int x = i + d[0];
                int y = j + d[1];

                if (x >= 0 && x < m && y >= 0 && y < n) {
                    int wt = (grid[x][y] == 1) ? 1 : 0;
                    int newCost = cost + wt;

                    if (newCost < result[x][y]) {
                        result[x][y] = newCost;
                        pq.offer(new int[]{newCost, x, y});
                    }
                }
            }
        }

        return result[m - 1][n - 1];
    }
}

