import java.util.*;

class DijkstraWithSet {
    static class Pair implements Comparable<Pair> {
        int node, dist;

        Pair(int dist, int node) {
            this.dist = dist;
            this.node = node;
        }

        @Override
        public int compareTo(Pair other) {
            if (this.dist == other.dist) return this.node - other.node;
            return this.dist - other.dist;
        }
    }

    public static int[] dijkstra(int V, List<List<Pair>> adj, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        TreeSet<Pair> set = new TreeSet<>();
        set.add(new Pair(0, src));

        while (!set.isEmpty()) {
            Pair p = set.pollFirst(); // smallest distance
            int u = p.node;

            for (Pair edge : adj.get(u)) {
                int v = edge.node;
                int weight = edge.dist;

                if (dist[u] + weight < dist[v]) {
                    // Remove old pair if present
                    set.remove(new Pair(dist[v], v));

                    dist[v] = dist[u] + weight;
                    set.add(new Pair(dist[v], v));
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        // Undirected weighted graph
        adj.get(0).add(new Pair(2, 1));
        adj.get(0).add(new Pair(4, 2));
        adj.get(1).add(new Pair(2, 0));
        adj.get(1).add(new Pair(1, 2));
        adj.get(1).add(new Pair(7, 3));
        adj.get(2).add(new Pair(4, 0));
        adj.get(2).add(new Pair(1, 1));
        adj.get(2).add(new Pair(3, 3));
        adj.get(3).add(new Pair(7, 1));
        adj.get(3).add(new Pair(3, 2));
        adj.get(3).add(new Pair(2, 4));
        adj.get(4).add(new Pair(2, 3));

        int[] dist = dijkstra(V, adj, 0);

        System.out.println("Shortest distances from source:");
        for (int i = 0; i < V; i++) {
            System.out.println("Node " + i + " -> " + dist[i]);
        }
    }
}
