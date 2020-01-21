package io.xelagr.algs4.graph.sp;

import java.util.Arrays;
import java.util.LinkedList;

public class AsyclicSP implements SP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public AsyclicSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[s] = 0.0;

        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(G);
        System.out.println(depthFirstOrder.reversePost());

        for(Integer v  : depthFirstOrder.reversePost()) {
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

}
