/*
Problem: Merge Triplets to Form Target Triplet
(LeetCode 1899)

Problem Explanation:
You are given a list of triplets where each triplet contains
three integers. You are also given a target triplet.

You can perform the following operation any number of times:
- Choose any two triplets and merge them
- The merge operation takes the maximum of each index

Goal:
Determine whether it is possible to merge some of the given
triplets to exactly form the target triplet.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
- Once a value exceeds the target at any index, it can never
  be reduced again (because merging uses max)
- So any triplet that has a value greater than target at any
  position is useless and must be ignored
- From the remaining valid triplets, we only need to check
  whether we can individually match all three target indices

--------------------------------------------------
Approach:
--------------------------------------------------
1. Iterate through all triplets
2. Ignore triplets where any value exceeds the target
3. Track whether we can match:
   - target[0] at index 0
   - target[1] at index 1
   - target[2] at index 2
4. If all three are matched, return true

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

--------------------------------------------------
Example Test Cases:
--------------------------------------------------

Example 1:
Input:
triplets = [[2,5,3],[1,8,4],[1,7,5]]
target   = [2,7,5]

Output:
true

Explanation:
Use [2,5,3] for index 0,
[1,7,5] for index 1 and 2

--------------------------------------------------

Example 2:
Input:
triplets = [[3,4,5],[4,5,6]]
target   = [3,4,5]

Output:
false

Explanation:
Second triplet exceeds target values, cannot be used

--------------------------------------------------

Example 3:
Input:
triplets = [[1,3,5],[2,3,4],[2,3,5]]
target   = [2,3,5]

Output:
true

--------------------------------------------------
*/

class Solution {

    public boolean mergeTriplets(int[][] triplets, int[] target) {

        boolean foundA = false;
        boolean foundB = false;
        boolean foundC = false;

        for (int[] t : triplets) {

            // Ignore triplets that exceed target at any index
            if (t[0] > target[0] ||
                t[1] > target[1] ||
                t[2] > target[2]) {
                continue;
            }

            if (t[0] == target[0]) foundA = true;
            if (t[1] == target[1]) foundB = true;
            if (t[2] == target[2]) foundC = true;
        }

        return foundA && foundB && foundC;
    }
}
