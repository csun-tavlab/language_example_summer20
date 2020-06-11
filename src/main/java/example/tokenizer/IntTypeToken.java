package example.tokenizer;

public class IntTypeToken implements Token {
    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof IntTypeToken;
    }

    @Override
    public int hashCode() {
        return 6;
    }
} // IntTypeToken

