package example.parser;

import java.util.List;

public class Program {
    public final List<Statement> statements;
    
    public Program(final List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public boolean equals(final Object obj) {
        return (obj instanceof Program &&
                statements.equals(((Program)obj).statements));
    }

    @Override
    public int hashCode() {
        return statements.hashCode();
    }

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer();
        for (final Statement statement : statements) {
            buffer.append(statement.toString());
            buffer.append('\n');
        }
        return buffer.toString();
    }
} // Program
