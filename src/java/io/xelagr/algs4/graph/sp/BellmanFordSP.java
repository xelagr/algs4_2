package io.xelagr.algs4.graph.sp;

import java.util.Arrays;
import java.util.LinkedList;

public class BellmanFordSP implements SP {
    private double[] distTo;
    private final DirectedEdge[] edgeTo;
    private final LinkedList<Integer> distChanged;
    private final boolean[] queued;
    private int edgeRelaxCount;

    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        distChanged = new LinkedList<>();
        queued = new boolean[G.V()];

        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[s] = 0.0;
        distChanged.push(s);
        queued[s] = true;

        edgeRelaxCount = 0;
        while(!distChanged.isEmpty()) {
            Integer v = distChanged.poll();
            queued[v] = false;
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
        /*for (int i = 0; i < G.V(); i++) {
            for (int v = 0; v < G.V(); v++) {
                for (DirectedEdge e : G.adj(v)) {
                    relax(e);
                }
            }
        }*/
        System.out.printf("%d iterations for %d vetrices and %d edges", edgeRelaxCount, G.V(), G.E());
    }

    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        double newDist = distTo[v] + e.weight();
        if (distTo[w] > newDist) {
            distTo[w] = newDist;
            edgeTo[w] = e;
            if (!queued[w]) {
                distChanged.push(w);
                queued[w] = true;
            }
        }
        edgeRelaxCount++;
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

}
