package io.xelagr.algs4.graph;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int V;
    private int E;
    private List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        this.V = V;
        adj = new ArrayList[V];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public Graph(In in) {
        this.V = in.readInt();
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

    int V() {
        return V;
    }

    int E() {
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
