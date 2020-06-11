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
        return parseOrExpression(startPos);
    } // parseExpression

    public ParseResult parseOrExpression(final int startPos) throws ParseException {
        // or ::= and ('||' and)*
        ParseResult result = parseAndExpression(startPos);

        try {
            while (result.nextPosition < tokens.length) {
                ensureTokenIs(result.nextPosition, new OrToken());
                final ParseResult nested = parseOrExpression(result.nextPosition + 1);
                result = new ParseResult(new OperatorExpression(result.exp,
                                                                new OrOp(),
                                                                nested.exp),
                                         nested.nextPosition);
            }
        } catch (final ParseException e) {}

        return result;
    } // parseOrExpression

    public ParseResult parseAndExpression(final int startPos) throws ParseException {
        // and ::= a ('&&' a)*
        ParseResult result = parseAdditiveExpression(startPos);

        try {
            while (result.nextPosition < tokens.length) {
                ensureTokenIs(result.nextPosition, new AndToken());
                final ParseResult nested = parseAdditiveExpression(result.nextPosition + 1);
                result = new ParseResult(new OperatorExpression(result.exp,
                                                                new AndOp(),
                                                                nested.exp),
                                         nested.nextPosition);
            }
        } catch (final ParseException e) {}

        return result;
    } // parseAndExpression

    public Op parseAdditiveOp(final int atPos) throws ParseException {
        final Token tokenHere = readToken(atPos);

        if (tokenHere instanceof PlusToken) {
            return new PlusOp();
        } else if (tokenHere instanceof MinusToken) {
            return new MinusOp();
        } else {
            throw new ParseException("Expected additive operator, got: " + tokenHere.toString());
        }
    } // parseAdditiveOp
    
    public ParseResult parseAdditiveExpression(final int startPos) throws ParseException {
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
    } // parseAdditiveExpression
    
    public void ensureTokenIs(final int atPos, final Token expectedToken) throws ParseException {
        if (!readToken(atPos).equals(expectedToken)) {
            throw new ParseException("Missing " + expectedToken.toString());
        }
    } // ensureTokenIs

    public Token readToken(final int atPos) throws ParseException {
        if (atPos < 0 || atPos >= tokens.length) {
            throw new ParseException("Ran out of tokens");
        } else {
            return tokens[atPos];
        }
    } // readToken

    public Op parseMultiplicativeOp(final int atPos) throws ParseException {
        final Token tokenHere = readToken(atPos);

        if (tokenHere instanceof MultiplyToken) {
            return new MultiplyOp();
        } else if (tokenHere instanceof DivisionToken) {
            return new DivisionOp();
        } else {
            throw new ParseException("Expected multiplicative operator, got: " + tokenHere.toString());
        }
    } // parseMultiplicativeOp
    
    public ParseResult parseMultiplicative(final int startPos) throws ParseException {
        // m ::= p ('*' p)*
        //                   initial p (goes on left)
        ParseResult result = parsePrimary(startPos);

        try {
            while (result.nextPosition < tokens.length) {
                final Op op = parseMultiplicativeOp(result.nextPosition);
                ParseResult nextPrimary = parsePrimary(result.nextPosition + 1);
                result = new ParseResult(new OperatorExpression(result.exp,
                                                                op,
                                                                nextPrimary.exp),
                                         nextPrimary.nextPosition);
            }
        } catch (final ParseException e) {}

        return result;
    } // parseMultiplicative
    
    public ParseResult parsePrimary(final int startPos) throws ParseException {
        final Token curToken = readToken(startPos);
        if (curToken instanceof IntegerToken) {
            final IntegerToken asInt = (IntegerToken)curToken;
            return new ParseResult(new IntegerExpression(asInt.value),
                                   startPos + 1);
        } else if (curToken instanceof VariableToken) {
            final VariableToken asVar = (VariableToken)curToken;
            return new ParseResult(new VariableExpression(asVar.name),
                                   startPos + 1);
        } else if (curToken instanceof BooleanToken) {
            final BooleanToken asBool = (BooleanToken)curToken;
            return new ParseResult(new BooleanExpression(asBool.value),
                                   startPos + 1);
        } else if (curToken instanceof LeftParenToken) {
            final ParseResult inner = parseExpression(startPos + 1);
            if (readToken(inner.nextPosition) instanceof RightParenToken) {
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
