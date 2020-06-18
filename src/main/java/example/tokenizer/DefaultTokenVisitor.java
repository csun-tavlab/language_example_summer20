package example.tokenizer;

public abstract class DefaultTokenVisitor<A, E extends Throwable> implements TokenVisitor<A, E> {
    public abstract A defaultAction() throws E;

    public A visitIntTypeToken() throws E {
        return defaultAction();
    }
    public A visitBoolTypeToken() throws E {
        return defaultAction();
    }
    public A visitEqualsToken() throws E {
        return defaultAction();
    }
    public A visitSemicolonToken() throws E {
        return defaultAction();
    }
    public A visitPlusToken() throws E {
        return defaultAction();
    }
    public A visitMinusToken() throws E {
        return defaultAction();
    }
    public A visitMultiplyToken() throws E {
        return defaultAction();
    }
    public A visitDivisionToken() throws E {
        return defaultAction();
    }
    public A visitAndToken() throws E {
        return defaultAction();
    }
    public A visitOrToken() throws E {
        return defaultAction();
    }
    public A visitLeftParenToken() throws E {
        return defaultAction();
    }
    public A visitRightParenToken() throws E {
        return defaultAction();
    }
    public A visitIntegerToken(final int value) throws E {
        return defaultAction();
    }
    public A visitVariableToken(final String name) throws E {
        return defaultAction();
    }
    public A visitBooleanToken(final boolean value) throws E {
        return defaultAction();
    }
} // DefaultTokenVisitor

