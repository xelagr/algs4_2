package io.xelagr.algs4.graph.directed;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

public class DigraphTest {

    @Test
    public void test() {
        In in = new In("/tinyDG.txt");
        Digraph g = new Digraph(in);

        for (int v = 0; v < g.V(); v++) {
            for (int w : g.adj(v)) {
                System.out.println(v + "->" + w);
            }
        }
    }

    @Test
    public void testReverse() {
        In in = new In("/tinyDG.txt");
        Digraph g = new Digraph(in);

        System.out.println("Original graph");
        for (int v = 0; v < g.V(); v++) {
            for (int w : g.adj(v)) {
                System.out.println(v + "->" + w);
            }
        }

        System.out.println();
        System.out.println("Reverse graph");
        Digraph gr = g.reverse();
        for (int v = 0; v < gr.V(); v++) {
            for (int w : gr.adj(v)) {
                System.out.println(v + "->" + w);
            }
        }
    }
}
