package example.parser;

public class AndOp implements Op {
    @Override
    public boolean equals(final Object other) {
        return other instanceof AndOp;
    }

    @Override
    public int hashCode() {
        return 4;
    }

    @Override
    public String toString() {
        return "&&";
    }

    public <A, E extends Throwable> A accept(final OpVisitor<A, E> visitor) throws E {
        return visitor.visitAndOp();
    } // accept
} // AndOp
