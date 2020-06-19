package example.parser;

public interface ExpressionVisitor<A, E extends Throwable> {
    public A visitIntegerExpression(final int value) throws E;
    public A visitVariableExpression(final String name) throws E;
    public A visitBooleanExpression(final boolean value) throws E;
    public A visitOperatorExpression(final Expression e1,
                                     final Op op,
                                     final Expression e2) throws E;
} // ExpressionVisitor
