package example.code_generator;

import example.parser.*;

public class CodeGenerator {
    // ---BEGIN INSTANCE VARIABLES---
    private final StringBuffer buffer;
    // ---END INSTANCE VARIABLES---

    public CodeGenerator() {
        buffer = new StringBuffer();
    } // CodeGenerator

    public String wholeProgramString() {
        return buffer.toString();
    } // wholeProgramString

    public void writeOp(final Op op) throws CodeGeneratorException {
        buffer.append(op.toString());
    } // writeOp
            
    public void writeExpression(final Expression expression) throws CodeGeneratorException {
        if (expression instanceof IntegerExpression) {
            final IntegerExpression asInt = (IntegerExpression)expression;
            buffer.append(asInt.value);
        } else if (expression instanceof VariableExpression) {
            final VariableExpression asVar = (VariableExpression)expression;
            buffer.append(asVar.name);
        } else if (expression instanceof BooleanExpression) {
            final BooleanExpression asBool = (BooleanExpression)expression;
            buffer.append(asBool.value);
        } else if (expression instanceof OperatorExpression) {
            // e1 op e2
            // (e1 op e2)
            final OperatorExpression asOp = (OperatorExpression)expression;
            buffer.append("(");
            writeExpression(asOp.e1);
            buffer.append(" ");
            writeOp(asOp.op);
            buffer.append(" ");
            writeExpression(asOp.e2);
            buffer.append(")");
        } else {
            assert(false);
            throw new CodeGeneratorException("Unrecognized expression: " + expression);
        }
    } // writeExpression

    public void writeType(final Type type) throws CodeGeneratorException {
        if (type instanceof IntType) {
            buffer.append("int");
        } else if (type instanceof BoolType) {
            buffer.append("bool");
        } else {
            assert(false);
            throw new CodeGeneratorException("Unrecognized type: " + type);
        }
    } // writeType
    
    public void writeStatement(final Statement statement) throws CodeGeneratorException {
        if (statement instanceof VariableDeclarationInitializationStatement) {
            // s ::= t x '=' e ';'
            // int x = 7;
            // let x = 7;

            // bool y = true;
            // let y = true; // bool
            final VariableDeclarationInitializationStatement asDec =
                (VariableDeclarationInitializationStatement)statement;
            buffer.append("let ");
            buffer.append(asDec.variableName);
            buffer.append(" = ");
            writeExpression(asDec.expression);
            buffer.append("; // ");
            writeType(asDec.type);
            buffer.append("\n");
        } else {
            assert(false);
            throw new CodeGeneratorException("Unrecognized statement: " + statement);
        }
    } // writeStatement
    
    public void writeProgram(final Program program) throws CodeGeneratorException {
        for (final Statement statement : program.statements) {
            writeStatement(statement);
        }
    } // writeProgram
} // CodeGenerator
