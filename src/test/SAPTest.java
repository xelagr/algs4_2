import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SAPTest {

    @Test
    public void testDigraph1() {
        Digraph g = new Digraph(new In("/wordnet/digraph1.txt"));
        SAP sap = new SAP(g);
        testSAP(sap, 6, 6, 0, 6);
        testSAP(sap, 2, 6, -1, -1);
        testSAP(sap, 3, 11, 4, 1);
        testSAP(sap, 9, 12, 3, 5);
        testSAP(sap, 7, 2, 4, 0);
        testSAP(sap, 1, 6, -1, -1);
    }

    @Test
    public void testDigraph2() {
        SAP sap = new SAP(new Digraph(new In("/wordnet/digraph2.txt")));
        testSAP(sap, 1, 3, 2, 3);
    }

    @Test
    public void testDigraph3() {
        SAP sap = new SAP(new Digraph(new In("/wordnet/digraph3.txt")));
        testSAP(sap, 1, 1, 0, 1);
        testSAP(sap, 2, 3, 1, 3);
    }

    @Test
    public void testDigraph4() {
        SAP sap = new SAP(new Digraph(new In("/wordnet/digraph4.txt")));
        testSAP(sap, 4, 1, 3, 4);
    }

    @Test
    public void testDigraph5() {
        SAP sap = new SAP(new Digraph(new In("/wordnet/digraph5.txt")));
        testSAP(sap, 7, 16, 4, 16);
    }

    @Test
    public void testDigraph6() {
        SAP sap = new SAP(new Digraph(new In("/wordnet/digraph6.txt")));
        testSAP(sap, 6, 5, 1, 6);
    }

    @Test
    public void testDigraph9() {
        SAP sap = new SAP(new Digraph(new In("/wordnet/digraph9.txt")));
        testSAP(sap, 6, 1, 2, 3);
        testSAP(sap, 8, 5, 1, 8);
    }

    @Test
    public void testDigraph25() {
        SAP sap = new SAP(new Digraph(new In("/wordnet/digraph25.txt")));
        testSAP(sap, Arrays.asList(13, 23, 24), Arrays.asList(6, 16, 17), 4, 3);
    }

    private void testSAP(SAP sap, int v, int w, int expectedLength, int expectedAncestor) {
        int length = sap.length(v, w);
        assertEquals(String.format("For [%d, %d] expected length is %d, but found %d", v, w, expectedLength, length), expectedLength, length);
        int ancestor = sap.ancestor(v, w);
        assertEquals(String.format("For [%d, %d] expected ancestor is %d, but found %d", v, w, expectedAncestor, ancestor), expectedAncestor, ancestor);
    }

    private void testSAP(SAP sap, Iterable<Integer> v, Iterable<Integer> w, int expectedLength, int expectedAncestor) {
        assertEquals(expectedLength, sap.length(v, w));
        assertEquals(expectedAncestor, sap.ancestor(v, w));
    }

}
