package io.xelagr.algs4.graph.undirected;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    protected final int V;
    protected int E;
    protected List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        this.V = V;
        this.adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    public Graph(In in) {
        this.V = in.readInt();
        this.adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
        int edgeCount = in.readInt();
        for (int i = 0; i < edgeCount; i++) {
            addEdge(in.readInt(), in.readInt());
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < adj.length; i++) {
            sb.append(i).append(":");
            for (Integer v: adj[i]) {
                sb.append(" ").append(v);
            }
            sb.append("\r\n");
        }
        return sb.toString();
    }
}
