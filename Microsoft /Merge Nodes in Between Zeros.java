import java.util.*;

/*
Problem: Merge Nodes in Between Zeros
(LeetCode 2181)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
You are given a linked list where:
- The list starts and ends with a node having value 0
- Between every two zero-valued nodes, there are some positive integers

Your task is to:
- Merge all nodes between two consecutive zeros
- Replace them with a single node whose value is the sum of those nodes
- Remove all zero-valued nodes from the final list

--------------------------------------------------
Intuition:
--------------------------------------------------
The list is already structured in segments separated by 0s.

So the idea is simple:
- Skip the first zero
- For every segment between two zeros:
  - Keep adding values until you hit the next zero
  - Store this sum in the first node of the segment
  - Link it directly to the start of the next segment

This way, we modify the list in-place without creating extra nodes.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Use two pointers:
   - p1 → points to the node where the sum will be stored
   - p2 → traverses the list to calculate the sum
2. For each segment:
   - Keep adding values until p2 reaches a zero
   - Store the sum in p1
   - Skip the zero and reconnect pointers
3. Return head.next (to skip the initial zero)

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

Each node is visited once.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

No extra space used.

--------------------------------------------------
Example:
--------------------------------------------------
Input:
0 -> 3 -> 1 -> 0 -> 4 -> 5 -> 2 -> 0

Output:
4 -> 11

Explanation:
(3 + 1) = 4
(4 + 5 + 2) = 11

--------------------------------------------------
*/

/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class Solution {

    public ListNode mergeNodes(ListNode head) {

        ListNode p1 = head.next;
        ListNode p2 = p1;

        while (p2 != null) {

            int sum = 0;

            while (p2 != null && p2.val != 0) {
                sum += p2.val;
                p2 = p2.next;
            }

            p1.val = sum;
            p2 = p2.next;

            p1.next = p2;
            p1 = p2;
        }

        return head.next;
    }
}
