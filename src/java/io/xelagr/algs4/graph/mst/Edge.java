package io.xelagr.algs4.graph.mst;

public class Edge implements Comparable<Edge>{
    private final int v, w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either() {
        return v;
    }

    public int other(int v) {
        return this.v == v ? w : v;
    }

    @Override
    public int compareTo(Edge that) {
        if (weight < that.weight) return -1;
        else if (weight > that.weight) return 1;
        return 0;
    }

    public double weight() {
        return weight;
    }

    @Override
    public String toString() {
        return "{" + v + ", " + w + ", " + weight + '}';
    }
}
