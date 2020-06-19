package example.parser;

public interface TypeVisitor<A, E extends Throwable> {
    public A visitIntType() throws E;
    public A visitBoolType() throws E;
} // TypeVisitor
