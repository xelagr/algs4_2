package io.xelagr.algs4.graph;

import java.util.ArrayList;
import java.util.List;

public class PathsSlow {
    private Graph g;
    private int s;
    private int v;

    public PathsSlow(Graph g, int s) {
        this.g = g;
        this.s = s;
    }

    public boolean hasPathTo(int v) {
        List<Integer> path = getPath(v);
        return !path.isEmpty();
    }

    public Iterable<Integer> pathTo(int v) {
        return getPath(v);
    }

    private List<Integer> getPath(int v) {
        this.v = v;
        boolean[] marked = new boolean[g.V()];
        marked[s] = true;
        List<Integer> path = new ArrayList<>();
        DFS(s, marked, path);
        return path;
    }

    private void DFS(int s, boolean[] marked, List<Integer> path) {
        if (s == v) {
            path.add(0, s);
            return;
        }
        for (Integer a : g.adj(s)) {
            if(!marked[a]) {
                marked[a] = true;
                DFS(a, marked, path);
                if(!path.isEmpty()) {
                    path.add(0, s);
                    break;
                }
            }
        }
    }
}
