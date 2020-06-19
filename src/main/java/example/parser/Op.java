package example.parser;

public interface Op {
    public <A, E extends Throwable> A accept(final OpVisitor<A, E> visitor) throws E;
} // Op
