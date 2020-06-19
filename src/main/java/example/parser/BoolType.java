package example.parser;

public class BoolType implements Type {
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "bool";
    }

    public <A, E extends Throwable> A accept(final TypeVisitor<A, E> visitor) throws E {
        return visitor.visitBoolType();
    } // accept
} // BoolType
