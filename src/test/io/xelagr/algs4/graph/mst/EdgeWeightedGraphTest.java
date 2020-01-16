package io.xelagr.algs4.graph.mst;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

public class EdgeWeightedGraphTest {

    @Test
    public void test() {
        final EdgeWeightedGraph ewg = new EdgeWeightedGraph(new In("tinyEWG.txt"));
        System.out.printf("EGW with %d vertices and %d edges: %s", ewg.V(), ewg.E(), ewg.edges());
    }
}
