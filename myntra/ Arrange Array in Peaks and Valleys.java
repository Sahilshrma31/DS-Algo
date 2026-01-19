/*
====================================================
📌 Problem: Arrange Array in Peaks and Valleys
====================================================

Given an array of integers, rearrange it so that it follows
a **wave pattern**.

Two possible patterns:

----------------------------------------------------
1️ Pattern 1: a[0] starts HIGH (Peak first)
----------------------------------------------------
a[0] >= a[1] <= a[2] >= a[3] <= a[4] ...

Meaning:
- Even index  → Peak
- Odd index   → Valley

----------------------------------------------------
2️ Pattern 2: a[0] starts LOW (Valley first)
----------------------------------------------------
a[0] <= a[1] >= a[2] <= a[3] >= a[4] ...

Meaning:
- Even index  → Valley
- Odd index   → Peak

====================================================
🧠 Key Insight
====================================================

- We only compare **adjacent elements**
- One single pass is enough
- No sorting required
- Greedy swapping works

====================================================
⏱ Time Complexity
====================================================
O(n)

====================================================
📦 Space Complexity
====================================================
O(1)

(In-place swaps)

====================================================
*/

class Solution {

    /*
    ====================================================
    🔹 Pattern 1: a[0] starts HIGH (Peak first)
    ====================================================
    Even index  → Peak
    Odd index   → Valley
    ====================================================
    */
    public static void wavePeakFirst(int[] arr) {

        int n = arr.length;

        for (int i = 1; i < n; i++) {

            // Odd index → Valley
            if (i % 2 == 1 && arr[i] > arr[i - 1]) {
                swap(arr, i, i - 1);
            }

            // Even index → Peak
            else if (i % 2 == 0 && arr[i] < arr[i - 1]) {
                swap(arr, i, i - 1);
            }
        }
    }

    /*
    ====================================================
    🔹 Pattern 2: a[0] starts LOW (Valley first)
    ====================================================
    Even index  → Valley
    Odd index   → Peak
    ====================================================
    */
    public static void waveValleyFirst(int[] arr) {

        int n = arr.length;

        for (int i = 1; i < n; i++) {

            // Odd index → Peak
            if (i % 2 == 1 && arr[i] < arr[i - 1]) {
                swap(arr, i, i - 1);
            }

            // Even index → Valley
            else if (i % 2 == 0 && arr[i] > arr[i - 1]) {
                swap(arr, i, i - 1);
            }
        }
    }

    /*
    ====================================================
    🔹 Utility swap function
    ====================================================
    */
    private static void swap(int[] arr, int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
