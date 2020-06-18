package example.tokenizer;

public class VariableToken implements Token {
    public final String name;

    public VariableToken(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        return (obj instanceof VariableToken &&
                name.equals(((VariableToken)obj).name));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E {
        return visitor.visitVariableToken(name);
    }
} // VariableToken
