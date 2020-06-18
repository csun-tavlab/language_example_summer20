package example.tokenizer;

public class IntegerToken implements Token {
    public final int value;

    public IntegerToken(final int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(final Object obj) {
        // return (obj instanceof IntegerToken &&
        //         value == ((IntegerToken)obj).value);
        if (obj instanceof IntegerToken) {
            final IntegerToken otherInteger = (IntegerToken)obj;
            return value == otherInteger.value;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value;
    }

    public <A, E extends Throwable> A accept(final TokenVisitor<A, E> visitor) throws E {
        return visitor.visitIntegerToken(value);
    }
}
