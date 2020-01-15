package io.xelagr.algs4.graph.directed;

import io.xelagr.algs4.graph.undirected.Graph;

public class StrongComponents extends DepthFirstOrder {

    private int[] scc;
    int group = 0;

    public StrongComponents(Digraph g) {
        super(g.reverse());

        scc = new int[g.V()];
        marked = new boolean[g.V()];
        System.out.print("Reverse postorder: ");
        for(int v : reversePost()) {
            System.out.print(v + " ");
            dfs2(g, v);
            group++;
        }
        System.out.println();
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
