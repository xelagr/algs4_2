package io.xelagr.algs4.graph;

import io.xelagr.algs4.graph.undirected.Graph;

public class ConnectedComponents {
    private int[] id;
    private boolean[] marked;
    private int count;

    public ConnectedComponents(Graph g) {
        id = new int[g.V()];
        marked = new boolean[g.V()];
        for (int v = 0; v < marked.length; v++) {
            if (!marked[v]) {
                DFS(g, v);
                count++;
            }
        }
    }

    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        return id[v];
    }

    private void DFS(Graph g, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : g.adj(v)) {
            if(!marked[w]) {
                DFS(g, w);
            }
        }
    }
}
