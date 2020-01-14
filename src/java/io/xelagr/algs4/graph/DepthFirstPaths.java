package io.xelagr.algs4.graph;

import io.xelagr.algs4.graph.undirected.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DepthFirstPaths extends GraphPaths {
    private boolean hasCycles;
    private List<Iterable<Integer>> cycles;
    public DepthFirstPaths(Graph g, int s) {
        super(g, s);
        cycles = new ArrayList<>();
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
                else {
                    if (w == s) {
                        edgeTo[w] = v;
                    }
                    hasCycles = true;
                    LinkedList<Integer> cycle = new LinkedList<>();
                    cycle.push(w);
                    cycle.push(v);
                    Integer s = v;
                    while(edgeTo[s] != w) {
                        s = edgeTo[s];
                        cycle.push(s);
                    }
                    cycles.add(cycle);
                }
            }
        }
    }

    public boolean hasCycles() {
        return hasCycles;
    }

    public Iterable<Iterable<Integer>> getCycles() {
        return cycles;
    }
}
