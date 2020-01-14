package io.xelagr.algs4.graph;

import io.xelagr.algs4.graph.undirected.Graph;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstPaths extends GraphPaths {
    private int[] distTo;
    public BreadthFirstPaths(Graph g, int s) {
        super(g, s);
        distTo = new int[g.V()];
        BFS(g);
    }

    private void BFS(Graph g) {
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        marked[s] = true;
        distTo[s] = 0;
        while(!q.isEmpty()) {
            Integer v = q.remove();
            for (Integer w: g.adj(v)) {
                if(!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v]+1;
                    q.add(w);
                }
            }
        }
    }

    public Integer distTo(int v) {
        return distTo[v];
    }

}
