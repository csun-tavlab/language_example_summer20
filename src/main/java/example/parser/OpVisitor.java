package example.parser;

public interface OpVisitor<A, E extends Throwable> {
    public A visitPlusOp() throws E;
    public A visitMinusOp() throws E;
    public A visitMultiplyOp() throws E;
    public A visitDivisionOp() throws E;
    public A visitAndOp() throws E;
    public A visitOrOp() throws E;
} // OpVisitor
