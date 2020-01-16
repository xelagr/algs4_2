import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {

    private final Map<String, List<Integer>> wordSynsetIdsMap;
    private final String[] synsets;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsetsFileName, String hypernymsFileName) {
        requireNonNull("synsetsFileName", synsetsFileName);
        requireNonNull("hypernymsFileName", hypernymsFileName);

        final String[] synsetLines = new In(synsetsFileName).readAllLines();
        Digraph g = new Digraph(synsetLines.length);

        if (!isRootedDAG(g)) {
            throw new IllegalArgumentException("The input does not correspond to a rooted DAG");
        }

        wordSynsetIdsMap = new HashMap<>(g.V());
        synsets = new String[g.V()];

        for (String sLine : synsetLines) {
            final String[] sTokens = sLine.split(",");
            int id = Integer.parseInt(sTokens[0]);

            final String synset = sTokens[1];
            synsets[id] = synset;

            final String[] sWords = synset.split(" ");
            for (String word : sWords) {
                if (!wordSynsetIdsMap.containsKey(word)) {
                    wordSynsetIdsMap.put(word, new ArrayList<>());
                }
                wordSynsetIdsMap.get(word).add(id);
            }
        }

        final In hypernymIn = new In(hypernymsFileName);
        while(hypernymIn.hasNextLine()) {
            String hLine = hypernymIn.readLine();
            final String[] hTokens = hLine.split(",");
            int v = Integer.parseInt(hTokens[0]);
            for (int i = 1; i < hTokens.length; i++) {
                int w = Integer.parseInt(hTokens[i]);
                g.addEdge(v, w);
            }
        }

        sap = new SAP(g);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordSynsetIdsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        requireNonNull("word", word);
        return wordSynsetIdsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        requireNonNull("nounA", nounA);
        requireNonNull("nounB", nounB);
        requireWordNetNoun(nounA);
        requireWordNetNoun(nounB);

        return sap.length(wordSynsetIdsMap.get(nounA), wordSynsetIdsMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        requireNonNull("nounA", nounA);
        requireNonNull("nounB", nounB);
        requireWordNetNoun(nounA);
        requireWordNetNoun(nounB);


        final int ancestor = sap.ancestor(wordSynsetIdsMap.get(nounA), wordSynsetIdsMap.get(nounB));
        return synsets[ancestor];
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println("First WordNet noun: " + wordNet.nouns().iterator().next());
        System.out.println();

        printIfWordNetNoun(wordNet, "George_W._Bush");
        printIfWordNetNoun(wordNet, "chimpanzee");
        printIfWordNetNoun(wordNet, "President_John_F._Kennedy");
        printIfWordNetNoun(wordNet, "Eric_Arthur_Blair");
        printIfWordNetNoun(wordNet, "bla");
        System.out.println();

//        TODO check the logic - should be primate instead of physical_entity?
        printAncestorAndDistance(wordNet, "George_W._Bush", "chimpanzee");
//        printAncestorAndDistance(wordNet, "George_W._Bush", "physical_entity");
//        printAncestorAndDistance(wordNet, "chimpanzee", "physical_entity");
//        printAncestorAndDistance(wordNet, "George_W._Bush", "primate");
//        printAncestorAndDistance(wordNet, "chimpanzee", "primate");

        printAncestorAndDistance(wordNet, "George_W._Bush", "President_John_F._Kennedy");
        printAncestorAndDistance(wordNet, "George_W._Bush", "Eric_Arthur_Blair");
        printAncestorAndDistance(wordNet, "man", "chimpanzee");
    }

    private static void printIfWordNetNoun(WordNet wordNet, String noun) {
        System.out.printf("Is '%s' a WordNet noun? : %b\r\n", noun, wordNet.isNoun(noun));
    }

    private static void printAncestorAndDistance(WordNet wordNet, String nounA, String nounB) {
        System.out.printf("Length of shortest ancestral path between '%s' and '%s' is %d \r\n", nounA, nounB, wordNet.distance(nounA, nounB));
        System.out.printf("A shortest common ancestor of '%s' and '%s' is '%s' \r\n", nounA, nounB, wordNet.sap(nounA, nounB));
        System.out.println();
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
