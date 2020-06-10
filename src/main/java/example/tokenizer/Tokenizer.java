package example.tokenizer;

import java.util.List;
import java.util.ArrayList;

public class Tokenizer {    
    // ---BEGIN INSTANCE VARIABLES---
    public final String input;
    private int position;
    // ---END INSTANCE VARIABLES---

    public Tokenizer(final String input) {
        this.input = input;
        position = 0;
    }

    private void skipWhitespace() {
        while (position < input.length() &&
               Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    } // skipWhitespace
    
    // assumes position starts on a digit
    private IntegerToken tokenizeInteger() {
        assert(position < input.length() &&
               Character.isDigit(input.charAt(position)));
        final StringBuffer digits = new StringBuffer();
        digits.append(input.charAt(position));
        position++;
        while (position < input.length() &&
               Character.isDigit(input.charAt(position))) {
            digits.append(input.charAt(position));
            position++;
        }
        final String digitsAsString = digits.toString();
        final int asInteger = Integer.parseInt(digitsAsString);
        return new IntegerToken(asInteger);
    } // tokenizeInteger

    // returns true if the input from the current position matches
    // this string.  Increments the position if it does match.
    // returns false otherwise, and will not change the position
    private boolean inputPrefixMatches(final String prefix) {
        if (input.startsWith(prefix, position)) {
            position += prefix.length();
            return true;
        } else {
            return false;
        }
    } // inputPrefixMatches

    // returns a read in non-integer token, and increments position accordingly.
    // returns null if there isn't an operator token here.
    public Token tryReadNonIntToken() {
        if (inputPrefixMatches("+")) {
            return new PlusToken();
        } else if (inputPrefixMatches("-")) {
            return new MinusToken();
        } else if (inputPrefixMatches("*")) {
            return new MultiplyToken();
        } else if (inputPrefixMatches("/")) {
            return new DivisionToken();
        } else if (inputPrefixMatches("&&")) {
            return new AndToken();
        } else if (inputPrefixMatches("||")) {
            return new OrToken();
        } else if (inputPrefixMatches("(")) {
            return new LeftParenToken();
        } else if (inputPrefixMatches(")")) {
            return new RightParenToken();
        } else {
            return null;
        }
    } // tryReadNonIntToken
    
    public Token[] tokenize() throws TokenizerException {
        final List<Token> tokens = new ArrayList<Token>();
        position = 0;

        while (position < input.length()) {
            skipWhitespace();
            if (position < input.length()) {
                final Token maybeTokenHere = tryReadNonIntToken();
                if (maybeTokenHere != null) {
                    tokens.add(maybeTokenHere);
                } else if (Character.isDigit(input.charAt(position))) {
                    tokens.add(tokenizeInteger());
                } else {
                    throw new TokenizerException("Invalid character: " + input.charAt(position));
                }
            }
        }

        return tokens.toArray(new Token[tokens.size()]);
    } // tokenize

    public static void main(final String[] args) throws TokenizerException {
        if (args.length != 1) {
            System.out.println("Needs a string to tokenize");
        } else {
            final Tokenizer tokenizer = new Tokenizer(args[0]);
            final Token[] tokens = tokenizer.tokenize();
            for (final Token token : tokens) {
                System.out.println(token.toString());
            }
        }
    }
}
