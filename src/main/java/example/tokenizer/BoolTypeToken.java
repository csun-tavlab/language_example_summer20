package example.tokenizer;

public class BoolTypeToken implements Token {
    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof BoolTypeToken;
    }

    @Override
    public int hashCode() {
        return 7;
    }
} // BoolTypeToken
