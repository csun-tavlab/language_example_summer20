package example.parser;

public class ParseResult {
    public final Expression exp;
    public final int nextPosition;

    public ParseResult(final Expression exp,
                       final int nextPosition) {
        this.exp = exp;
        this.nextPosition = nextPosition;
    }
} // ParseResult
