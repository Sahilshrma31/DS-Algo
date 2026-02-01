/*
Problem: Next Permutation
(LeetCode 31)

Problem Explanation:
You are given an array of integers representing a permutation.
Rearrange the numbers into the lexicographically next greater permutation.

If no such permutation exists (the array is in descending order),
rearrange it to the lowest possible order (ascending).

The replacement must be done in-place with constant extra memory.

--------------------------------------------------
Detailed Intuition:
--------------------------------------------------

A permutation increases lexicographically when we can make the
smallest possible change starting from the right.

Key observations:
1) If the array is entirely non-increasing from left to right,
   it is already the largest permutation.
   The next permutation is simply the reversed array.

2) Otherwise, there exists a position (pivot) such that:
   nums[pivot] < nums[pivot + 1]

   Everything to the right of this pivot is in decreasing order.

--------------------------------------------------
Step-by-Step Logic:
--------------------------------------------------

Step 1: Find the pivot index
- Traverse from right to left
- Find the first index `pivot` where nums[pivot] < nums[pivot + 1]
- This is the point where we can increase the permutation

Step 2: Find the next greater element to swap with pivot
- Traverse again from the right
- Find the first element greater than nums[pivot]
- Swap it with nums[pivot]
- This ensures the increase is minimal

Step 3: Reverse the suffix
- Reverse the subarray after pivot
- This gives the smallest lexicographical order for the suffix

--------------------------------------------------
Why This Works:
--------------------------------------------------
- The suffix after the pivot is the largest possible arrangement
- Swapping with the smallest greater element increases the number minimally
- Reversing the suffix resets it to the smallest possible order

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

- One pass to find pivot
- One pass to find swap index
- One pass to reverse suffix

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

- All operations are done in-place

--------------------------------------------------
Example Test Cases:
--------------------------------------------------

Example 1:
Input:
nums = [1,2,3]

Process:
Pivot = index 1 (2 < 3)
Swap 2 and 3 → [1,3,2]
Reverse suffix → [1,3,2]

Output:
[1,3,2]

--------------------------------------------------

Example 2:
Input:
nums = [3,2,1]

Process:
No pivot found
Reverse entire array

Output:
[1,2,3]

--------------------------------------------------

Example 3:
Input:
nums = [1,1,5]

Output:
[1,5,1]

--------------------------------------------------
*/

class Solution {

    public void nextPermutation(int[] nums) {

        int n = nums.length;
        int pivotIdx = -1;

        // Step 1: Find pivot
        for (int i = n - 1; i > 0; i--) {
            if (nums[i - 1] < nums[i]) {
                pivotIdx = i - 1;
                break;
            }
        }

        // Step 2: Swap with next greater element
        if (pivotIdx != -1) {
            for (int i = n - 1; i > pivotIdx; i--) {
                if (nums[i] > nums[pivotIdx]) {
                    swap(nums, i, pivotIdx);
                    break;
                }
            }
        }

        // Step 3: Reverse the suffix
        reverse(nums, pivotIdx + 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start) {
        int left = start;
        int right = nums.length - 1;

        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
}
