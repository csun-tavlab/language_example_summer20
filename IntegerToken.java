public class IntegerToken implements Token {
    public final int value;

    public IntegerToken(final int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
