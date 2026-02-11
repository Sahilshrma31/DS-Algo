import java.util.*;

/*
Problem: Shortest Path Visiting All Nodes
(LeetCode 847)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given an undirected connected graph.
You can start at any node.
Return the length of the shortest path that visits every node at least once.
You may revisit nodes and reuse edges.

--------------------------------------------------
Intuition:
--------------------------------------------------
We are asked to:
- visit all nodes
- with minimum number of edges walked
- starting from any node

This is similar to "traveling to cover all nodes with minimum steps".
This is NOT shortest path between two nodes.
This is shortest path over states.

Key observation:
The important state is not only the current node,
but also which nodes have already been visited.

So each state can be represented as:
(currentNode, visitedMask)

Where:
visitedMask is a bitmask of length n
If bit i is set, node i has been visited.

Example:
For n = 4 nodes
mask = 0101 means nodes 0 and 2 are visited.

--------------------------------------------------
Why BFS:
--------------------------------------------------
We want the minimum number of steps.
Each move to a neighbor costs 1 step.
So BFS is the correct choice.

--------------------------------------------------
Why Multi-source BFS:
--------------------------------------------------
We can start from ANY node.
So we push all nodes as starting points into the queue:
(node = i, mask = 1 << i)

This avoids running BFS separately from each node.

--------------------------------------------------
When do we stop:
--------------------------------------------------
When mask == allVisitedMask
That means all nodes have been visited at least once.

--------------------------------------------------
Approach:
--------------------------------------------------
1) Let n = number of nodes
2) Create a queue of states (node, mask)
3) visited[node][mask] = true means:
   we already visited this state, so do not repeat it
4) Push all nodes as starting states:
   (i, 1 << i)
5) BFS level by level
6) For each neighbor:
   newMask = mask | (1 << neighbor)
7) If newMask == allVisitedMask, return current steps
8) If BFS finishes without finding full mask, return -1

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n * 2^n)

There are:
- n possible nodes
- 2^n possible masks

Each state is processed once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n * 2^n)
Queue + visited array

--------------------------------------------------
Example:
--------------------------------------------------
Input:
graph = [[1,2,3],[0],[0],[0]]

Output: 4

Explanation:
One shortest path:
1 -> 0 -> 2 -> 0 -> 3

--------------------------------------------------
Test Cases:
--------------------------------------------------
1)
Input: [[1,2,3],[0],[0],[0]]
Output: 4

2)
Input: [[1],[0,2,4],[1,3,4],[2],[1,2]]
Output: 4

--------------------------------------------------
*/

class Solution {

    class Pair {
        int node;
        int mask;

        Pair(int node, int mask) {
            this.node = node;
            this.mask = mask;
        }
    }

    public int shortestPathLength(int[][] graph) {
        int n = graph.length;

        if (n == 0 || n == 1) {
            return 0;
        }

        Queue<Pair> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][1 << n];

        // Multi-source BFS initialization
        for (int i = 0; i < n; i++) {
            int mask = 1 << i;
            queue.offer(new Pair(i, mask));
            visited[i][mask] = true;
        }

        int allVisited = (1 << n) - 1;
        int path = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                Pair curr = queue.poll();
                int node = curr.node;
                int mask = curr.mask;

                if (mask == allVisited) {
                    return path;
                }

                for (int neigh : graph[node]) {
                    int neighMask = mask | (1 << neigh);

                    if (!visited[neigh][neighMask]) {
                        visited[neigh][neighMask] = true;
                        queue.add(new Pair(neigh, neighMask));
                    }
                }
            }

            path++;
        }

        return -1;
    }
}
