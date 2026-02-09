import java.io.*;
import java.util.*;

/*
Problem: B. Seats
(Codeforces)

--------------------------------------------------
Problem Summary:
--------------------------------------------------
Cordell manages a row of n seats where students are not allowed to sit next to
each other.

You are given a binary string s:
- '1' means the seat is already occupied
- '0' means the seat is free

It is guaranteed that initially no two adjacent seats are occupied.

Cordell will add students until it becomes impossible to seat anyone else
(without violating the rule of no adjacent occupied seats).

Your task is to return the minimum total number of students seated at the end.

--------------------------------------------------
Intuition:
--------------------------------------------------
We want to place students in such a way that:
- No two adjacent seats have '1'
- Eventually, no free seat can accept a student anymore

To make it impossible to seat more students:
- Every '0' seat must have at least one neighbor as '1'

So we greedily place students in the largest possible gaps to block future placements.

Key idea:
- Handle boundary cases
- Greedily fill middle gaps so that no segment of three consecutive positions
  remains free to place a new student

--------------------------------------------------
Approach:
--------------------------------------------------
1. Handle small n (n <= 2) separately
2. Fix boundary cases:
   - If s[0] == '0' and s[1] == '0', place at index 1
   - If s[n-1] == '0' and s[n-2] == '0', place at index n-2
3. Traverse the string:
   - Track previous occupied index
   - If distance between occupied seats becomes 3, place a student
4. Count total number of occupied seats

--------------------------------------------------
Time Complexity:
--------------------------------------------------
O(n) per test case

--------------------------------------------------
Space Complexity:
--------------------------------------------------
O(1) extra space (in-place modification of char array)

--------------------------------------------------
Example:
--------------------------------------------------
Input:
5
1
0
3
000
5
00000
6
100101
13
0000100001000

Output:
1
1
2
3
5

--------------------------------------------------
*/

public class B_Seats {

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int tc = fs.nextInt();
        while (tc-- > 0) {
            int n = fs.nextInt();
            char s[] = fs.next().toCharArray();

            if (n <= 2) {
                out.append(1).append('\n');
                continue;
            }

            // boundary handling
            if (s[0] == '0' && s[1] == '0') {
                s[1] = '1';
            }
            if (s[n - 1] == '0' && s[n - 2] == '0') {
                s[n - 2] = '1';
            }

            int prev = -1;
            int cnt = 0;

            for (int i = 0; i < n; i++) {
                if (s[i] == '0') {
                    if (i - prev == 3) {
                        s[i] = '1';
                        cnt++;
                        prev = i;
                    }
                } else {
                    cnt++;
                    prev = i;
                }
            }

            out.append(cnt).append('\n');
        }

        System.out.print(out.toString());
    }

    // -------- Fast Scanner ----------
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
