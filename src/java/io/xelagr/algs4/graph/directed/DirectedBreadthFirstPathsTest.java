package io.xelagr.algs4.graph.directed;

import edu.princeton.cs.algs4.In;
import io.xelagr.algs4.graph.BreadthFirstPaths;
import io.xelagr.algs4.graph.GraphPaths;
import io.xelagr.algs4.graph.undirected.Graph;

public class DirectedBreadthFirstPathsTest {
    public static void main(String[] args) {
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

    private static String parseInt(Integer i) {
        return i != null ? String.valueOf(i) : "-";
    }
}
