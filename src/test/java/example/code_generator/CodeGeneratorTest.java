package example.code_generator;

import example.parser.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CodeGeneratorTest {
    @Test
    public void emptyProgram() {
        final Program program = new Program(new ArrayList<Statement>());
        final CodeGenerator generator = new CodeGenerator();
        generator.writeProgram(program);
        assertEquals("", generator.wholeProgramString());
    } // emptyProgram

    // int x = 7;
    @Test
    public void integerDeclaration() {
        final List<Statement> statements = new ArrayList<Statement>();
        final VariableDeclarationInitializationStatement statement =
            new VariableDeclarationInitializationStatement(new IntType(),
                                                           "x",
                                                           new IntegerExpression(7));
        statements.add(statement);
        final Program program = new Program(statements);
        final CodeGenerator generator = new CodeGenerator();
        generator.writeProgram(program);
        assertEquals("let x = 7; // int\n", generator.wholeProgramString());
    } // integerDeclaration
} // CodeGeneratorTest
