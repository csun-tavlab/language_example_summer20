package example.tokenizer;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Tokenizer {
    // ---BEGIN CONSTANTS---
    public static final Map<String, Token> OPERATIONS;
    public static final Map<String, Token> RESERVED_WORDS;
    static {
        // try operations in a specific order, in case one is a prefix
        // of another
        OPERATIONS = new LinkedHashMap<String, Token>();
        OPERATIONS.put("+", new PlusToken());
        OPERATIONS.put("-", new MinusToken());
        OPERATIONS.put("*", new MultiplyToken());
        OPERATIONS.put("/", new DivisionToken());
        OPERATIONS.put("&&", new AndToken());
        OPERATIONS.put("||", new OrToken());
        OPERATIONS.put("(", new LeftParenToken());
        OPERATIONS.put(")", new RightParenToken());
        OPERATIONS.put("=", new EqualsToken());
        OPERATIONS.put(";", new SemicolonToken());
        
        RESERVED_WORDS = new HashMap<String, Token>();
        RESERVED_WORDS.put("true", new BooleanToken(true));
        RESERVED_WORDS.put("false", new BooleanToken(false));
        RESERVED_WORDS.put("int", new IntTypeToken());
        RESERVED_WORDS.put("bool", new BoolTypeToken());
    }
    // ---END CONSTANTS---
    
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

    // returns the tokenized integer and updates the position
    // accordingly, or returns null if it wasn't an integer
    public IntegerToken tryReadInteger() {
        final StringBuffer digits = new StringBuffer();

        while (position < input.length() &&
               Character.isDigit(input.charAt(position))) {
            digits.append(input.charAt(position));
            position++;
        }

        final String digitsAsString = digits.toString();
        if (digitsAsString.length() != 0) {
            final int asInteger = Integer.parseInt(digitsAsString);
            return new IntegerToken(asInteger);
        } else {
            return null;
        }
    } // tryReadInteger

    // returns true if the input from the current position matches
    // this string.  Increments the position if it does match.
    // returns false otherwise, and will not change the position
    public boolean inputPrefixMatches(final String prefix) {
        if (input.startsWith(prefix, position)) {
            position += prefix.length();
            return true;
        } else {
            return false;
        }
    } // inputPrefixMatches

    // returns the read-in reserved word or variable token,
    // and updates the position accordingly.  Will return null
    // and leave position unchanged if it couldn't read a variable
    // or reserved word.
    public Token tryReadReservedWordOrVariable() {
        // start with a letter
        if (position < input.length() &&
            Character.isLetter(input.charAt(position))) {
            final StringBuffer characters = new StringBuffer();

            characters.append(input.charAt(position));
            position++;

            // followed by letters or digits
            while (position < input.length() &&
                   Character.isLetterOrDigit(input.charAt(position))) {
                characters.append(input.charAt(position));
                position++;
            }

            final String readIn = characters.toString();
            final Token reservedWord = RESERVED_WORDS.get(readIn);
            if (reservedWord != null) {
                return reservedWord;
            } else {
                return new VariableToken(readIn);
            }
        } else {
            return null;
        }
    } // tryReadReservedWordOrVariable
    
    // returns a read in an operation token, and increments position accordingly.
    // returns null if there isn't an operator token here.
    public Token tryReadOperationToken() {
        for (final Map.Entry<String, Token> entry : OPERATIONS.entrySet()) {
            if (inputPrefixMatches(entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    } // tryReadOperationToken
    
    public Token[] tokenize() throws TokenizerException {
        final List<Token> tokens = new ArrayList<Token>();
        position = 0;

        // skip initial whitespace
        skipWhitespace();
        
        while (position < input.length()) {
            Token curToken = null;
            if ((curToken = tryReadReservedWordOrVariable()) == null &&
                (curToken = tryReadOperationToken()) == null &&
                (curToken = tryReadInteger()) == null) {
                throw new TokenizerException("Failed to tokenize at position: " + position);
            } else {
                tokens.add(curToken);
                // start next token on non-whitespace
                skipWhitespace();
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
