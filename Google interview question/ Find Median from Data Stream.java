/*
====================================================
📌 Problem: Find Median from Data Stream 
====================================================

Design a data structure that supports:
1) addNum(int num)   -> Add a number to the data stream
2) findMedian()     -> Return the median of all elements so far

----------------------------------------------------
🧠 Intuition
----------------------------------------------------
Median depends on the order of numbers:
- If count is odd → middle element
- If count is even → average of two middle elements

We need fast insertion + fast median retrieval.

----------------------------------------------------
🧩 Approaches Covered
----------------------------------------------------
1️⃣ Brute Force (ArrayList + Sorting)
2️⃣ Optimized (Two Heaps: MaxHeap + MinHeap)

----------------------------------------------------
⏱ Time & Space Complexity
----------------------------------------------------

1️⃣ Brute Force:
   addNum(): O(1)
   findMedian(): O(N log N)
   Space: O(N)

2️⃣ Two Heaps (Optimal):
   addNum(): O(log N)
   findMedian(): O(1)
   Space: O(N)

====================================================
*/

import java.util.*;

/* ==================================================
   1️⃣ BRUTE FORCE APPROACH
   ================================================== */
class MedianFinderBruteForce {

    private List<Integer> list;

    public MedianFinderBruteForce() {
        list = new ArrayList<>();
    }

    // Add number to list
    public void addNum(int num) {
        list.add(num);
    }

    // Sort and find median
    public double findMedian() {
        Collections.sort(list);
        int n = list.size();

        if (n % 2 == 1) {
            return list.get(n / 2);
        } else {
            return (list.get(n / 2 - 1) + list.get(n / 2)) / 2.0;
        }
    }
}

/* ==================================================
   2️⃣ OPTIMIZED APPROACH (TWO HEAPS)
   ================================================== */
class MedianFinder {

    // Max heap for left half
    private PriorityQueue<Integer> left;

    // Min heap for right half
    private PriorityQueue<Integer> right;

    public MedianFinder() {
        left = new PriorityQueue<>((a, b) -> b - a); // max heap
        right = new PriorityQueue<>();               // min heap
    }

    public void addNum(int num) {
        // Step 1: Add to max heap
        left.offer(num);

        // Step 2: Balance → move max of left to right
        right.offer(left.poll());

        // Step 3: Ensure left has >= right elements
        if (right.size() > left.size()) {
            left.offer(right.poll());
        }
    }

    public double findMedian() {
        // If odd count, left heap has extra element
        if (left.size() > right.size()) {
            return left.peek();
        }
        // Even count → average of tops
        return (left.peek() + right.peek()) / 2.0;
    }
}

/*
====================================================
✅ SUMMARY
====================================================
✔ Brute force is simple but slow for large streams  
✔ Two-heap solution is industry-standard & optimal  
✔ Used in FAANG interviews very frequently  

====================================================
*/

