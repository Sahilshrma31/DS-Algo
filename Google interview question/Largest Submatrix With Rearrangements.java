import java.util.*;

/*
Problem: Largest Submatrix With Rearrangements (LeetCode 1727)

--------------------------------------------------
Problem Description
--------------------------------------------------
You are given a binary matrix (0s and 1s).

You are allowed to rearrange the columns of each row
independently.

After rearranging, you need to find the largest
submatrix (consisting only of 1s) and return its area.

--------------------------------------------------
Key Observations
--------------------------------------------------
1. Rearranging columns means order doesn't matter,
   only the heights matter.
2. For every row, we can treat it like a histogram.
3. If we sort heights in descending order,
   we can greedily maximize area.
4. Width = number of columns considered
   Height = minimum height among them (after sorting).

--------------------------------------------------
Intuition
--------------------------------------------------
Think of each row as the base of a histogram.

If we keep track of consecutive 1s vertically,
each cell becomes the "height" of histogram.

Now, for each row:
- Rearranging columns means sorting heights.
- Larger heights first → maximize area.

Then:
Area = height * width

--------------------------------------------------
Approach
--------------------------------------------------
1. Traverse matrix row by row.
2. For each cell:
   If it's 1 → add previous row height.
3. Convert row into heights array.
4. Sort heights in descending order.
5. For each index:
   width = index + 1
   area = height * width
6. Track maximum area.

--------------------------------------------------
State Definition
--------------------------------------------------
matrix[row][col] =
height of consecutive 1s ending at that cell.

--------------------------------------------------
Transition
--------------------------------------------------
if(matrix[row][col] == 1):
    matrix[row][col] += matrix[row-1][col]

--------------------------------------------------
Time Complexity
--------------------------------------------------
O(n * m log m)

- n rows
- sorting each row takes m log m

--------------------------------------------------
Space Complexity
--------------------------------------------------
O(m)

--------------------------------------------------
Example
--------------------------------------------------
Input:
matrix = [
 [0,0,1],
 [1,1,1],
 [1,0,1]
]

Output:
4

Explanation:
After rearranging columns optimally,
largest submatrix of 1s has area = 4.
*/

class Solution {

    /*
    --------------------------------------------------
    1. Brute Force (Conceptual - Not Efficient)
    --------------------------------------------------
    Try all column permutations for each row.
    This is factorial → not practical.
    */

    /*
    --------------------------------------------------
    2. Better Approach (Histogram + Sorting)
    --------------------------------------------------
    */

    public int largestSubmatrix(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;
        int maxArea = 0;

        for (int row = 0; row < n; row++) {

            // Build heights (like histogram)
            for (int col = 0; col < m; col++) {

                if (matrix[row][col] == 1 && row > 0) {
                    matrix[row][col] += matrix[row - 1][col];
                }
            }

            // Copy row into array for sorting
            Integer[] heights = new Integer[m];
            for (int i = 0; i < m; i++) {
                heights[i] = matrix[row][i];
            }

            // Sort in descending order
            Arrays.sort(heights, Collections.reverseOrder());

            // Calculate max area for this row
            for (int i = 0; i < m; i++) {

                int width = i + 1;
                int height = heights[i];

                maxArea = Math.max(maxArea, width * height);
            }
        }

        return maxArea;
    }

    /*
    --------------------------------------------------
    3. Optimized Without Sorting (Counting Sort Idea)
    --------------------------------------------------
    */

    public int largestSubmatrixOptimized(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;
        int maxArea = 0;

        for (int row = 0; row < n; row++) {

            for (int col = 0; col < m; col++) {

                if (matrix[row][col] == 1 && row > 0) {
                    matrix[row][col] += matrix[row - 1][col];
                }
            }

            // Frequency array (since height ≤ n)
            int[] count = new int[n + 1];

            for (int val : matrix[row]) {
                count[val]++;
            }

            int width = 0;

            // Traverse from largest height to smallest
            for (int h = n; h >= 1; h--) {

                width += count[h];
                maxArea = Math.max(maxArea, width * h);
            }
        }

        return maxArea;
    }
}