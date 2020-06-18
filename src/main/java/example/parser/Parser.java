package example.parser;

import java.util.List;
import java.util.ArrayList;

import example.tokenizer.*;

public class Parser {
    private final Token[] tokens;

    public Parser(final Token[] tokens) {
        this.tokens = tokens;
    }

    public Expression parseExpression() throws ParseException {
        final ParseResult<Expression> result = parseExpression(0);
        if (result.nextPosition == tokens.length) {
            return result.result;
        } else {
            throw new ParseException("Extra tokens at end");
        }
    } // parseExpression
    
    public ParseResult<Expression> parseExpression(final int startPos) throws ParseException {
        return parseOrExpression(startPos);
    } // parseExpression

    public ParseResult<Expression> parseOrExpression(final int startPos) throws ParseException {
        // or ::= and ('||' and)*
        ParseResult<Expression> result = parseAndExpression(startPos);

        try {
            while (result.nextPosition < tokens.length) {
                ensureTokenIs(result.nextPosition, new OrToken());
                final ParseResult<Expression> nested = parseOrExpression(result.nextPosition + 1);
                result = new ParseResult<Expression>(new OperatorExpression(result.result,
                                                                            new OrOp(),
                                                                            nested.result),
                                                     nested.nextPosition);
            }
        } catch (final ParseException e) {}

        return result;
    } // parseOrExpression

    public ParseResult<Expression> parseAndExpression(final int startPos) throws ParseException {
        // and ::= a ('&&' a)*
        ParseResult<Expression> result = parseAdditiveExpression(startPos);

        try {
            while (result.nextPosition < tokens.length) {
                ensureTokenIs(result.nextPosition, new AndToken());
                final ParseResult<Expression> nested = parseAdditiveExpression(result.nextPosition + 1);
                result = new ParseResult<Expression>(new OperatorExpression(result.result,
                                                                            new AndOp(),
                                                                            nested.result),
                                                     nested.nextPosition);
            }
        } catch (final ParseException e) {}

        return result;
    } // parseAndExpression

    public Op parseAdditiveOp(final int atPos) throws ParseException {
        final Token tokenHere = readToken(atPos);

        class AdditiveOpVisitor extends DefaultTokenVisitor<Op, ParseException> {
            public Op defaultAction() throws ParseException {
                throw new ParseException("expected additive operator, got: " + tokenHere);
            }
            @Override
            public Op visitPlusToken() throws ParseException {
                return new PlusOp();
            }
            @Override
            public Op visitMinusToken() throws ParseException {
                return new MinusOp();
            }
        }

        return tokenHere.accept(new AdditiveOpVisitor());
    } // parseAdditiveOp
        
    public ParseResult<Expression> parseAdditiveExpression(final int startPos) throws ParseException {
        // a ::= m (('+' | '-') m)*
        ParseResult<Expression> result = parseMultiplicative(startPos);

        try {
            while (result.nextPosition < tokens.length) {
                final Op op = parseAdditiveOp(result.nextPosition);
                final ParseResult<Expression> innerMultiplicative = parseMultiplicative(result.nextPosition + 1);
                result = new ParseResult<Expression>(new OperatorExpression(result.result,
                                                                            op,
                                                                            innerMultiplicative.result),
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
    
    public ParseResult<Expression> parseMultiplicative(final int startPos) throws ParseException {
        // m ::= p ('*' p)*
        //                   initial p (goes on left)
        ParseResult<Expression> result = parsePrimary(startPos);

        try {
            while (result.nextPosition < tokens.length) {
                final Op op = parseMultiplicativeOp(result.nextPosition);
                ParseResult<Expression> nextPrimary = parsePrimary(result.nextPosition + 1);
                result = new ParseResult<Expression>(new OperatorExpression(result.result,
                                                                            op,
                                                                            nextPrimary.result),
                                                     nextPrimary.nextPosition);
            }
        } catch (final ParseException e) {}

        return result;
    } // parseMultiplicative
    
    public ParseResult<Expression> parsePrimary(final int startPos) throws ParseException {
        final Token curToken = readToken(startPos);
        if (curToken instanceof IntegerToken) {
            final IntegerToken asInt = (IntegerToken)curToken;
            return new ParseResult<Expression>(new IntegerExpression(asInt.value),
                                               startPos + 1);
        } else if (curToken instanceof VariableToken) {
            final VariableToken asVar = (VariableToken)curToken;
            return new ParseResult<Expression>(new VariableExpression(asVar.name),
                                               startPos + 1);
        } else if (curToken instanceof BooleanToken) {
            final BooleanToken asBool = (BooleanToken)curToken;
            return new ParseResult<Expression>(new BooleanExpression(asBool.value),
                                               startPos + 1);
        } else if (curToken instanceof LeftParenToken) {
            final ParseResult<Expression> inner = parseExpression(startPos + 1);
            if (readToken(inner.nextPosition) instanceof RightParenToken) {
                return new ParseResult<Expression>(inner.result,
                                                   inner.nextPosition + 1);
            } else {
                throw new ParseException("Missing right parenthesis");
            }
        } else {
            throw new ParseException("Not a primary expression");
        }
    } // parsePrimary

    public ParseResult<Type> parseType(final int startPos) throws ParseException {
        final Token curToken = readToken(startPos);
        if (curToken instanceof IntTypeToken) {
            return new ParseResult<Type>(new IntType(), startPos + 1);
        } else if (curToken instanceof BoolTypeToken) {
            return new ParseResult<Type>(new BoolType(), startPos + 1);
        } else {
            throw new ParseException("Expected type; got: " + curToken);
        }
    } // parseType

    public ParseResult<String> parseVariable(final int startPos) throws ParseException {
        final Token curToken = readToken(startPos);
        if (curToken instanceof VariableToken) {
            return new ParseResult<String>(((VariableToken)curToken).name, startPos + 1);
        } else {
            throw new ParseException("Expected variable; got: " + curToken);
        }
    } // parseVariable
    
    public ParseResult<Statement> parseStatement(final int startPos) throws ParseException {
        // currently this is hard-coded for variable declaration/initialization statements, because
        // this is the only kind of statement we have
        // s ::= t x '=' e ';'

        final ParseResult<Type> type = parseType(startPos);
        final ParseResult<String> variableName = parseVariable(type.nextPosition);
        ensureTokenIs(variableName.nextPosition, new EqualsToken());
        final ParseResult<Expression> expression = parseExpression(variableName.nextPosition + 1);
        ensureTokenIs(expression.nextPosition, new SemicolonToken());
        return new ParseResult<Statement>(new VariableDeclarationInitializationStatement(type.result,
                                                                                         variableName.result,
                                                                                         expression.result),
                                          expression.nextPosition + 1);
    } // parseStatement

    public Program parseProgram() throws ParseException {
        final List<Statement> statements = new ArrayList<Statement>();
        int curPos = 0;
        
        while (curPos < tokens.length) {
            final ParseResult<Statement> curStatement = parseStatement(curPos);
            statements.add(curStatement.result);
            curPos = curStatement.nextPosition;
        }

        return new Program(statements);
    } // parseProgram
}
