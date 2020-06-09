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
} // PlusOp
