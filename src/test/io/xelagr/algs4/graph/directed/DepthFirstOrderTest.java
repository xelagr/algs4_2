package io.xelagr.algs4.graph.directed;

import org.junit.Test;

public class DepthFirstOrderTest {

    @Test
    public void test() {
        Digraph g = new Digraph(7);
        g.addEdge(0, 5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);

        g.addEdge(1, 4);

        g.addEdge(3, 5);
        g.addEdge(3, 6);
        g.addEdge(3, 4);
        g.addEdge(3, 2);

        g.addEdge(5, 2);

        g.addEdge(6, 0);
        g.addEdge(6, 4);

        final DepthFirstOrder dfo = new DepthFirstOrder(g);
        System.out.print("Topological order: ");
        for (Integer v : dfo.reversePost()) {
            System.out.print(v + " ");
        }
    }
}
