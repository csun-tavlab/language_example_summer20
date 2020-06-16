package example.typechecker;

import example.parser.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Map;
import java.util.HashMap;

public class TypecheckerTest {
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
} // TypecheckerTest
