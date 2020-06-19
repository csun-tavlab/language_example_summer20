package example.parser;

public interface Statement {
    public <A, E extends Throwable> A accept(final StatementVisitor<A, E> visitor) throws E;
} // Statement
