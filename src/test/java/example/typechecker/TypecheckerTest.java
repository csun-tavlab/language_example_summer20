package example.typechecker;

import example.parser.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

import java.util.Map;
import java.util.HashMap;

public class TypecheckerTest {
    // ---BEGIN CONSTANTS---
    public static final Op[] ARITHMETIC_OPS = new Op[]{ new PlusOp(),
                                                        new MinusOp(),
                                                        new MultiplyOp(),
                                                        new DivisionOp() };
    public static final Op[] BOOLEAN_OPS = new Op[]{ new AndOp(),
                                                     new OrOp() };
    // ---END CONSTANTS---
    
    // 1: int
    @Test
    public void integerHasTypeInt() throws IllTypedException {
        final Typechecker typechecker = new Typechecker(new HashMap<String, Type>());
        final Type gotType = typechecker.typeOfExpression(new IntegerExpression(1));
        assertEquals(new IntType(), gotType);
    } // integerHasTypeInt

    // [x -> int] x: int
    @Test
    public void variableInScopeTypeInt() throws IllTypedException {
        final HashMap<String, Type> typeEnv = new HashMap<String, Type>();
        typeEnv.put("x", new IntType());
        final Typechecker typechecker = new Typechecker(typeEnv);
        final Type gotType = typechecker.typeOfExpression(new VariableExpression("x"));
        assertEquals(new IntType(), gotType);
    } // variableInScopeTypeInt

    // [y -> bool] y: bool
    @Test
    public void variableInScopeTypeBool() throws IllTypedException {
        final HashMap<String, Type> typeEnv = new HashMap<String, Type>();
        typeEnv.put("y", new BoolType());
        final Typechecker typechecker = new Typechecker(typeEnv);
        final Type gotType = typechecker.typeOfExpression(new VariableExpression("y"));
        assertEquals(new BoolType(), gotType);
    } // variableInScopeTypeBool

    // [] x: ill-typed
    @Test(expected = IllTypedException.class)
    public void noVariableInScopeInEmptyTypeEnvironment() throws IllTypedException {
        final Typechecker typechecker = new Typechecker(new HashMap<String, Type>());
        typechecker.typeOfExpression(new VariableExpression("x"));
    } // noVariableInScopeInEmptyTypeEnvironment

    // [x -> int] y: ill-typed
    @Test(expected = IllTypedException.class)
    public void variableNotInScope() throws IllTypedException {
        final HashMap<String, Type> typeEnv = new HashMap<String, Type>();
        typeEnv.put("x", new IntType());
        final Typechecker typechecker = new Typechecker(typeEnv);
        typechecker.typeOfExpression(new VariableExpression("y"));
    } // variableNotInScope

    // true: bool
    @Test
    public void trueIsBoolean() throws IllTypedException {
        final Typechecker typechecker = new Typechecker(new HashMap<String, Type>());
        final Type gotType = typechecker.typeOfExpression(new BooleanExpression(true));
        assertEquals(new BoolType(), gotType);
    } // trueIsBoolean

    // false: bool
    @Test
    public void falseIsBoolean() throws IllTypedException {
        final Typechecker typechecker = new Typechecker(new HashMap<String, Type>());
        final Type gotType = typechecker.typeOfExpression(new BooleanExpression(false));
        assertEquals(new BoolType(), gotType);
    } // falseIsBoolean

    // -2 types (int, bool)
    // -6 operators (+, -, *, /, &&, ||)
    // -each test has two input types and one operator
    // 2 * 6 * 2 combinations = 24
    
    // all else: ill-typed

    // int + int = int
    // int - int = int
    // int * int = int
    // int / int = int
    @Test
    public void testArithmeticOperations() throws IllTypedException {
        final Typechecker typechecker = new Typechecker(new HashMap<String, Type>());
        final Expression left = new IntegerExpression(1);
        final Expression right = new IntegerExpression(2);
        for (final Op op : ARITHMETIC_OPS) {
            final Expression input = new OperatorExpression(left, op, right);
            assertEquals(new IntType(), typechecker.typeOfExpression(input));
        }
    } // testArithmeticOperations

    // true + 1: ill-typed
    // true - 1: ill-typed
    // true * 1: ill-typed
    // true / 1: ill-typed
    @Test
    public void testArithmeticIllTypedLeft() {
        final Typechecker typechecker = new Typechecker(new HashMap<String, Type>());

        for (final Op op : ARITHMETIC_OPS) {
            final Expression exp = new OperatorExpression(new BooleanExpression(true),
                                                          op,
                                                          new IntegerExpression(1));
            try {
                typechecker.typeOfExpression(exp);
                fail("operator not ill-typed: " + op);
            } catch (final IllTypedException e) {}
        }
    } // testArithmeticIllTypedLeft

    // 1 + true: ill-typed
    // 1 - true: ill-typed
    // 1 * true: ill-typed
    // 1 / true: ill-typed
    @Test
    public void testArithmeticIllTypedRight() {
        final Typechecker typechecker = new Typechecker(new HashMap<String, Type>());

        for (final Op op : ARITHMETIC_OPS) {
            final Expression exp = new OperatorExpression(new IntegerExpression(1),
                                                          op,
                                                          new BooleanExpression(true));
            try {
                typechecker.typeOfExpression(exp);
                fail("operator not ill-typed: " + op);
            } catch (final IllTypedException e) {}
        }
    } // testArithmeticIllTypedRight

    // bool && bool = bool
    // bool || bool = bool
    @Test
    public void testBooleanOperations() throws IllTypedException {
        final Typechecker typechecker = new Typechecker(new HashMap<String, Type>());
        final Expression left = new BooleanExpression(true);
        final Expression right = new BooleanExpression(false);
        for (final Op op : BOOLEAN_OPS) {
            final Expression input = new OperatorExpression(left, op, right);
            assertEquals(new BoolType(), typechecker.typeOfExpression(input));
        }
    } // testBooleanOperations
} // TypecheckerTest
