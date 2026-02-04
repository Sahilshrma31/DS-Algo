/*
 * 🔹 Problem: Accounts Merge (LeetCode 721)
 *
 * You are given a list of accounts. Each account has:
 *   - A name (string)
 *   - One or more email addresses
 *
 * If two accounts share at least one email, they belong to the same person
 * and should be merged into a single account with:
 *   - The person’s name
 *   - All unique emails (sorted)
 *
 * -----------------------------------------------------
 * Example:
 * Input:
 * [
 *   ["John","johnsmith@mail.com","john_newyork@mail.com"],
 *   ["John","johnsmith@mail.com","john00@mail.com"],
 *   ["Mary","mary@mail.com"],
 *   ["John","johnnybravo@mail.com"]
 * ]
 *
 * Output:
 * [
 *   ["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],
 *   ["Mary","mary@mail.com"],
 *   ["John","johnnybravo@mail.com"]
 * ]
 *
 * -----------------------------------------------------
 * 🧠 Intuition:
 * - Think of each email as a "node" in a graph.
 * - If two emails appear in the same account, connect them.
 * - Then, all connected emails form one merged account.
 * - Use Disjoint Set Union (DSU / Union-Find) to merge accounts efficiently.
 *
 * -----------------------------------------------------
 * ⚡ Approach:
 * 1. Map each email to the account index it belongs to.
 * 2. If an email is already mapped, union the current account with the earlier one.
 * 3. After union operations, group all emails by their "parent" account.
 * 4. Sort each group’s emails, prepend the account name, and build the final answer.
 *
 * -----------------------------------------------------
 * ⏱️ Time Complexity:
 * - Let N = total number of accounts, M = total number of emails.
 * - Union-Find operations ≈ O(M * α(N))   [α(N) is inverse Ackermann, very small]
 * - Sorting emails per group ≈ O(M log M)
 * - Overall: O(M log M)
 *
 * 💾 Space Complexity:
 * - Storing mappings & groups ≈ O(M + N)
 *
 */
import java.util.*;
class Solution {

    // DSU (Disjoint Set Union) / Union-Find class
    static class dsu {
        int[] parent, size;

        // Constructor: initialize DSU with n nodes
        dsu(int n) {
            parent = new int[n];
            size = new int[n];

            // Initially, every node is its own parent
            // and size of each component is 1
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        // Find function with path compression
        int find(int node) {
            // If node is the root, return it
            if (parent[node] == node)
                return node;

            // Otherwise, recursively find root
            // and compress the path
            return parent[node] = find(parent[node]);
        }

        // Union two nodes u and v
        void union(int u, int v) {
            int pu = find(u); // parent of u
            int pv = find(v); // parent of v

            // If already in same set, do nothing
            if (pu == pv) return;

            // Union by size (attach smaller tree to larger)
            if (size[pu] < size[pv]) {
                parent[pu] = pv;
                size[pv] += size[pu];
            } else {
                parent[pv] = pu;
                size[pu] += size[pv];
            }
        }
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        // Map to store: email -> account index
        HashMap<String, Integer> map = new HashMap<>();

        int n = accounts.size();

        // Create DSU for n accounts
        dsu ds = new dsu(n);

        // STEP 1: Map each email to an account
        // If an email is already seen, union the two accounts
        for (int i = 0; i < n; i++) {
            // Start from index 1 because index 0 is the name
            for (int j = 1; j < accounts.get(i).size(); j++) {
                String email = accounts.get(i).get(j);

                // If email is seen for the first time
                if (!map.containsKey(email)) {
                    map.put(email, i);
                } 
                // If email already exists, merge the accounts
                else {
                    ds.union(i, map.get(email));
                }
            }
        }

        
        // STEP 2: Group emails by their ultimate parent account
        Map<Integer, List<String>> merged = new HashMap<>();

        for (String email : map.keySet()) {
            int node = map.get(email);        // account index of email
            int parent = ds.find(node);       // ultimate parent account

            // Add email to its parent's list
            if (merged.containsKey(parent)) {
                merged.get(parent).add(email);
            } else {
                List<String> list = new ArrayList<>();
                list.add(email);
                merged.put(parent, list);
            }
        }

        // STEP 3: Build the final answer
        List<List<String>> ans = new ArrayList<>();

        for (int parent : merged.keySet()) {

            // Account name is taken from original accounts list
            String name = accounts.get(parent).get(0);

            // Get and sort all emails
            List<String> emails = merged.get(parent);
            Collections.sort(emails);

            // Prepare final merged account
            List<String> temp = new ArrayList<>();
            temp.add(name);          // name first
            temp.addAll(emails);     // then sorted emails

            ans.add(temp);
        }

        return ans;
    }
}
