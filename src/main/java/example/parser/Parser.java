package example.parser;

import example.tokenizer.*;

public class Parser {
    private final Token[] tokens;

    public Parser(final Token[] tokens) {
        this.tokens = tokens;
    }

    public Expression parseExpression() throws ParseException {
        final ParseResult result = parseExpression(0);
        if (result.nextPosition == tokens.length) {
            return result.exp;
        } else {
            throw new ParseException("Extra tokens at end");
        }
    } // parseExpression
    
    public ParseResult parseExpression(final int startPos) throws ParseException {
        return parseAdditive(startPos);
    } // parseExpression

    public Op parseAdditiveOp(final int atPos) throws ParseException {
        final Token tokenHere = tokens[atPos];

        if (tokenHere instanceof PlusToken) {
            return new PlusOp();
        } else if (tokenHere instanceof MinusToken) {
            return new MinusOp();
        } else {
            throw new ParseException("Expected additive operator, got: " + tokenHere.toString());
        }
    } // parseAdditiveOp
    
    public ParseResult parseAdditive(final int startPos) throws ParseException {
        // a ::= m (('+' | '-') m)*
        ParseResult result = parseMultiplicative(startPos);

        try {
            while (result.nextPosition < tokens.length) {
                final Op op = parseAdditiveOp(result.nextPosition);
                final ParseResult innerMultiplicative = parseMultiplicative(result.nextPosition + 1);
                result = new ParseResult(new OperatorExpression(result.exp,
                                                                op,
                                                                innerMultiplicative.exp),
                                         innerMultiplicative.nextPosition);
            }
        } catch (final ParseException e) {}

        return result;
    } // parseAdditive
    
    public void ensureTokenIs(final int atPos, final Token expectedToken) throws ParseException {
        if (!tokens[atPos].equals(expectedToken)) {
            throw new ParseException("Missing " + expectedToken.toString());
        }
    } // ensureTokenIs
    
    public ParseResult parseMultiplicative(final int startPos) throws ParseException {
        // m ::= p ('*' p)*
        //                   initial p (goes on left)
        ParseResult result = parsePrimary(startPos);

        try {
            while (result.nextPosition < tokens.length) {
                ensureTokenIs(result.nextPosition, new MultiplyToken());
                //                        one nested p
                ParseResult nextPrimary = parsePrimary(result.nextPosition + 1);
                result = new ParseResult(new OperatorExpression(result.exp,
                                                                new MultiplyOp(),
                                                                nextPrimary.exp),
                                         nextPrimary.nextPosition);
            }
        } catch (final ParseException e) {}

        return result;
    } // parseMultiplicative
    
    public ParseResult parsePrimary(final int startPos) throws ParseException {
        if (tokens[startPos] instanceof IntegerToken) {
            final IntegerToken asInt = (IntegerToken)tokens[startPos];
            return new ParseResult(new IntegerExpression(asInt.value),
                                   startPos + 1);
        } else if (tokens[startPos] instanceof LeftParenToken) {
            final ParseResult inner = parseExpression(startPos + 1);
            if (tokens[inner.nextPosition] instanceof RightParenToken) {
                return new ParseResult(inner.exp,
                                       inner.nextPosition + 1);
            } else {
                throw new ParseException("Missing right parenthesis");
            }
        } else {
            throw new ParseException("Not a primary expression");
        }
    } // parsePrimary
}
