package io.xelagr.algs4.graph.sp;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

public class SPTest {

    @Test
    public void test1() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWD.txt"));
        int s = 0;
        DijkstraSP sp = new DijkstraSP(G, s);
        for (int v = 0; v < G.V(); v++) {
            System.out.printf("%d to %d (%.2f): ", s, v, sp.distTo(v));
            for (DirectedEdge e : sp.pathTo(v)) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void test2() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("mediumEWD.txt"));
        int s = 0;
        DijkstraSP sp = new DijkstraSP(G, s);
        for (int v = 0; v < G.V(); v++) {
            System.out.printf("%d to %d (%.2f): ", s, v, sp.distTo(v));
            for (DirectedEdge e : sp.pathTo(v)) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }
}
