package io.xelagr.algs4.graph.directed;

import edu.princeton.cs.algs4.In;
import io.xelagr.algs4.graph.BreadthFirstPaths;
import io.xelagr.algs4.graph.DepthFirstPaths;
import io.xelagr.algs4.graph.GraphPaths;
import io.xelagr.algs4.graph.undirected.Graph;
import org.junit.Test;

public class DirectedPathsTest {

    @Test
    public void bfsTest() {
        final In in = new In("/algs4-data/tinyDG2.txt");
        Graph g = new Digraph(in);

        int s = 0;
        BreadthFirstPaths paths = new BreadthFirstPaths(g, s);
        System.out.println("v\tedgeTo[]\tdistTo[]");
        System.out.println("-----------------------");
        for (int v = 0; v < g.V(); v++) {
            System.out.printf("%d\t%s        \t%s\r\n", v, parseInt(paths.edgeTo(v)), parseInt(paths.distTo(v)));
        }
    }

    @Test
    public void dfsTest() {
        final In in = new In("/algs4-data/tinyDG2.txt");
        Graph g = new Digraph(in);

        int s = 0;
        DepthFirstPaths paths = new DepthFirstPaths(g, s);
        System.out.println("v\tedgeTo[]\tdistTo[]");
        System.out.println("-----------------------");
        for (int v = 0; v < g.V(); v++) {
            System.out.printf("%d\t%s\r\n", v, parseInt(paths.edgeTo(v)));
        }
        System.out.println();
        System.out.println("The graph " + (paths.hasCycles() ? "has cycles:" : "doesn't have cycles"));
        if (paths.hasCycles()) {
            for (Iterable<Integer> cycle : paths.getCycles()) {
                for (Integer v : cycle) {
                    System.out.printf("%d->", v);
                }
                System.out.print(cycle.iterator().next());
                System.out.println();
            }
        }
    }

    private static String parseInt(Integer i) {
        return i != null ? String.valueOf(i) : "-";
    }
}
