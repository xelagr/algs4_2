package io.xelagr.algs4.graph.sp;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

public class SPTest {

    @Test
    public void testTinyEWD() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWD.txt"));
        int s = 0;
        SP sp = new DijkstraSP(G, s);
        test(sp, G, s);
    }

    @Test
    public void testmediumEWD() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("mediumEWD.txt"));
        int s = 0;
        SP sp = new DijkstraSP(G, s);
        test(sp, G, s);
    }

    @Test
    public void testDAG() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWDAG.txt"));
        int s = 0;
        SP sp = new AsyclicSP(G, s);
        test(sp, G, s);
    }

    @Test
    public void testDijkstraNegativeEdge() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyNegativeEdge.txt"));
        int s = 0;
        SP sp = new DijkstraSP(G, s);
        test(sp, G, s);
    }

    @Test
    public void testBellmanFordNegativeEdge() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyNegativeEdge.txt"));
        int s = 0;
        SP sp = new BellmanFordSP(G, s);
        test(sp, G, s);
    }

    private void test(SP sp, EdgeWeightedDigraph g, int s) {
        for (int v = 0; v < g.V(); v++) {
            System.out.printf("%d to %d (%.2f): ", s, v, sp.distTo(v));
            for (DirectedEdge e : sp.pathTo(v)) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }
}
