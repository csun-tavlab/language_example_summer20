public class PlusToken implements Token {
    // Java gives us the following constructor automatically
    // public PlusToken() {}

    @Override
    public String toString() {
        return "+";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof PlusToken;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
