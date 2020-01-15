package io.xelagr.algs4.graph;

import edu.princeton.cs.algs4.In;
import io.xelagr.algs4.graph.undirected.Graph;
import org.junit.Test;

import java.net.URL;

public class ConnectedComponentsTest {

    @Test
    public void test() {
        final URL graphURL = ConnectedComponentsTest.class.getResource("/graph1.txt");
        final Graph g = new Graph(new In(graphURL));

        final ConnectedComponents cc = new ConnectedComponents(g);
        System.out.println("v\tid[v]");
        for (int i = 0; i < g.V(); i++) {
            System.out.printf("%d\t%d\r\n", i, cc.id(i));
        }
    }
}
