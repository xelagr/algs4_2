package io.xelagr.algs4.graph.mst;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.adj = new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int edgeCount = in.readInt();
        for (int i = 0; i < edgeCount; i++) {
            addEdge(new Edge(in.readInt(), in.readInt(), in.readDouble()));
        }
    }

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(EdgeWeightedGraph G) {
        this(G.V());
        for (Edge e : G.edges()) {
            addEdge(new Edge(e));
        }
    }

    public void addEdge(Edge e) {
        adj[e.either()].add(e);
        adj[e.other(e.either())].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        final Set<Edge> edges = new TreeSet<>();
        for (List<Edge> l : adj) {
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
