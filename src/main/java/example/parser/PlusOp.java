package example.parser;

public class PlusOp implements Op {
    @Override
    public boolean equals(final Object other) {
        return other instanceof PlusOp;
    }

    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "+";
    }

    public <A, E extends Throwable> A accept(final OpVisitor<A, E> visitor) throws E {
        return visitor.visitPlusOp();
    } // accept
} // PlusOp
