package example.tokenizer;

public class RightParenToken implements Token {
    @Override
    public String toString() {
        return ")";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof RightParenToken;
    }

    @Override
    public int hashCode() {
        return 4;
    }

    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E {
        return visitor.visitRightParenToken();
    }
} // RightParenToken
