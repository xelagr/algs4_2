import edu.princeton.cs.algs4.*;

import java.util.Iterator;

public class SAP {

    private final Digraph G;
    private final Digraph RG;
    private final BreadthFirstDirectedPaths rootPaths;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        requireNonNull("G", G);
        this.G = G;

        DepthFirstOrder order = new DepthFirstOrder(G);
        Integer root = order.post().iterator().next();
        RG = G.reverse();
        this.rootPaths = new BreadthFirstDirectedPaths(RG, root);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        int ancestor = ancestor(v, w);
        BreadthFirstDirectedPaths ancestorPaths = new BreadthFirstDirectedPaths(RG, ancestor);
        int l = ancestorPaths.distTo(v) + ancestorPaths.distTo(w);
        return l;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        Iterator<Integer> vp = rootPaths.pathTo(v).iterator();
        Iterator<Integer> wp = rootPaths.pathTo(w).iterator();
        System.out.println(rootPaths.pathTo(v));
        System.out.println(rootPaths.pathTo(w));
        int ancestor = -1;
        while(vp.hasNext() && wp.hasNext()) {
            v = vp.next();
            w = wp.next();
            if (v == w) {
                ancestor = v;
            }
            else {
                break;
            }
        }
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        requireNonNullIterable("v", v);
        requireNonNullIterable("w", w);

        throw new UnsupportedOperationException("not implemented yet");
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        requireNonNullIterable("v", v);
        requireNonNullIterable("w", w);

        throw new UnsupportedOperationException("not implemented yet");
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private void requireNonNull(String name, Object o) {
        if (o == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
    }

    private <T> void requireNonNullIterable(String name, Iterable<T> iterable) {
        requireNonNull(name, iterable);
        for (T t : iterable) {
            if (t == null) {
                throw new IllegalArgumentException("An item in iterable argument " + name + " cannot be null");
            }
        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= G.V())
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (G.V()-1));
    }
}
