import static WordNetUtils.requireNonNull;

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        requireNonNull("synsets", synsets);
        requireNonNull("hypernyms", hypernyms);
        //TODO Throw an IllegalArgumentException if the input does not correspond to a rooted DAG.

        throw new UnsupportedOperationException("not implemented yet");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        requireNonNull("word", word);

        throw new UnsupportedOperationException("not implemented yet");
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

    }



    private void requireWordNetNoun(String noun) {
        if (!isNoun(noun)) {
            throw new IllegalArgumentException(noun + " is not a WordNet noun");
        }
    }
}
