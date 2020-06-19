package example.typechecker;

import example.parser.*;

import java.util.Map;
import java.util.HashMap;

public class Typechecker {
    // ---BEGIN CONSTANTS---
    public static final Map<Op, OpDesc> OPERATORS;
    static {
        OPERATORS = new HashMap<Op, OpDesc>();
        final OpDesc intOp = new OpDesc(new IntType(),
                                        new IntType(),
                                        new IntType());
        final OpDesc boolOp = new OpDesc(new BoolType(),
                                         new BoolType(),
                                         new BoolType());
        OPERATORS.put(new PlusOp(), intOp);
        OPERATORS.put(new MinusOp(), intOp);
        OPERATORS.put(new MultiplyOp(), intOp);
        OPERATORS.put(new DivisionOp(), intOp);
        OPERATORS.put(new AndOp(), boolOp);
        OPERATORS.put(new OrOp(), boolOp);
    }
    // ---END CONSTANTS---
    
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

    public Type opType(final Type leftType,
                       final Op op,
                       final Type rightType) throws IllTypedException {
        final OpDesc expected = OPERATORS.get(op);
        if (expected.expectedLeft.equals(leftType) &&
            expected.expectedRight.equals(rightType)) {
            return expected.returnType;
        } else {
            throw new IllTypedException("Type error involving operator: " + op);
        }
    } // opType
                
    public Type typeOfExpression(final Expression expression) throws IllTypedException {
        class TypeOfExpressionVisitor implements ExpressionVisitor<Type, IllTypedException> {
            public Type visitIntegerExpression(final int value) throws IllTypedException {
                return new IntType();
            } // visitIntegerExpression
            
            public Type visitVariableExpression(final String name) throws IllTypedException {
                final Type type = variableType(name);
                if (type == null) {
                    throw new IllTypedException("variable not in scope: " + name);
                } else {
                    return type;
                }
            } // visitVariableExpression

            public Type visitBooleanExpression(final boolean value) throws IllTypedException {
                return new BoolType();
            } // visitBooleanExpression

            public Type visitOperatorExpression(final Expression e1,
                                                final Op op,
                                                final Expression e2) throws IllTypedException {
                final Type e1Type = typeOfExpression(e1);
                final Type e2Type = typeOfExpression(e2);
                return opType(e1Type, op, e2Type);
            } // visitOperatorExpression
        } // TypeOfExpressionVisitor

        return expression.accept(new TypeOfExpressionVisitor());
    } // typeOfExpression
} // Typechecker
