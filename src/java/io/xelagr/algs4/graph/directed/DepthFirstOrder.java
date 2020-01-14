package io.xelagr.algs4.graph.directed;

import java.util.LinkedList;

public class DepthFirstOrder {
    boolean[] marked;
    private LinkedList<Integer> reversePost;

    public DepthFirstOrder(Digraph g) {
        marked = new boolean[g.V()];
        reversePost = new LinkedList<>();
        for (int v = 0; v < g.V(); v++) {
            if(!marked[v]) dfs(g, v);
        }
    }

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) dfs(g, w);
        }
        reversePost.push(v);
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
