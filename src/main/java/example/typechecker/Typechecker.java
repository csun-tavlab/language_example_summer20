package example.typechecker;

import example.parser.*;

import java.util.Map;
import java.util.HashMap;

public class Typechecker {
    // ---BEGIN INSTANCE VARIABLES---
    private final Map<String, Type> typeEnv;
    // ---END INSTANCE VARIABLES---

    public Typechecker() {
        this(new HashMap<String, Type>());
    } // Typechecker
    
    public Typechecker(final Map<String, Type> typeEnv) {
        this.typeEnv = typeEnv;
    } // Typechecker

    // returns the type of the given variable, or null
    // if it's not in scope
    public Type variableType(final String variable) {
        return typeEnv.get(variable);
    } // variableType
    
    public void isWellTyped(final Program program) throws IllTypedException {
        for (final Statement statement : program.statements) {
            isWellTyped(statement);
        }
    } // isWellTyped(Program)

    public void isWellTyped(final Statement statement) throws IllTypedException {
        // Object is just a dummy placeholder.  We have to return something in the visitor,
        // but the only thing we care about is whether or not it threw an exception.
        class WellTypedStatementVisitor implements StatementVisitor<Object, IllTypedException> {
            public Object visitVariableDeclarationInitializationStatement(final Type type,
                                                                          final String variableName,
                                                                          final Expression expression) throws IllTypedException {
                if (typeOfExpression(expression).equals(type)) {
                    typeEnv.put(variableName, type);
                    return null;
                } else {
                    throw new IllTypedException("Variable type mismatch on initialization: " + variableName);
                }
            } // visitVariableDeclarationInitializationStatement
        } // WellTypedStatementVisitor

        statement.accept(new WellTypedStatementVisitor());
    } // isWellTyped(Statement)

    // typeOfExpression(1) // IntType
    // typeOfExpression(true) // BoolType
    public Type typeOfExpression(final Expression expression) throws IllTypedException {
        if (expression instanceof IntegerExpression) {
            return new IntType();
        } else if (expression instanceof VariableExpression) {
            final String variableName = ((VariableExpression)expression).name;
            final Type variableType = variableType(variableName);
            if (variableType == null) {
                throw new IllTypedException("variable not in scope: " + variableName);
            } else {
                return variableType;
            }
        } else if (expression instanceof BooleanExpression) {
            return new BoolType();
        } else if (expression instanceof OperatorExpression) {
            final OperatorExpression asOp = (OperatorExpression)expression;
            final Type e1Type = typeOfExpression(asOp.e1);
            final Type e2Type = typeOfExpression(asOp.e2);
            final Op op = asOp.op;
            if ((op instanceof PlusOp ||
                 op instanceof MinusOp ||
                 op instanceof MultiplyOp ||
                 op instanceof DivisionOp) && // int (+|-|*|/) int = int
                e1Type instanceof IntType &&
                e2Type instanceof IntType) {
                return new IntType();
            } else if ((op instanceof AndOp || // bool (&& | ||) bool = bool
                        op instanceof OrOp) &&
                       e1Type instanceof BoolType &&
                       e2Type instanceof BoolType) {
                return new BoolType();
            } else {
                throw new IllTypedException("Type error involving operator: " + op);
            }
        } else {
            throw new IllTypedException("Unrecognized expression: " + expression);
        }
    }
}
