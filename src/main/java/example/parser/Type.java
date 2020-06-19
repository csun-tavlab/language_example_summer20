package example.parser;

public interface Type {
    public <A, E extends Throwable> A accept(final TypeVisitor<A, E> visitor) throws E;
} // Type
