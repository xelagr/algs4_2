package io.xelagr.algs4.graph.directed;

import io.xelagr.algs4.graph.undirected.Graph;

public class KosarajuSharirSCC extends DepthFirstOrder {

    private int[] scc;
    private int group = 0;

    public KosarajuSharirSCC(Digraph g) {
        super(g.reverse());

        scc = new int[g.V()];
        marked = new boolean[g.V()];
        for(int v : reversePost()) {
            if(!marked[v]) {
                dfs2(g, v);
                group++;
            }
        }
    }

    private void dfs2(Graph g, int v) {
        marked[v] = true;
        scc[v] = group;
        for (int w : g.adj(v)) {
            if(!marked[w]) {
                dfs2(g, w);
            }
        }
    }

    public int scc(int v) {
        return scc[v];
    }
}
