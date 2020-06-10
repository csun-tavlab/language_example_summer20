package example.parser;

public class DivisionOp implements Op {
    @Override
    public boolean equals(final Object other) {
        return other instanceof DivisionOp;
    }

    @Override
    public int hashCode() {
        return 3;
    }

    @Override
    public String toString() {
        return "/";
    }
} // DivisionOp
