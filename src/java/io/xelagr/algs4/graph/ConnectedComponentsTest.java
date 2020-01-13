package io.xelagr.algs4.graph;

import edu.princeton.cs.algs4.In;

import java.net.URL;

public class ConnectedComponentsTest {
    public static void main(String[] args) {
        final URL graphURL = ConnectedComponentsTest.class.getResource("/graph1.txt");
        final Graph g = new Graph(new In(graphURL));

        final ConnectedComponents cc = new ConnectedComponents(g);
        System.out.println("v\tid[v]");
        for (int i = 0; i < g.V(); i++) {
            System.out.printf("%d\t%d\r\n", i, cc.id(i));
        }
    }
}
