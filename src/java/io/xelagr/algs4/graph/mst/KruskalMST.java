package io.xelagr.algs4.graph.mst;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.UF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST implements MST {

    private double weight;
    private List<Edge> mst;
    private final UF uf;

    public KruskalMST(EdgeWeightedGraph G) {
        List<Edge> allEdges = new ArrayList<>(G.E());
        G.edges().forEach(allEdges::add);
        Collections.sort(allEdges);

        uf = new UF(G.V());

        mst = new ArrayList<>(G.V()-1);
        for (Edge e : allEdges) {
            if (!hasCycle(e)) {
                mst.add(e);
                weight += e.weight();
                if (mst.size() == G.V()-1) break;
            }
        }
    }

    private boolean hasCycle(EdgeWeightedGraph mst, Edge e) {
        EdgeWeightedGraph newMST = new EdgeWeightedGraph(mst);
        newMST.addEdge(e);
        return new DFS(newMST, e.either()).hasCycle();
    }

    // union-find gives complexity log* V instead of V in case of DFS
    private boolean hasCycle(Edge e) {
        int v = e.either();
        int w = e.other(v);
        if (uf.find(v) != uf.find(w)) {
            uf.union(v, w);
            return false;
        }
        return true;
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

    public static void main(String[] args) {
        KruskalMST mst = new KruskalMST(new EdgeWeightedGraph(new In("tinyEWG.txt")));
        for (Edge e : mst.edges()) {
            System.out.println(e);
        }
        System.out.printf("%.2f\r\n", mst.weight());
    }
}
