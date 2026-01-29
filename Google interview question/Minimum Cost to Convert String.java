/*
Problem: Minimum Cost to Convert String
(LeetCode 2976)

Problem Explanation:
You are given two strings `source` and `target` of equal length.
You are also given conversion rules:
- original[i] can be converted to changed[i] with cost[i]

You can apply conversions any number of times.
The task is to find the minimum total cost to convert `source`
into `target`.

If it is not possible to convert, return -1.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
- Characters are limited to lowercase English letters (26 total)
- Conversions form a directed weighted graph of size 26
- You may need multiple conversions to go from one character to another
- This is an all-pairs shortest path problem on a small graph

Because the graph size is fixed (26 nodes),
Floyd–Warshall is the best and simplest choice.

--------------------------------------------------
Approach: Floyd–Warshall
--------------------------------------------------
1. Build a 26 x 26 adjacency matrix
   adj[i][j] = minimum cost to convert character i to j
2. Initialize:
   - adj[i][i] = 0
   - all other entries = INF
3. Fill direct conversion costs
4. Run Floyd–Warshall to compute minimum cost between all pairs
5. For each position in source:
   - If source[i] == target[i], cost = 0
   - Otherwise, add adj[source[i]][target[i]]
   - If no path exists, return -1

--------------------------------------------------
Time Complexity:
--------------------------------------------------
Floyd–Warshall: O(26^3) ≈ constant
String traversal: O(n)

Overall: O(n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(26^2) ≈ constant

--------------------------------------------------
Example Test Cases:
--------------------------------------------------

Example 1:
Input:
source = "abcd"
target = "bcde"
original = ['a','b','c','d']
changed  = ['b','c','d','e']
cost     = [1,1,1,1]

Output:
4

--------------------------------------------------

Example 2:
Input:
source = "abc"
target = "ddd"
original = ['a','b','c']
changed  = ['b','c','d']
cost     = [2,2,2]

Output:
6

--------------------------------------------------

Example 3:
Input:
source = "abc"
target = "xyz"
original = ['a']
changed  = ['b']
cost     = [1]

Output:
-1

--------------------------------------------------
*/

import java.util.*;

class Solution {

    static final long INF = (long) 1e18;

    // Runs Floyd–Warshall on the character conversion graph
    private void floydWarshall(char[] original,
                               char[] changed,
                               int[] cost,
                               long[][] adj) {

        // Direct conversions
        for (int i = 0; i < original.length; i++) {
            int from = original[i] - 'a';
            int to = changed[i] - 'a';
            adj[from][to] = Math.min(adj[from][to], cost[i]);
        }

        // All-pairs shortest paths
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    if (adj[i][k] + adj[k][j] < adj[i][j]) {
                        adj[i][j] = adj[i][k] + adj[k][j];
                    }
                }
            }
        }
    }

    public long minimumCost(String source,
                            String target,
                            char[] original,
                            char[] changed,
                            int[] cost) {

        long[][] adj = new long[26][26];

        // Initialize adjacency matrix
        for (int i = 0; i < 26; i++) {
            Arrays.fill(adj[i], INF);
            adj[i][i] = 0;
        }

        // Compute minimum conversion costs
        floydWarshall(original, changed, cost, adj);

        long totalCost = 0;

        // Compute total cost for string conversion
        for (int i = 0; i < source.length(); i++) {

            int from = source.charAt(i) - 'a';
            int to = target.charAt(i) - 'a';

            if (from == to) continue;

            if (adj[from][to] == INF) {
                return -1;
            }

            totalCost += adj[from][to];
        }

        return totalCost;
    }
}
