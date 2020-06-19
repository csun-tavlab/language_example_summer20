package example.parser;

public class OrOp implements Op {
    @Override
    public boolean equals(final Object other) {
        return other instanceof OrOp;
    }

    @Override
    public int hashCode() {
        return 5;
    }

    @Override
    public String toString() {
        return "||";
    }

    public <A, E extends Throwable> A accept(final OpVisitor<A, E> visitor) throws E {
        return visitor.visitOrOp();
    } // accept
} // OrOp
