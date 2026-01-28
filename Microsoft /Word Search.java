/*
Problem: Word Search
(LeetCode 79)

Problem Explanation:
You are given a 2D grid of characters `board` and a string `word`.
You need to check whether the word exists in the grid.

Rules:
- The word must be formed using sequentially adjacent cells
- Adjacent cells are horizontally or vertically neighboring
- The same cell cannot be used more than once in a single word path

Return true if the word exists, otherwise false.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
- This is a classic backtracking / DFS problem on a grid
- We try to start the word from every cell that matches word[0]
- From that cell, we explore all 4 directions
- If a path fails, we backtrack and try another direction

--------------------------------------------------
Approach:
--------------------------------------------------
1. Iterate over every cell in the grid
2. If the cell matches the first character of the word,
   start DFS from that cell
3. In DFS:
   - If all characters are matched, return true
   - If out of bounds or character mismatch, return false
   - Mark the current cell as visited
   - Explore all 4 directions
   - Restore the cell (backtracking)
4. If no path matches the word, return false

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(m * n * 4^L)

where:
- m = number of rows
- n = number of columns
- L = length of the word

In the worst case, from each cell we explore 4 directions.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(L)

- Recursion stack depth equals word length
- No extra visited array (we modify the board in-place)

--------------------------------------------------
Example Test Cases:
--------------------------------------------------

Example 1:
Input:
board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]
word = "ABCCED"

Output:
true

--------------------------------------------------

Example 2:
Input:
board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]
word = "SEE"

Output:
true

--------------------------------------------------

Example 3:
Input:
board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]
word = "ABCB"

Output:
false

--------------------------------------------------
*/

class Solution {

    int m, n, len;

    public boolean exist(char[][] board, String word) {

        m = board.length;
        n = board[0].length;
        len = word.length();

        // Try starting DFS from every cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0) &&
                    dfs(board, i, j, 0, word)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, int i, int j,
                        int idx, String word) {

        // All characters matched
        if (idx == len) {
            return true;
        }

        // Boundary and mismatch checks
        if (i < 0 || j < 0 || i >= m || j >= n) return false;
        if (board[i][j] != word.charAt(idx)) return false;

        // Mark current cell as visited
        char temp = board[i][j];
        board[i][j] = '#';

        boolean found =
                dfs(board, i, j - 1, idx + 1, word) ||
                dfs(board, i, j + 1, idx + 1, word) ||
                dfs(board, i - 1, j, idx + 1, word) ||
                dfs(board, i + 1, j, idx + 1, word);

        // Backtrack
        board[i][j] = temp;

        return found;
    }
}
