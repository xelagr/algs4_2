package io.xelagr.algs4.graph.undirected;

public class GraphTest {
    public static void main(String[] args) {
        Graph g = new Graph(10);
        g.addEdge(0, 1);
        g.addEdge(5, 7);
        g.addEdge(7, 1);
        System.out.println("g.V(): " + g.V());
        System.out.println("g.E(): " + g.E());
        System.out.println(g);
    }


}
