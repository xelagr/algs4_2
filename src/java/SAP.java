import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        requireNonNull("G", G);

        this.G = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        throw new UnsupportedOperationException("not implemented yet");
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        throw new UnsupportedOperationException("not implemented yet");
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        requireNonNull("v", v);
        requireNonNull("w", w);
        //TODO Throw an IllegalArgumentException if any iterable argument contains a null item

        throw new UnsupportedOperationException("not implemented yet");
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        requireNonNull("v", v);
        requireNonNull("w", w);
        //TODO Throw an IllegalArgumentException if any iterable argument contains a null item

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

    private void validateVertex(int v) {
        if (v < 0 || v >= G.V())
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (G.V()-1));
    }
}
