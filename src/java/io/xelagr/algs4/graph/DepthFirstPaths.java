package io.xelagr.algs4.graph;

import java.util.LinkedList;

public class DepthFirstPaths extends GraphPaths {
    public DepthFirstPaths(Graph g, int s) {
        super(g, s);
        DFS2(g, s);
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

    private void DFS2(Graph g, int v) {
        marked[v] = true;
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(v);
        while(!stack.isEmpty()) {
            v = stack.pop();
            for (int w : g.adj(v)) {
                if(!marked[w]) {
                    stack.push(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                }
            }
        }
    }
}
