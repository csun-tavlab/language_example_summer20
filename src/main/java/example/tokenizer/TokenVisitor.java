package example.tokenizer;

public interface TokenVisitor<A, E extends Throwable> {
    public A visitIntTypeToken() throws E;
    public A visitBoolTypeToken() throws E;
    public A visitEqualsToken() throws E;
    public A visitSemicolonToken() throws E;
    public A visitPlusToken() throws E;
    public A visitMinusToken() throws E;
    public A visitMultiplyToken() throws E;
    public A visitDivisionToken() throws E;
    public A visitAndToken() throws E;
    public A visitOrToken() throws E;
    public A visitLeftParenToken() throws E;
    public A visitRightParenToken() throws E;
    public A visitIntegerToken(final int value) throws E;
    public A visitVariableToken(final String name) throws E;
    public A visitBooleanToken(final boolean value) throws E;
} // TokenVisitor
