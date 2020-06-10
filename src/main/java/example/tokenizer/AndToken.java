package example.tokenizer;

public class AndToken implements Token {
    @Override
    public String toString() {
        return "&&";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof AndToken;
    }

    @Override
    public int hashCode() {
        return 4;
    }
} // AndToken
