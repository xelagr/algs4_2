import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class WordNet {

    private final Digraph G;
    private HashSet<String> words;
    private HashMap<String, Integer> wordVertexMap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        requireNonNull("synsets", synsets);
        requireNonNull("hypernyms", hypernyms);

        final String[] synsetLines = new In(synsets).readAllLines();
        G = new Digraph(synsetLines.length);

        if (!isRootedDAG(G)) {
            throw new IllegalArgumentException("The input does not correspond to a rooted DAG");
        }

        words = new HashSet<>(G.V());
        wordVertexMap = new HashMap<>(G.V());

        int percent = G.V() / 100;
        for (String sLine : synsetLines) {
            final String[] sTokens = sLine.split(",");
            int id = Integer.parseInt(sTokens[0]);
            if (id % percent == 0) {
                System.out.printf("Loaded %d%% of synsets\r\n", id / percent);
            }
            final String[] sWords = sTokens[1].split(" ");
            words.addAll(Arrays.asList(sWords));
            words.forEach(word -> wordVertexMap.put(word, id));
        }

        final In hypernymIn = new In(hypernyms);
        int counter = 0;
        System.out.println();
        while(hypernymIn.hasNextLine()) {
            counter++;
            if (counter % 1000 == 0) {
                System.out.printf("Loaded %d lines of hypernyms\r\n", counter);
            }
            String hLine = hypernymIn.readLine();
            final String[] hTokens = hLine.split(",");
            int v = Integer.parseInt(hTokens[0]);
            for (int i = 1; i < hTokens.length; i++) {
                int w = Integer.parseInt(hTokens[i]);
                G.addEdge(v, w);
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return words;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        requireNonNull("word", word);
        return words.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        requireNonNull("nounA", nounA);
        requireNonNull("nounB", nounB);
        requireWordNetNoun(nounA);
        requireWordNetNoun(nounB);

        throw new UnsupportedOperationException("not implemented yet");
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        requireNonNull("nounA", nounA);
        requireNonNull("nounB", nounB);
        requireWordNetNoun(nounA);
        requireWordNetNoun(nounB);

        throw new UnsupportedOperationException("not implemented yet");
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
    }

    private void requireNonNull(String name, Object o) {
        if (o == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
    }

    private void requireWordNetNoun(String noun) {
        if (!isNoun(noun)) {
            throw new IllegalArgumentException(noun + " is not a WordNet noun");
        }
    }

    private boolean isRootedDAG(Digraph G) {
        boolean result = false;
        final Topological topological = new Topological(G);
        if (topological.hasOrder()) {
            //TODO use DFS to find roots for all unmarked vertices in topological order
            //TODO and verify that roots are the same
            result = true;
        }
        return result;
    }
}
