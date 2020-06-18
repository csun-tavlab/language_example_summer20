package example.tokenizer;

public class MultiplyToken implements Token {
    @Override
    public String toString() {
        return "*";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof MultiplyToken;
    }

    @Override
    public int hashCode() {
        return 2;
    }

    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E {
        return visitor.visitMultiplyToken();
    }
}
