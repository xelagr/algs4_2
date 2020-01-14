package io.xelagr.algs4.graph;

import io.xelagr.algs4.graph.undirected.Graph;

import java.util.LinkedList;

public abstract class GraphPaths {
    protected int s;
    protected boolean[] marked;
    protected Integer[] edgeTo;

    public GraphPaths(Graph g, int s) {
        this.s = s;
        this.marked = new boolean[g.V()];
        this.edgeTo = new Integer[g.V()];
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedList<Integer> path = new LinkedList<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    public Integer edgeTo(int v) {
        return edgeTo[v];
    }
}
