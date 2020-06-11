package example.parser;

public class IntType implements Type {
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "int";
    }
} // IntType
