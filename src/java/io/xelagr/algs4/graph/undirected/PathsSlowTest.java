package io.xelagr.algs4.graph.undirected;

public class PathsSlowTest {
    public static void main(String[] args) {
        Graph g = new Graph(9);
        g.addEdge(0, 3);
        g.addEdge(0, 4);

        g.addEdge(4, 1);
        g.addEdge(4, 6);
        g.addEdge(4, 8);

        g.addEdge(1, 7);

        g.addEdge(2, 5);

        int s = 0;
        PathsSlow pathsSlow = new PathsSlow(g, s);
        for (int v = 0; v < g.V(); v++) {
//            if (pathsSlow.hasPathTo(v)) {
                System.out.printf("Path from %d to %d: %s\r\n", s, v, pathsSlow.pathTo(v));
//            }
        }
    }
}
