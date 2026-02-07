import java.util.*;

/*
Problem: Reconstruct Itinerary
(LeetCode 332)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given a list of airline tickets where tickets[i] = [from, to].
All tickets belong to a man who departs from "JFK".

Your task is to reconstruct the itinerary in order and return it.
If there are multiple valid itineraries, return the one with the
smallest lexical order.

All tickets must be used exactly once.

--------------------------------------------------
Core Observation:
--------------------------------------------------
This is a graph problem where:
- Each airport is a node
- Each ticket is a directed edge
- We need to use every edge exactly once
- The path must start from "JFK"
- If multiple paths exist, choose lexicographically smallest

This is exactly the definition of:
- Eulerian Path in a directed graph

--------------------------------------------------
Key Intuition (Hierholzer’s Algorithm):
--------------------------------------------------
To find a path that uses every edge exactly once:
- We perform DFS
- Always take the lexicographically smallest next flight
- When we reach a dead-end (no outgoing edges), we add the node to the answer
- Final answer is built during backtracking

This reverse building ensures:
- Every ticket is used once
- Lexicographically smallest itinerary is constructed

--------------------------------------------------
Why PriorityQueue is Used:
--------------------------------------------------
From any airport, multiple destinations may exist.
To always choose the smallest lexicographical destination:
- Store neighbors in a min-heap (PriorityQueue)

So DFS always picks the smallest available destination first.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Build adjacency list:
   - Map<String, PriorityQueue<String>>
   - For each "from", store all "to" in min-heap

2. Start DFS from "JFK"

3. In DFS:
   - While there are destinations available from current airport:
       - Take the smallest destination
       - DFS into that destination
   - After exploring all outgoing edges, add current airport to front of result

4. The result is built in reverse order (postorder)

--------------------------------------------------
Why We Add to Front (Backtracking Logic):
--------------------------------------------------
When a node has no outgoing edges left:
- It means this node is the last stop of that path segment
- So we add it to the front of the itinerary

This backtracking order reconstructs the correct path.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
Let n be number of tickets.

Building graph: O(n log n)
Each ticket is pushed and popped once from PriorityQueue.

DFS traversal: O(n log n)

Total:
O(n log n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)
Adjacency list + recursion stack + result list

--------------------------------------------------
Example:
--------------------------------------------------
Tickets:
[
  ["MUC", "LHR"],
  ["JFK", "MUC"],
  ["SFO", "SJC"],
  ["LHR", "SFO"]
]

Output:
["JFK", "MUC", "LHR", "SFO", "SJC"]

--------------------------------------------------
More Test Cases:
--------------------------------------------------

1)
Input:
[
  ["JFK","SFO"],
  ["JFK","ATL"],
  ["SFO","ATL"],
  ["ATL","JFK"],
  ["ATL","SFO"]
]

Output:
["JFK","ATL","JFK","SFO","ATL","SFO"]

2)
Input:
[
  ["JFK","KUL"],
  ["JFK","NRT"],
  ["NRT","JFK"]
]

Output:
["JFK","NRT","JFK","KUL"]

--------------------------------------------------
*/

/**
 * Reconstruct Itinerary using DFS + Hierholzer’s Algorithm
 */
class Solution {

    LinkedList<String> res = new LinkedList<>();

    public List<String> findItinerary(List<List<String>> tickets) {

        // Adjacency list with lexicographically smallest destination first
        HashMap<String, PriorityQueue<String>> map = new HashMap<>();

        for (int i = 0; i < tickets.size(); i++) {
            String u = tickets.get(i).get(0);
            String v = tickets.get(i).get(1);

            if (!map.containsKey(u)) {
                PriorityQueue<String> temp = new PriorityQueue<>();
                map.put(u, temp);
            }
            map.get(u).add(v);
        }

        dfs("JFK", map);
        return res;
    }

    public void dfs(String dest, HashMap<String, PriorityQueue<String>> map) {
        PriorityQueue<String> arrivals = map.get(dest);

        while (arrivals != null && !arrivals.isEmpty()) {
            dfs(arrivals.poll(), map);
        }

        // Backtracking step
        res.addFirst(dest);
    }
}
