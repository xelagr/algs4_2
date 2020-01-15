package io.xelagr.algs4.graph.directed;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.net.URL;

public class StrongComponentsTest {

    @Test
    public void test() {
        final URL graphURL = StrongComponentsTest.class.getResource("/algs4-data/tinyDG.txt");
        final Digraph g = new Digraph(new In(graphURL));

        final StrongComponents sc = new StrongComponents(g);
        System.out.println("v\tscc[v]");
        for (int i = 0; i < g.V(); i++) {
            System.out.printf("%d\t%d\r\n", i, sc.scc(i));
        }
    }
}