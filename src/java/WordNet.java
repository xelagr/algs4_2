import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {

    private Map<String, List<Integer>> wordSynsetIdsMap;
    private String[] synsets;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsetsFileName, String hypernymsFileName) {
        requireNonNull("synsetsFileName", synsetsFileName);
        requireNonNull("hypernymsFileName", hypernymsFileName);

        Digraph g = readSynsets(synsetsFileName);
        readHypernyms(hypernymsFileName, g);

        if (!isRootedDAG(g)) {
            throw new IllegalArgumentException("The input does not correspond to a rooted DAG");
        }

        sap = new SAP(g);
    }

    private Digraph readSynsets(String synsetsFileName) {
        final String[] synsetLines = new In(synsetsFileName).readAllLines();
        Digraph g = new Digraph(synsetLines.length);

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
        return g;
    }

    private void readHypernyms(String hypernymsFileName, Digraph g) {
        final In hypernymIn = new In(hypernymsFileName);
        while (hypernymIn.hasNextLine()) {
            String hLine = hypernymIn.readLine();
            final String[] hTokens = hLine.split(",");
            int v = Integer.parseInt(hTokens[0]);
            for (int i = 1; i < hTokens.length; i++) {
                int w = Integer.parseInt(hTokens[i]);
                g.addEdge(v, w);
            }
        }
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
        WordNet wordNet = new WordNet("/wordnet/synsets.txt", "/wordnet/hypernyms.txt");
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

    private void requireNonNull(String name, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
    }

    private void requireWordNetNoun(String noun) {
        if (!isNoun(noun)) {
            throw new IllegalArgumentException(noun + " is not a WordNet noun");
        }
    }

    private boolean isRootedDAG(Digraph G) {
        int roots = 0;
        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) == 0) {
                roots++;
            }
        }
        return roots == 1;
    }
}
