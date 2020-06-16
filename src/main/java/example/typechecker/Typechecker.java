package example.typechecker;

import example.parser.*;

import java.util.Map;
import java.util.HashMap;

public class Typechecker {
    // ---BEGIN INSTANCE VARIABLES---
    private final Map<String, Type> typeEnv;
    // ---END INSTANCE VARIABLES---

    public Typechecker(final Map<String, Type> typeEnv) {
        this.typeEnv = typeEnv;
    } // Typechecker
    
    public void isWellTyped(final Program program) throws IllTypedException {
        for (final Statement statement : program.statements) {
            isWellTyped(statement);
        }
    } // isWellTyped(Program)

    public void isWellTyped(final Statement statement) throws IllTypedException {
        if (statement instanceof VariableDeclarationInitializationStatement) {
            final VariableDeclarationInitializationStatement asDec =
                (VariableDeclarationInitializationStatement)statement;
            if (typeOfExpression(asDec.expression).equals(asDec.type)) {
                typeEnv.put(asDec.variableName, asDec.type);
            } else {
                throw new IllTypedException("Variable type mismatch on initialization: " + asDec.variableName);
            }
        } else {
            throw new IllTypedException("Unrecognized statement: " + statement);
        }
    } // isWellTyped(Statement)

    // typeOfExpression(1) // IntType
    // typeOfExpression(true) // BoolType
    public Type typeOfExpression(final Expression expression) throws IllTypedException {
        if (expression instanceof IntegerExpression) {
            return new IntType();
        } else if (expression instanceof VariableExpression) {
            final String variableName = ((VariableExpression)expression).name;
            final Type variableType = typeEnv.get(variableName);
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
