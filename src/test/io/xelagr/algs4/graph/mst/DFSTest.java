package io.xelagr.algs4.graph.mst;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DFSTest {

    @Test
    public void testNoCycle() {
        EdgeWeightedGraph g = new EdgeWeightedGraph(4);
        g.addEdge(new Edge(0, 1, 1));
        g.addEdge(new Edge(1, 2, 1));
        g.addEdge(new Edge(2, 3, 1));
        assertFalse(new DFS(g).hasCycle());
    }

    @Test
    public void testCycle() {
        EdgeWeightedGraph g = new EdgeWeightedGraph(3);
        g.addEdge(new Edge(0, 1, 1));
        g.addEdge(new Edge(1, 2, 1));
        g.addEdge(new Edge(2, 0, 1));
        assertTrue(new DFS(g).hasCycle());
    }
}
