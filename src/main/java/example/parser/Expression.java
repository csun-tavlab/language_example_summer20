package example.parser;

public interface Expression {
    public <A, E extends Throwable> A accept(final ExpressionVisitor<A, E> visitor) throws E;
} // Expression
