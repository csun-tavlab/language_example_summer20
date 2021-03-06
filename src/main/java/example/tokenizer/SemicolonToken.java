package example.tokenizer;

public class SemicolonToken implements Token {
    @Override
    public String toString() {
        return ";";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof SemicolonToken;
    }

    @Override
    public int hashCode() {
        return 9;
    }

    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E {
        return visitor.visitSemicolonToken();
    }
} // SemicolonToken
