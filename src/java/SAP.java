import edu.princeton.cs.algs4.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SAP {

    private final Digraph G;
    private final Digraph RG;
    private final BreadthFirstDirectedPaths rootPaths;
    private final Integer root;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        requireNonNull("G", G);
        this.G = G;

        DepthFirstOrder order = new DepthFirstOrder(G);
        this.root = order.post().iterator().next();
        this.RG = G.reverse();
        this.rootPaths = new BreadthFirstDirectedPaths(RG, root);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        final SAPResult result = lengthAndAncestor(v, w);
        return result.length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path

    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        Iterator<Integer> vp = rootPaths.pathTo(v).iterator();
        Iterator<Integer> wp = rootPaths.pathTo(w).iterator();

//        System.out.printf("Path from %d to %d: %s\r\n", root, v, rootPaths.pathTo(v));
//        System.out.printf("Path from %d to %d: %s\r\n", root, w, rootPaths.pathTo(w));

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

    private SAPResult lengthAndAncestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        int ancestor = ancestor(v, w);
        if (ancestor == -1) return SAPResult.NO_RESULT;

        BreadthFirstDirectedPaths ancestorPaths = new BreadthFirstDirectedPaths(RG, ancestor);
        final int length = ancestorPaths.distTo(v) + ancestorPaths.distTo(w);
        return new SAPResult(v, w, ancestor, length);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        final SAPResult result = lengthAndAncestor(v, w);
        return result.length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        final SAPResult result = lengthAndAncestor(v, w);
        return result.ancestor;
    }

    private SAPResult lengthAndAncestor(Iterable<Integer> v, Iterable<Integer> w) {
        requireNonNullIterable("v", v);
        requireNonNullIterable("w", w);

        SAPResult shortestResult = new SAPResult(-1, -1, -1, Integer.MAX_VALUE);
        for (Integer a : v) {
            for (Integer b : w) {
                final SAPResult result = lengthAndAncestor(a, b);
                if (result.length < shortestResult.length) {
                    shortestResult.length = result.length;
                    shortestResult.ancestor = result.ancestor;
                    shortestResult.v = a;
                    shortestResult.w = b;
                }
            }
        }
        return shortestResult;
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        testSingleVertices(args[0]);
        testVerticesSubsets();
    }

    private static void testVerticesSubsets() {
        In in = new In("digraph25.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        final List<Integer> A = Arrays.asList(13, 23, 24);
        final List<Integer> B = Arrays.asList(6, 16, 17);
        StdOut.printf("length = %d, ancestor = %d\n", sap.length(A, B), sap.ancestor(A, B));
    }

    private static void testSingleVertices(String arg) {
        In in = new In(arg);
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

    private static class SAPResult {
        int v;
        int w;
        int ancestor;
        int length;
        private static SAPResult NO_RESULT = new SAPResult(-1, -1, -1, -1);

        SAPResult(int v, int w, int ancestor, int length) {
            this.v = v;
            this.w = w;
            this.ancestor = ancestor;
            this.length = length;
        }
    }
}
