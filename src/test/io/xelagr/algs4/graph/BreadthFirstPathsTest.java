package io.xelagr.algs4.graph;

import io.xelagr.algs4.graph.undirected.Graph;
import org.junit.Test;

public class BreadthFirstPathsTest {

    @Test
    public void test() {
        Graph g = new Graph(9);
        g.addEdge(0, 3);
        g.addEdge(0, 4);

        g.addEdge(4, 1);
        g.addEdge(4, 6);
        g.addEdge(4, 8);

        g.addEdge(1, 7);

        g.addEdge(2, 5);

        int s = 0;
        GraphPaths paths = new BreadthFirstPaths(g, s);
        for (int v = 0; v < g.V(); v++) {
//            if (pathsSlow.hasPathTo(v)) {
            System.out.printf("Path from %d to %d: %s\r\n", s, v, paths.pathTo(v));
//            }
        }
    }
}
