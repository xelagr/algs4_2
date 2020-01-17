import org.junit.Test;

public class WordNetTest {

    @Test
    public void testValidFiles() {
        WordNet wordNet = new WordNet("/wordnet/synsets.txt", "/wordnet/hypernyms.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotRootedDAG() {
        WordNet wordNet = new WordNet("/wordnet/synsets3.txt", "/wordnet/hypernyms3InvalidTwoRoots.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotRootedDAG2() {
        WordNet wordNet = new WordNet("/wordnet/synsets3.txt", "/wordnet/hypernyms3InvalidCycle.txt");
    }

}
