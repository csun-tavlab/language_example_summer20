package example.tokenizer;

public class DivisionToken implements Token {
    @Override
    public String toString() {
        return "/";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof DivisionToken;
    }

    @Override
    public int hashCode() {
        return 3;
    }
} // DivisionToken
