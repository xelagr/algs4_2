package io.xelagr.algs4.graph.sp;

import edu.princeton.cs.algs4.In;
import io.xelagr.algs4.graph.mst.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class EdgeWeightedDigraph {
    private final int V;
    private int E;
    private List<DirectedEdge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.adj = new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    public EdgeWeightedDigraph(In in) {
        this(in.readInt());
        int edgeCount = in.readInt();
        for (int i = 0; i < edgeCount; i++) {
            addEdge(new DirectedEdge(in.readInt(), in.readInt(), in.readDouble()));
        }
    }

    @SuppressWarnings("unchecked")
    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this(G.V());
        for (DirectedEdge e : G.edges()) {
            addEdge(new DirectedEdge(e));
        }
    }

    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DirectedEdge>  edges() {
        final Set<DirectedEdge> edges = new TreeSet<>();
        for (List<DirectedEdge> l : adj) {
            edges.addAll(l);
        }
        return edges;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    @Override
    public String toString() {
        return "[" + V + ", " + E() + ']';
    }

}
