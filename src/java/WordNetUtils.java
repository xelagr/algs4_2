public class WordNetUtils {
    public static void requireNonNull(String name, Object o) {
        if (o == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
    }
}
