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

    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E {
        return visitor.visitEqualsToken();
    }
} // EqualsToken
