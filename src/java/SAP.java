import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {

    private static class SAPResult {
        static final SAPResult NO_RESULT = new SAPResult(-1, -1);
        int ancestor;
        int length;

        SAPResult(int ancestor, int length) {
            this.ancestor = ancestor;
            this.length = length;
        }
    }

    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        requireNonNull("G", G);
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return getSapResult(v, w).length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return getSapResult(v, w).ancestor;
    }

    private SAPResult getSapResult(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        if (v == w) return new SAPResult(v, 0);

        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(G, w);

        return getSapResult(vPaths, wPaths);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return getSapResult(v, w).length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return getSapResult(v, w).ancestor;
    }

    private SAPResult getSapResult(Iterable<Integer> v, Iterable<Integer> w) {
        requireNonNullIterable("v", v);
        requireNonNullIterable("w", w);

        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(G, w);

        return getSapResult(vPaths, wPaths);
    }

    private SAPResult getSapResult(BreadthFirstDirectedPaths vPaths, BreadthFirstDirectedPaths wPaths) {

        int sap = Integer.MAX_VALUE;
        int sca = -1;
        for (int x = 0; x < G.V(); x++) {
            if(vPaths.hasPathTo(x) && wPaths.hasPathTo(x)) {
                int d = vPaths.distTo(x) + wPaths.distTo(x);
                if (d < sap) {
                    sap = d;
                    sca = x;
                }
            }
        }

        return sca == -1 ? SAPResult.NO_RESULT : new SAPResult(sca, sap);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        // TODO add test code
    }

    private void requireNonNull(String name, Object obj) {
        if (obj == null) {
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
