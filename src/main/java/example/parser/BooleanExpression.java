package example.parser;

public class BooleanExpression implements Expression {
    public final boolean value;

    public BooleanExpression(final boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof BooleanExpression &&
                value == ((BooleanExpression)other).value);
    }

    @Override
    public int hashCode() {
        return (value) ? 1 : 0;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
} // BooleanExpression
