package example.parser;

public class VariableExpression implements Expression {
    public final String name;

    public VariableExpression(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof VariableExpression &&
                name.equals(((VariableExpression)other).name));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public <A, E extends Throwable> A accept(final ExpressionVisitor<A, E> visitor) throws E {
        return visitor.visitVariableExpression(name);
    } // accept
} // VariableExpression
