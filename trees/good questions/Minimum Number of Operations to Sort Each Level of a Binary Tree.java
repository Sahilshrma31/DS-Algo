import java.util.*;

/*
Problem: Minimum Number of Operations to Sort Each Level of a Binary Tree
(LeetCode 2471)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given the root of a binary tree, you need to make the values at each level
strictly increasing by performing minimum number of swap operations.

In one operation, you can swap any two values at the same level.

Return the minimum number of operations required to sort the values at every level.

--------------------------------------------------
Intuition:
--------------------------------------------------
Each level of the binary tree is independent.

For each level:
- Extract all node values
- Find the minimum number of swaps required to sort that array

The total answer is the sum of swaps required for all levels.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Perform level order traversal (BFS) of the tree.
2. For each level:
   - Store all values in an array
   - Compute minimum swaps to sort that array
3. Add the swaps of each level to the total.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n log n)
For each level, sorting is performed.
Overall sum of elements across levels = n.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)
Queue for BFS + extra arrays for sorting each level.

--------------------------------------------------
Example:
--------------------------------------------------
Tree:
        1
       / \
      4   3
     / \   \
    7   6   5

Level 0: [1] -> already sorted, swaps = 0  
Level 1: [4, 3] -> sorted [3, 4], swaps = 1  
Level 2: [7, 6, 5] -> sorted [5, 6, 7], swaps = 1  

Total = 2

--------------------------------------------------
*/

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    public int minimumOperations(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int totalSwaps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            int[] level = new int[size];

            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                level[i] = curr.val;

                if (curr.left != null) queue.add(curr.left);
                if (curr.right != null) queue.add(curr.right);
            }

            totalSwaps += minSwaps(level);
        }

        return totalSwaps;
    }

    public int minSwaps(int[] arr) {
        int n = arr.length;
        int[] sort = arr.clone();
        Arrays.sort(sort);

        int swaps = 0;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.put(arr[i], i);
        }

        for (int i = 0; i < n; i++) {
            if (arr[i] == sort[i]) continue;

            swaps++;

            int correctIdx = map.get(sort[i]);

            int temp = arr[i];
            arr[i] = arr[correctIdx];
            arr[correctIdx] = temp;

            map.put(temp, correctIdx);
            map.put(arr[i], i);
        }

        return swaps;
    }
}

