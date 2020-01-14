package directed;

import edu.princeton.cs.algs4.In;

public class DigraphTest {
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph g = new Digraph(in);

        for (int v = 0; v < g.V(); v++) {
            for (int w : g.adj(v)) {
                System.out.println(v + "->" + w);
            }
        }
    }
}
