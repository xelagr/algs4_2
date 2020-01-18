package io.xelagr.algs4.graph.mst;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LazyPrimMST implements MST {
    private boolean[] marked;
    private List<Edge> mst;
    private double weight;

    public LazyPrimMST(EdgeWeightedGraph g) {
        mst = new ArrayList<>(g.V()-1);
        marked = new boolean[g.V()];
        MinPQ<Edge> pq = new MinPQ<>();
        visit(g, pq, 0);

        while(!pq.isEmpty() && mst.size() < g.V()-1) {
            Edge min = pq.delMin();
            int v = min.either();
            int w = min.other(v);
            if (marked[v] && marked[w]) continue;

            int x = marked[v] ? w: v;
            visit(g, pq, x);
            mst.add(min);
            weight += min.weight();
        }
    }

    private void visit(EdgeWeightedGraph g, MinPQ<Edge> pq, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            if (!marked[e.other(v)]) {
                pq.insert(e);
            }
        }
        printPQLen(pq);
    }

    private void printPQLen(MinPQ<Edge> pq) {
        Iterator<Edge> i= pq.iterator();
        int n = 0;
        while(i.hasNext()) {
            i.next();
            n++;
        }
        System.out.println(n);
    }

    @Override
    public Iterable<Edge> edges() {
        return mst;
    }

    @Override
    public double weight() {
        return weight;
    }
}
