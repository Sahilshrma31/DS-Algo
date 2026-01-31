/*
Problem: Gas Station
(LeetCode 134)

Problem Explanation:
You are given two integer arrays:
- gas[i]  → amount of gas available at station i
- cost[i] → gas required to travel from station i to station (i + 1)

The stations are arranged in a circular route.
You start with an empty tank at some station.

Task:
Return the index of the gas station from which you can travel
around the circuit exactly once without running out of gas.
If it is not possible, return -1.

--------------------------------------------------
Detailed Intuition:
--------------------------------------------------

1) Global Feasibility Check (Why sumGas >= sumCost is required)

If the total gas available across all stations is less than
the total cost required to travel all edges, then no starting
point can ever work.

This is because gas cannot be created, only redistributed.

So:
- If sum(gas) < sum(cost) → answer is impossible → return -1

This check prunes all impossible cases early.

--------------------------------------------------

2) Why Greedy Works (Key Insight)

Suppose we start from station `start` and keep track of
current gas in the tank.

If at some station `i`:
    currentGas < 0

That means:
- Starting from `start`, we could not even reach station `i + 1`
- Therefore, **no station between `start` and `i` can be a valid start**

Why?
Because every station between `start` and `i` has:
- Less initial gas contribution (we skipped some gas)
- Same or more cost to travel

So we safely discard all those stations and try starting fresh
from `i + 1`.

This is the core greedy reasoning.

--------------------------------------------------

3) One-Pass Greedy Strategy

- Maintain:
  - currGas → gas available in current journey
  - start   → candidate starting station

- Traverse stations from 0 to n - 1
- Add gas[i] - cost[i] to currGas
- If currGas becomes negative:
    - Reset currGas to 0
    - Set start = i + 1

By the end, `start` is guaranteed to be the valid starting index
(because global feasibility was already confirmed).

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n)

Single pass through the stations.

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1)

Only constant extra variables used.

--------------------------------------------------
Example Walkthrough:
--------------------------------------------------

Example:
gas  = [1, 2, 3, 4, 5]
cost = [3, 4, 5, 1, 2]

Total gas  = 15
Total cost = 15 → feasible

Iteration:
i = 0 → currGas = -2 → reset, start = 1
i = 1 → currGas = -2 → reset, start = 2
i = 2 → currGas = -2 → reset, start = 3
i = 3 → currGas = 3
i = 4 → currGas = 6

Answer = 3

--------------------------------------------------
*/

class Solution {

    public int canCompleteCircuit(int[] gas, int[] cost) {

        int n = gas.length;

        int sumGas = 0;
        int sumCost = 0;

        // Global feasibility check
        for (int i = 0; i < n; i++) {
            sumGas += gas[i];
            sumCost += cost[i];
        }

        if (sumGas < sumCost) {
            return -1;
        }

        int start = 0;
        int currGas = 0;

        // Greedy traversal
        for (int i = 0; i < n; i++) {

            currGas += gas[i] - cost[i];

            if (currGas < 0) {
                currGas = 0;
                start = i + 1;
            }
        }

        return start;
    }
}
