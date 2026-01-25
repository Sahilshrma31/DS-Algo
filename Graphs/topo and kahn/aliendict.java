/*
Alien Dictionary (GFG)

-------------------------------------------------
Problem Statement:
-------------------------------------------------
Given a sorted dictionary of an alien language having N words and k starting alphabets of standard dictionary.
Find the order of characters in the alien language.

-------------------------------------------------
Example:
-------------------------------------------------
Input: 
N = 5, K = 4
dict = {"baa","abcd","abca","cab","cad"}

Output: 
bdac

Explanation:
Given words are sorted in the alien language.  
From "baa" and "abcd" → 'b' comes before 'a'.  
From "abcd" and "abca" → 'd' comes before 'a'.  
From "abca" and "cab" → 'a' comes before 'c'.  
From "cab" and "cad" → 'b' comes before 'd'.  
So order is: b d a c

-------------------------------------------------
Intuition:
-------------------------------------------------
1. The given dictionary is sorted in alien language order.  
2. Compare each pair of adjacent words:
   - Find the first differing character. That gives order (u → v).  
3. Build a directed graph of size k.  
4. Perform Topological Sort (Kahn’s Algorithm or DFS) to get the character order.  

-------------------------------------------------
Time Complexity:
-------------------------------------------------
- Building graph: O(N * L) where L is average word length.  
- Topological sort: O(K + E).  
Total: O(N * L + K + E)

Space Complexity:
-------------------------------------------------
- Graph: O(K + E)  
- Indegree array + result: O(K)  
*/

import java.util.*;

class Solution{

    // Function to find the order of characters in alien dictionary 
    public static String findOrder(String[] words) {
        
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[26];
        boolean[] exists = new boolean[26];

        // Initialize graph
        for (int i = 0; i < 26; i++) {
            graph.add(new ArrayList<>());
        }

        // Mark characters that exist in the input
        for (String word : words) {
            for (char ch : word.toCharArray()) {
                exists[ch - 'a'] = true;
            }
        }

        // Build the graph based on adjacent word comparisons
        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i];
            String w2 = words[i + 1];
            int len = Math.min(w1.length(), w2.length());
            int j = 0;

            while (j < len && w1.charAt(j) == w2.charAt(j)) {
                j++;
            }

            if (j < len) {
                int u = w1.charAt(j) - 'a';
                int v = w2.charAt(j) - 'a';
                graph.get(u).add(v);
                inDegree[v]++;
            } else if (w1.length() > w2.length()) {
                
                // Invalid input
                return "";
            }
        }

        // Topological Sort
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < 26; i++) {
            if (exists[i] && inDegree[i] == 0) {
                q.offer(i);
            }
        }

        StringBuilder result = new StringBuilder();

        while (!q.isEmpty()) {
            int u = q.poll();
            result.append((char) (u + 'a'));

            for (int v : graph.get(u)) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    q.offer(v);
                }
            }
        }

        // Check for cycle
        for (int i = 0; i < 26; i++) {
            if (exists[i] && inDegree[i] != 0) {
                return "";
            }
        }

        return result.toString();
    }

  
}
