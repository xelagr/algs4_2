package io.xelagr.algs4.graph.mst;

public class DFS {
    private boolean[] marked;
    private boolean hasCycles;

    public DFS(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                DFS(G, v, -1);
            }
        }
    }
    public DFS(EdgeWeightedGraph G, int s) {
        marked = new boolean[G.V()];
        DFS(G, s, -1);
    }


    private void DFS(EdgeWeightedGraph G, int v, int prev) {
        marked[v] = true;
        for(Edge e : G.adj(v)) {
            int w = e.other(v);
            if (!marked[w]) {
                DFS(G, w, v);
            }
            else if (w != prev){
                hasCycles = true;
            }
        }
    }

    public boolean hasCycle() {
        return hasCycles;
    }
}
