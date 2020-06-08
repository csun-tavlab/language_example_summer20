package example.tokenizer;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class TokenizerTest {
    public static void assertTokenizes(final String input,
                                       final Token[] expectedTokens)
        throws TokenizerException {
        final Tokenizer tokenizer = new Tokenizer(input);
        final Token[] receivedTokens = tokenizer.tokenize();
        assertArrayEquals(expectedTokens, receivedTokens);
    }
    
    // "" = []
    @Test
    public void testEmpty() throws TokenizerException {
        assertTokenizes("", new Token[0]);
    }

    // '123' = [123]
    @Test
    public void testSingleIntegerToken() throws TokenizerException {
        assertTokenizes("123", new Token[]{ new IntegerToken(123) });
    }

    // '+' = [+]
    @Test
    public void testPlus() throws TokenizerException {
        assertTokenizes("+", new Token[]{ new PlusToken() });
    }

    // '-' = [-]
    @Test
    public void testMinus() throws TokenizerException {
        assertTokenizes("-", new Token[]{ new MinusToken() });
    }

    // '*' = [*]
    @Test
    public void testMultiply() throws TokenizerException {
        assertTokenizes("*", new Token[]{ new MultiplyToken() });
    }

    // ' ' = []
    @Test
    public void testWhitespace() throws TokenizerException {
        assertTokenizes(" ", new Token[0]);
    }
}
