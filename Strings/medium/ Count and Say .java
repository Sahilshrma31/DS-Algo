/**
 * LeetCode Problem: 38. Count and Say
 * ----------------------------------
 * 
 * Problem Summary:
 * The "count-and-say" sequence is a sequence of digit strings defined as:
 * 
 * 1. countAndSay(1) = "1"
 * 2. To determine countAndSay(n), describe the previous term countAndSay(n-1).
 * 
 * Description rule:
 * - Read the previous string and count consecutive identical digits.
 * - Say the count followed by the digit.
 * 
 * Example:
 * n = 4
 * Sequence:
 * 1
 * 11        -> one 1
 * 21        -> two 1s
 * 1211      -> one 2, one 1
 * 
 * Output: "1211"
 * 
 * ----------------------------------
 * Intuition:
 * This problem is naturally recursive.
 * To build the nth term, we only need the (n-1)th term.
 * 
 * For each term:
 * - Traverse the string
 * - Group consecutive identical characters
 * - Count how many times they repeat
 * - Append "count + character" to form the next string
 * 
 * ----------------------------------
 * Approach:
 * 1. Base case:
 *    - If n == 1, return "1"
 * 
 * 2. Recursively compute countAndSay(n - 1)
 * 
 * 3. Process the previous string:
 *    - Use a loop to traverse characters
 *    - Use an inner loop to count consecutive characters
 *    - Append the count and character using StringBuilder
 * 
 * 4. Return the constructed string
 * 
 * ----------------------------------
 * Time Complexity:
 * - O(N * L)
 *   Where:
 *   N = number of recursion calls
 *   L = average length of the generated string
 * 
 * Space Complexity:
 * - O(N * L) due to recursion stack and string construction
 * 
 * ----------------------------------
 * Author: Sahil Sharma
 */

class Solution {

    public String countAndSay(int n) {
        // Base case
        if (n == 1) {
            return "1";
        }

        // Recursive call to get previous term
        String say = countAndSay(n - 1);
        StringBuilder result = new StringBuilder();

        // Traverse the previous term
        for (int i = 0; i < say.length(); i++) {

            char currentChar = say.charAt(i);
            int count = 0;

            // Count consecutive identical characters
            while (i < say.length() && say.charAt(i) == currentChar) {
                count++;
                i++;
            }
            result.append(count);
            result.append(currentChar);

            // Adjust index because for-loop will increment it again
            i--;
        }

        return result.toString();
    }
}
