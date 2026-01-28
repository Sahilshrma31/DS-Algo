/*
Problem: Car Fleet
(LeetCode 853)

Problem Explanation:
You are given:
- an integer `target` representing the destination
- an array `position[]` where position[i] is the starting position of the i-th car
- an array `speed[]` where speed[i] is the speed of the i-th car

All cars move towards the target.
A faster car cannot pass a slower car ahead of it. Instead, it will
slow down and form a fleet.

A car fleet is a group of cars that travel together at the same speed.

Task:
Return the number of car fleets that will arrive at the destination.

--------------------------------------------------
Key Intuition:
--------------------------------------------------
- Cars closer to the target should be processed first
- For each car, compute the time it takes to reach the target
- If a car behind takes less or equal time than the car ahead,
  it will catch up and form the same fleet
- Only when a car takes strictly more time than all cars ahead,
  it forms a new fleet

--------------------------------------------------
Approach:
--------------------------------------------------
1. For each car, compute:
   time = (target - position[i]) / speed[i]
2. Store (position, time) pairs
3. Sort cars by position in descending order (closest to target first)
4. Traverse cars:
   - Maintain the maximum time seen so far
   - If current car’s time > maxTime, it forms a new fleet
   - Otherwise, it joins an existing fleet

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n log n)
- Sorting dominates

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(n)
- Array used to store car information

--------------------------------------------------
Example Test Cases:
--------------------------------------------------

Example 1:
Input:
target = 12
position = [10, 8, 0, 5, 3]
speed    = [2, 4, 1, 1, 3]

Output:
3

Explanation:
Fleets are formed as:
- Cars at 10 and 8 form one fleet
- Cars at 5 and 3 form one fleet
- Car at 0 is alone

--------------------------------------------------

Example 2:
Input:
target = 10
position = [3]
speed    = [3]

Output:
1

--------------------------------------------------

Example 3:
Input:
target = 100
position = [0, 2, 4]
speed    = [4, 2, 1]

Output:
1

--------------------------------------------------
*/

import java.util.*;

class Solution {

    public int carFleet(int target, int[] position, int[] speed) {

        int n = position.length;

        // car[i][0] = position
        // car[i][1] = time to reach target
        double[][] car = new double[n][2];

        for (int i = 0; i < n; i++) {
            car[i][0] = position[i];
            car[i][1] = (double) (target - position[i]) / speed[i];
        }

        // Sort by position descending (closest to target first)
        Arrays.sort(car, (a, b) -> Double.compare(b[0], a[0]));

        int fleets = 0;
        double maxTime = 0;

        for (int i = 0; i < n; i++) {
            double time = car[i][1];

            // New fleet only if it cannot catch any fleet ahead
            if (time > maxTime) {
                fleets++;
                maxTime = time;
            }
        }

        return fleets;
    }
}
