/*
====================================================
📌 Problem: Most Stones Removed with Same Row or Column
(LeetCode 947)
====================================================

You are given `n` stones placed on a 2D plane.
Each stone is at position (x, y).

A stone can be removed if:
- There exists another stone in the SAME row OR
- There exists another stone in the SAME column

Return the **maximum number of stones** that can be removed.

====================================================
🧠 Key Insight
====================================================

- Stones that are connected (same row or column) form a
  **connected component**
- From each connected component, we can remove
  all stones except ONE

So:
Answer = total stones - number of connected components

====================================================
🧠 Approach: Disjoint Set Union (Union-Find)
====================================================

1️⃣ Treat each stone as a node  
2️⃣ If two stones share the same row or column → union them  
3️⃣ Count number of unique parents (connected components)  
4️⃣ Result = n - components  

====================================================
⏱ Time Complexity
====================================================
O(n² · α(n))

- Pairwise checking of stones → O(n²)
- Union-Find operations are almost constant (α(n))

====================================================
📦 Space Complexity
====================================================
O(n)

- Parent and size arrays

====================================================
*/

class Solution {

    /*
    ====================================================
    🔹 Disjoint Set Union (Union-Find)
    ====================================================
    */
    static class DSU {

        int[] parent;
        int[] size;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            return parent[x] = find(parent[x]); // path compression
        }

        void union(int u, int v) {

            int pu = find(u);
            int pv = find(v);

            if (pu == pv) return;

            // union by size
            if (size[pu] > size[pv]) {
                parent[pv] = pu;
                size[pu] += size[pv];
            } else {
                parent[pu] = pv;
                size[pv] += size[pu];
            }
        }
    }

    /*
    ====================================================
    🚀 MAIN FUNCTION
    ====================================================
    */
    public int removeStones(int[][] stones) {

        int n = stones.length;
        DSU ds = new DSU(n);

        // Union stones sharing same row or column
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (stones[i][0] == stones[j][0] ||
                    stones[i][1] == stones[j][1]) {
                    ds.union(i, j);
                }
            }
        }

        // Count connected components
        int components = 0;
        for (int i = 0; i < n; i++) {
            if (ds.find(i) == i) {
                components++;
            }
        }

        // Maximum stones removable
        return n - components;
    }
}
