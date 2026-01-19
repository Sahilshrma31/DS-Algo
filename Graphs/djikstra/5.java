/*
Problem: Path With Minimum Effort (LeetCode #1631)

Problem Summary:
Given an m x n grid 'heights', each cell has an elevation. You need to find a path from top-left (0,0) to bottom-right (m-1,n-1) that minimizes the maximum absolute difference in heights between consecutive cells along the path. You can move up, down, left, or right.

Input:
- heights: int[][] m x n matrix representing elevations.

Output:
- int: minimum effort required to travel from start to end.

Example:
Input: [[1,2,2],[3,8,2],[5,3,5]]
Output: 2
Explanation: Path [1,2,2,2,5] has maximum height difference 2.

Intuition:
- This is a grid shortest path variant where the 'cost' between two nodes is the absolute height difference.
- Instead of minimizing sum of costs, we minimize the maximum cost along the path.
- Can be solved using BFS with binary search or Dijkstra's algorithm.

Approach (Dijkstra's style using Min-Heap):
1. Maintain a distance matrix 'dist' where dist[i][j] is the minimum effort to reach cell (i,j).
2. Use a priority queue to always expand the cell with minimum effort seen so far.
3. For each neighboring cell, calculate effort = max(current effort, abs(height difference))
4. If effort < dist[nr][nc], update and push to priority queue.
5. Return dist[m-1][n-1] when reached.

Time Complexity:
- O(M*N*log(M*N)) due to min-heap operations.

Space Complexity:
- O(M*N) for distance matrix and priority queue.
*/

import java.util.*;

class Solution {
    class Cell {
        int row;
        int col;
        int effort;

        Cell(int r, int c, int e) {
            this.row = r;
            this.col = c;
            this.effort = e;
        }
    }

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        int[][] dist = new int[m][n];
        for (int[] row : dist) Arrays.fill(row, (int)1e9);

        PriorityQueue<Cell> pq = new PriorityQueue<>((a,b)->a.effort - b.effort);
        pq.add(new Cell(0,0,0));
        dist[0][0] = 0;

        int[] dr = {-1,0,1,0};
        int[] dc = {0,1,0,-1};

        while(!pq.isEmpty()){
            Cell cell = pq.poll();
            int r = cell.row;
            int c = cell.col;
            int e = cell.effort;

            if(r == m-1 && c == n-1) return e;

            for(int i=0;i<4;i++){
                int nr = r + dr[i];
                int nc = c + dc[i];

                if(nr>=0 && nc>=0 && nr<m && nc<n){
                    int newEffort = Math.max(e, Math.abs(heights[nr][nc]-heights[r][c]));
                    if(newEffort < dist[nr][nc]){
                        dist[nr][nc] = newEffort;
                        pq.add(new Cell(nr,nc,newEffort));
                    }
                }
            }
        }
        return 0; // This line is unreachable
    }

    public static void main(String[] args){
        Solution sol = new Solution();
        int[][] heights1 = {{1,2,2},{3,8,2},{5,3,5}};
        System.out.println(sol.minimumEffortPath(heights1)); // Output: 2

        int[][] heights2 = {{1,2,3},{3,8,4},{5,3,5}};
        System.out.println(sol.minimumEffortPath(heights2)); // Output: 1

        int[][] heights3 = {{1,2,1,1,1},{1,2,1,2,1},{1,2,1,2,1},{1,2,1,2,1},{1,1,1,2,1}};
        System.out.println(sol.minimumEffortPath(heights3)); // Output: 0
    }
}
