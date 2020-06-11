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
} // VariableToken
