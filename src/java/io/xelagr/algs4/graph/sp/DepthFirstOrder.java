package io.xelagr.algs4.graph.sp;

import java.util.LinkedList;

public class DepthFirstOrder {
    protected boolean[] marked;
    protected LinkedList<Integer> reversePost;

    public DepthFirstOrder(EdgeWeightedDigraph g) {
        marked = new boolean[g.V()];
        reversePost = new LinkedList<>();
        for (int v = 0; v < g.V(); v++) {
            if(!marked[v]) dfs(g, v);
        }
    }

    protected void dfs(EdgeWeightedDigraph g, int v) {
        marked[v] = true;
        for (DirectedEdge e : g.adj(v)) {
            if (!marked[e.to()]) dfs(g, e.to());
        }
        reversePost.push(v);
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
