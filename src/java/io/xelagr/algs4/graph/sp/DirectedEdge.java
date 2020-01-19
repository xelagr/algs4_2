package io.xelagr.algs4.graph.sp;

public class DirectedEdge implements Comparable<DirectedEdge>{
    private final int v, w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public DirectedEdge(DirectedEdge e) {
        this(e.v, e.w, e.weight);
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    @Override
    public int compareTo(DirectedEdge that) {
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
