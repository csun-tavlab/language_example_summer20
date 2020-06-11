package example.parser;

public class ParseResult<A> {
    public final A result;
    public final int nextPosition;

    public ParseResult(final A result,
                       final int nextPosition) {
        this.result = result;
        this.nextPosition = nextPosition;
    }
} // ParseResult
