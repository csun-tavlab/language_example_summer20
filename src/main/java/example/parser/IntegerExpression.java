package example.parser;

public class IntegerExpression implements Expression {
    public final int value;

    public IntegerExpression(final int value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof IntegerExpression &&
                value == ((IntegerExpression)other).value);
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
} // IntegerExpression
