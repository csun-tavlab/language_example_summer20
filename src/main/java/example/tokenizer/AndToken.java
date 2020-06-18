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

    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E {
        return visitor.visitAndToken();
    }
} // AndToken
