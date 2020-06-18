package example.tokenizer;

public class LeftParenToken implements Token {
    @Override
    public String toString() {
        return "(";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof LeftParenToken;
    }

    @Override
    public int hashCode() {
        return 3;
    }

    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E {
        return visitor.visitLeftParenToken();
    }
} // LeftParenToken
