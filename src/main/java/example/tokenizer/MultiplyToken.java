package example.tokenizer;

public class MultiplyToken implements Token {
    @Override
    public String toString() {
        return "*";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof MultiplyToken;
    }

    @Override
    public int hashCode() {
        return 2;
    }
}
