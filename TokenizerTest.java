public class TokenizerTest {
    // "" = []
    public static void testEmpty() throws TokenizerException {
        final Tokenizer tokenizer = new Tokenizer("");
        final Token[] tokens = tokenizer.tokenize();
        assert(tokens.length == 0);
    }

    // '123' = [123]
    public static void testSingleIntegerToken() throws TokenizerException {
        final Tokenizer tokenizer = new Tokenizer("123");
        final Token[] tokens = tokenizer.tokenize();
        assert(tokens.length == 1);
        assert(tokens[0].equals(new IntegerToken(123)));
    }
    
    public static void main(final String[] args) throws TokenizerException {
        testEmpty();
        testSingleIntegerToken();
    }
}
