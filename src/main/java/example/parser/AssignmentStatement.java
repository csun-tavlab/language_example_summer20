package example.parser;

public class AssignmentStatement implements Statement {
    public final Type type;
    public final String variableName;
    public final Expression expression;

    public AssignmentStatement(final Type type,
                               final String variableName,
                               final Expression expression) {
        this.type = type;
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof AssignmentStatement) {
            final AssignmentStatement asAssign = (AssignmentStatement)obj;
            return (type.equals(asAssign.type) &&
                    variableName.equals(asAssign.variableName) &&
                    expression.equals(asAssign.expression));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (type.hashCode() +
                variableName.hashCode() +
                expression.hashCode());
    }

    @Override
    public String toString() {
        return (type.toString() +
                " " +
                variableName +
                " = " +
                expression.toString() +
                ";");
    }
} // AssignmentStatement
