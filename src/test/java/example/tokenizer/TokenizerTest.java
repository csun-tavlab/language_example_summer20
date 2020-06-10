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

    // '123+'
    @Test
    public void testIntegerPlus() throws TokenizerException {
        final Token[] expected =
            new Token[] { new IntegerToken(123),
                          new PlusToken() };
        assertTokenizes("123+", expected);
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

    // '$' = exception
    @Test(expected = TokenizerException.class)
    public void testInvalidCharacter() throws TokenizerException {
        assertTokenizes("$", null);
    }

    // '(' = [(]
    @Test
    public void testLeftParen() throws TokenizerException {
        assertTokenizes("(", new Token[]{ new LeftParenToken() });
    }

    // ')' = [)]
    @Test
    public void testRightParen() throws TokenizerException {
        assertTokenizes(")", new Token[]{ new RightParenToken() });
    }
}
