package example.parser;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import example.tokenizer.*;

public class ParserTest {
    // 3 - 4
    // 5 * 6
    
    public static void assertParses(final Token[] tokens,
                                    final Expression expected) throws ParseException {
        final Parser parser = new Parser(tokens);
        assertEquals(expected, parser.parseExpression());
    } // assertParses

    // 1
    @Test
    public void parseInteger() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(1) },
                     new IntegerExpression(1));
    }

    // (2)
    @Test
    public void parseParens() throws ParseException {
        assertParses(new Token[]{ new LeftParenToken(),
                                  new IntegerToken(2),
                                  new RightParenToken() },
            new IntegerExpression(2));
    }
    
    // 1 + 2
    @Test
    public void parseOnePlusTwo() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(1),
                                  new PlusToken(),
                                  new IntegerToken(2) },
            new OperatorExpression(new IntegerExpression(1),
                                   new PlusOp(),
                                   new IntegerExpression(2)));
    }

    // 1 + 2 * 3  // 1 + (2 * 3)
    @Test
    public void parsePrecidenceRight() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(1),
                                  new PlusToken(),
                                  new IntegerToken(2),
                                  new MultiplyToken(),
                                  new IntegerToken(3) },
            new OperatorExpression(new IntegerExpression(1),
                                   new PlusOp(),
                                   new OperatorExpression(new IntegerExpression(2),
                                                          new MultiplyOp(),
                                                          new IntegerExpression(3))));
    }
    
    // 1 * 2 + 3  // (1 * 2) + 3
    @Test
    public void parsePrecidenceLeft() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(1),
                                  new MultiplyToken(),
                                  new IntegerToken(2),
                                  new PlusToken(),
                                  new IntegerToken(3) },
            new OperatorExpression(new OperatorExpression(new IntegerExpression(1),
                                                          new MultiplyOp(),
                                                          new IntegerExpression(2)),
                                   new PlusOp(),
                                   new IntegerExpression(3)));
    }

    // 1 +
    @Test(expected = ParseException.class)
    public void throwErrorOnPlusWithoutRightSide() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(1),
                                  new PlusToken() },
            null);
    }
}
