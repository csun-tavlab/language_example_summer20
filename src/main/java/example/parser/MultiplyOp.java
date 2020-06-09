package example.parser;

public class MultiplyOp implements Op {
    @Override
    public boolean equals(final Object other) {
        return other instanceof MultiplyOp;
    }

    @Override
    public int hashCode() {
        return 2;
    }
    
    @Override
    public String toString() {
        return "*";
    }
} // MultiplyOp
