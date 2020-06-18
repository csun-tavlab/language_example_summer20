package example.tokenizer;

public class BooleanToken implements Token {
    public final boolean value;

    public BooleanToken(final Boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object obj) {
        return (obj instanceof BooleanToken &&
                value == ((BooleanToken)obj).value);
    }

    @Override
    public int hashCode() {
        return (value) ? 1 : 0;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E {
        return visitor.visitBooleanToken(value);
    }
} // BooleanToken
