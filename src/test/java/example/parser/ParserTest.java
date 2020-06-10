package example.parser;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import example.tokenizer.*;

public class ParserTest {    
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

    // 3 - 4
    @Test
    public void parseThreeMinusFour() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(3),
                                  new MinusToken(),
                                  new IntegerToken(4) },
            new OperatorExpression(new IntegerExpression(3),
                                   new MinusOp(),
                                   new IntegerExpression(4)));
    }
    
    // 5 * 6
    @Test
    public void parseFiveTimesSix() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(5),
                                  new MultiplyToken(),
                                  new IntegerToken(6) },
            new OperatorExpression(new IntegerExpression(5),
                                   new MultiplyOp(),
                                   new IntegerExpression(6)));
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

    // 1 / 2
    @Test
    public void parseOneDivTwo() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(1),
                                  new DivisionToken(),
                                  new IntegerToken(2) },
            new OperatorExpression(new IntegerExpression(1),
                                   new DivisionOp(),
                                   new IntegerExpression(2)));
    }
    
    // 1 + 2 / 3
    @Test
    public void parseOnePlusTwoDivThreeRight() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(1),
                                  new PlusToken(),
                                  new IntegerToken(2),
                                  new DivisionToken(),
                                  new IntegerToken(3) },
            new OperatorExpression(new IntegerExpression(1),
                                   new PlusOp(),
                                   new OperatorExpression(new IntegerExpression(2),
                                                          new DivisionOp(),
                                                          new IntegerExpression(3))));
    }
    
    // 1 / 2 + 3
    @Test
    public void parseOnePlusTwoDivThreeLeft() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(1),
                                  new DivisionToken(),
                                  new IntegerToken(2),
                                  new PlusToken(),
                                  new IntegerToken(3) },
            new OperatorExpression(new OperatorExpression(new IntegerExpression(1),
                                                          new DivisionOp(),
                                                          new IntegerExpression(2)),
                                   new PlusOp(),
                                   new IntegerExpression(3)));
    }

    // 1 && 2
    @Test
    public void parseOneAndTwo() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(1),
                                  new AndToken(),
                                  new IntegerToken(2) },
            new OperatorExpression(new IntegerExpression(1),
                                   new AndOp(),
                                   new IntegerExpression(2)));
    }

    // 3 || 4
    @Test
    public void parseThreeOrFour() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(3),
                                  new OrToken(),
                                  new IntegerToken(4) },
            new OperatorExpression(new IntegerExpression(3),
                                   new OrOp(),
                                   new IntegerExpression(4)));
    }

    // 1 || 2 && 3
    @Test
    public void testAndHigherThanOr() throws ParseException {
        assertParses(new Token[]{ new IntegerToken(1),
                                  new OrToken(),
                                  new IntegerToken(2),
                                  new AndToken(),
                                  new IntegerToken(3) },
            new OperatorExpression(new IntegerExpression(1),
                                   new OrOp(),
                                   new OperatorExpression(new IntegerExpression(2),
                                                          new AndOp(),
                                                          new IntegerExpression(3))));
    }

    // 1 || 2 && 3 * 4 + 5
    // 1 || (2 && ((3 * 4) + 5))
    @Test
    public void testAllPrecedence() throws ParseException {
        final Expression mult = new OperatorExpression(new IntegerExpression(3),
                                                       new MultiplyOp(),
                                                       new IntegerExpression(4));
        final Expression plus = new OperatorExpression(mult,
                                                       new PlusOp(),
                                                       new IntegerExpression(5));
        final Expression and = new OperatorExpression(new IntegerExpression(2),
                                                      new AndOp(),
                                                      plus);
        final Expression or = new OperatorExpression(new IntegerExpression(1),
                                                     new OrOp(),
                                                     and);
        assertParses(new Token[]{ new IntegerToken(1),
                                  new OrToken(),
                                  new IntegerToken(2),
                                  new AndToken(),
                                  new IntegerToken(3),
                                  new MultiplyToken(),
                                  new IntegerToken(4),
                                  new PlusToken(),
                                  new IntegerToken(5) },
            or);
    }
}
