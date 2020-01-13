package io.xelagr.algs4.graph;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstPaths extends GraphPaths {
    public BreadthFirstPaths(Graph g, int s) {
        super(g, s);
        BFS(g);
    }

    private void BFS(Graph g) {
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        marked[s] = true;
        while(!q.isEmpty()) {
            Integer v = q.remove();
            for (Integer w: g.adj(v)) {
                if(!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    q.add(w);
                }
            }
        }
    }

}
