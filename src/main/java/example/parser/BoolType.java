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
} // BoolType
