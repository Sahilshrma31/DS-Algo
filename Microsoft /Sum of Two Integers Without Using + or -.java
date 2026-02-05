import java.util.*;

/*
Problem: Sum of Two Integers Without Using + or -
(LeetCode 371)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Given two integers a and b, return the sum of the two integers
without using the operators '+' and '-'.

--------------------------------------------------
Detailed Intuition:
--------------------------------------------------
Binary addition can be simulated using bitwise operations.

For any two bits:
- XOR (^) gives the sum without carry
- AND (&) gives the carry bits

Example:
  a = 5  -> 0101
  b = 3  -> 0011

XOR (sum without carry):
  0101 ^ 0011 = 0110  -> 6

AND (carry bits):
  0101 & 0011 = 0001
  Carry must be added to next higher bit:
  0001 << 1 = 0010  -> 2

Now repeat with:
  a = 6
  b = 2

Repeat until carry becomes 0.
At that point, no further changes will happen and `a` holds the final sum.

--------------------------------------------------
Approach:
--------------------------------------------------
Loop until carry becomes 0:
1. temp = (a & b) << 1   // compute carry
2. a = a ^ b            // compute sum without carry
3. b = temp             // move carry to be added next

When b becomes 0, there is no carry left, and a is the result.

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(1)

The loop runs at most 32 times for 32-bit integers.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

No extra space used.

--------------------------------------------------
Examples:
--------------------------------------------------

Example 1:
Input:
a = 2, b = 3

Binary:
2 -> 0010
3 -> 0011

Output:
5

--------------------------------------------------

Example 2:
Input:
a = -2, b = 3

Output:
1

--------------------------------------------------
*/

class Solution {

    public int getSum(int a, int b) {

        // for sum we can use xor as for same values it is 0 and for diff it is 1
        // for carry we will use and and left shift by 1 to move the carry as carry always goes to the left side

        while (b != 0) { // iterate till carry becomes 0
            int temp = (a & b) << 1;
            a = a ^ b;     // doing addition without carry
            b = temp;     // doing carry
        }

        return a;
    }
}

