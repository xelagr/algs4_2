package io.xelagr.algs4.graph.sp;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;

import java.util.Arrays;
import java.util.LinkedList;

public class DijkstraSP implements SP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private final IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[s] = 0.0;

        pq = new IndexMinPQ<>(G.V());
        pq.insert(s, distTo[s]);

        while(!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        double newDist = distTo[v] + e.weight();
        if (distTo[w] > newDist) {
            distTo[w] = newDist;
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else  pq.insert(w, distTo[w]);
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        LinkedList<DirectedEdge> stack = new LinkedList<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            stack.push(e);
        }
        return stack;
    }

    public boolean hasPathTo(int v) {
        return edgeTo[v] != null;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(args[0]));
        int s = Integer.parseInt(args[1]);
        DijkstraSP sp = new DijkstraSP(G, s);
        for (int v = 0; v < G.V(); v++) {
            System.out.printf("%d to %d (%.2f): ", s, v, sp.distTo(v));
            for (DirectedEdge e : sp.pathTo(v)) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }
}
