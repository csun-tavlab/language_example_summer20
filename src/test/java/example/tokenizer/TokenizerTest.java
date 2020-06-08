package example.tokenizer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TokenizerTest {
    // "" = []
    @Test
    public void testEmpty() throws TokenizerException {
        final Tokenizer tokenizer = new Tokenizer("");
        final Token[] tokens = tokenizer.tokenize();
        // assertEquals(expectedValue, givenValue)
        assertEquals(0, tokens.length);
    }

    // '123' = [123]
    @Test
    public void testSingleIntegerToken() throws TokenizerException {
        final Tokenizer tokenizer = new Tokenizer("123");
        final Token[] tokens = tokenizer.tokenize();
        assertEquals(1, tokens.length);
        assertEquals(new IntegerToken(123), tokens[0]);
    }
}
