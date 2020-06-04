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
    
    public Token[] tokenize() throws TokenizerException {
        final List<Token> tokens = new ArrayList<Token>();
        position = 0;

        while (position < input.length()) {
            final char currentChar = input.charAt(position);
            if (currentChar == '+') {
                tokens.add(new PlusToken());
                position++;
            } else if (currentChar == '-') {
                tokens.add(new MinusToken());
                position++;
            } else if (currentChar == '*') {
                tokens.add(new MultiplyToken());
                position++;
            } else if (Character.isDigit(currentChar)) {
                tokens.add(tokenizeInteger());
            } else {
                throw new TokenizerException("Invalid character: " + currentChar);
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
