package example.tokenizer;

public class OrToken implements Token {
    @Override
    public String toString() {
        return "||";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof OrToken;
    }

    @Override
    public int hashCode() {
        return 5;
    }
} // OrToken
