package example.tokenizer;

public class MinusToken implements Token {
    @Override
    public String toString() {
        return "-";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof MinusToken;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E {
        return visitor.visitMinusToken();
    }
} // MinusToken
