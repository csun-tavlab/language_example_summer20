package example.tokenizer;

public interface Token {
    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E;
} // Token
