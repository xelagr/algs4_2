package io.xelagr.algs4.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Paths {
    private int s;
    boolean[] marked;
    Integer[] edgeTo;

    public Paths(Graph g, int s) {
        this.s = s;
        marked = new boolean[g.V()];
        edgeTo = new Integer[g.V()];
        DFS(g, s);
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

    private void DFS(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if(!marked[w]) {
                DFS(g, w);
                edgeTo[w] = v;
            }
        }
    }
}
