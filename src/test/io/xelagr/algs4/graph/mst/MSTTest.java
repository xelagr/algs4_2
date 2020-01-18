package io.xelagr.algs4.graph.mst;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

public class MSTTest {

    @Test
    public void testKruskal() {
        final MST mst = new KruskalMST(new EdgeWeightedGraph(new In("tinyEWG.txt")));
        for (Edge e : mst.edges()) {
            System.out.println(e);
        }
        System.out.printf("MST weight: %.2f\r\n", mst.weight());
    }

    @Test
    public void testPrim() {
        final MST mst = new LazyPrimMST(new EdgeWeightedGraph(new In("tinyEWG.txt")));
        for (Edge e : mst.edges()) {
            System.out.println(e);
        }
        System.out.printf("MST weight: %.2f\r\n", mst.weight());
    }
}
