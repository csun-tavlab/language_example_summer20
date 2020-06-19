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

    public void writeOp(final Op op) {
        buffer.append(op.toString());
    } // writeOp
            
    public void writeExpression(final Expression expression) {
        // Object is needed for a return type; this is a dummy parameter
        // Error is used as it's an unchecked exception; this is also
        // a dummy parameter
        class WriteExpressionVisitor implements ExpressionVisitor<Object, Error> {
            public Object visitIntegerExpression(final int value) throws Error {
                buffer.append(value);
                return null;
            }
            public Object visitVariableExpression(final String name) throws Error {
                buffer.append(name);
                return null;
            }
            public Object visitBooleanExpression(final boolean value) throws Error {
                buffer.append(value);
                return null;
            }
            public Object visitOperatorExpression(final Expression e1,
                                                  final Op op,
                                                  final Expression e2) throws Error {
                buffer.append("(");
                writeExpression(e1);
                buffer.append(" ");
                writeOp(op);
                buffer.append(" ");
                writeExpression(e2);
                buffer.append(")");
                return null;
            }
        } // WriteExpressionVisitor

        expression.accept(new WriteExpressionVisitor());
    } // writeExpression

    public void writeType(final Type type) {

        // same dummy parameters as with writeExpression
        class WriteTypeVisitor implements TypeVisitor<Object, Error> {
            public Object visitIntType() throws Error {
                buffer.append("int");
                return null;
            }
            public Object visitBoolType() throws Error {
                buffer.append("bool");
                return null;
            }
        } // WriteTypeVisitor

        type.accept(new WriteTypeVisitor());
    } // writeType
    
    public void writeStatement(final Statement statement) {

        // same dummy parameters as with writeExpression
        class WriteStatementVisitor implements StatementVisitor<Object, Error> {
            public Object visitVariableDeclarationInitializationStatement(final Type type,
                                                                          final String variableName,
                                                                          final Expression expression) throws Error {
                // s ::= t x '=' e ';'
                // int x = 7;
                // let x = 7;
                
                // bool y = true;
                // let y = true; // bool
                buffer.append("let ");
                buffer.append(variableName);
                buffer.append(" = ");
                writeExpression(expression);
                buffer.append("; // ");
                writeType(type);
                buffer.append("\n");
                return null;
            } // visitVariableDeclarationInitializationStatement
        } // WriteStatementVisitor

        statement.accept(new WriteStatementVisitor());
    } // writeStatement
    
    public void writeProgram(final Program program) {
        for (final Statement statement : program.statements) {
            writeStatement(statement);
        }
    } // writeProgram
} // CodeGenerator
