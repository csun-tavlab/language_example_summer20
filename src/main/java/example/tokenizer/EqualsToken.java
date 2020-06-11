package example.tokenizer;

public class EqualsToken implements Token {
    @Override
    public String toString() {
        return "=";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof EqualsToken;
    }

    @Override
    public int hashCode() {
        return 8;
    }
} // EqualsToken
