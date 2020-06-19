package example.parser;

public interface StatementVisitor<A, E extends Throwable> {
    public A visitVariableDeclarationInitializationStatement(final Type type,
                                                             final String variableName,
                                                             final Expression expression) throws E;
} // StatementVisitor
