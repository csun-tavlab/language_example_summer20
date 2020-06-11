package example.tokenizer;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void testTryReadSingleDigitInteger() {
        final Tokenizer tokenizer = new Tokenizer("1");
        assertEquals(new IntegerToken(1), tokenizer.tryReadInteger());
    }

    @Test
    public void testTryReadMultiDigitInteger() {
        final Tokenizer tokenizer = new Tokenizer("123");
        assertEquals(new IntegerToken(123), tokenizer.tryReadInteger());
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

    // '/' = [/]
    @Test
    public void testDivision() throws TokenizerException {
        assertTokenizes("/", new Token[]{ new DivisionToken() });
    }

    // '&&' = [&&]
    @Test
    public void testAnd() throws TokenizerException {
        assertTokenizes("&&", new Token[]{ new AndToken() });
    }
    
    // '||' = [||]
    @Test
    public void testOr() throws TokenizerException {
        assertTokenizes("||", new Token[]{ new OrToken() });
    }

    // 'true' = [true]
    @Test
    public void testTrue() throws TokenizerException {
        assertTokenizes("true", new Token[]{ new BooleanToken(true) });
    }

    // 'false' = [false]
    @Test
    public void testFalse() throws TokenizerException {
        assertTokenizes("false", new Token[]{ new BooleanToken(false) });
    }
} // TokenizerTest
