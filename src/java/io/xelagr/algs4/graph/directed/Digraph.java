package io.xelagr.algs4.graph.directed;

import edu.princeton.cs.algs4.In;
import io.xelagr.algs4.graph.undirected.Graph;

public class Digraph extends Graph {

    public Digraph(int v) {
        super(v);
    }

    public Digraph(In in) {
        super(in);
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public Digraph reverse() {
        throw new UnsupportedOperationException("no implemented yet");
    }

}
