package example;

import example.tokenizer.Tokenizer;
import example.tokenizer.TokenizerException;
import example.tokenizer.Token;

import example.parser.Parser;
import example.parser.ParseException;
import example.parser.Program;

import example.typechecker.Typechecker;
import example.typechecker.IllTypedException;

import example.code_generator.CodeGenerator;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class ExampleCompiler {
    public static void usage() {
        System.out.println("Takes two arguments:");
        System.out.println("1. Name of the file to compile");
        System.out.println("2. Output file to write to");
    } // usage

    public static void writeStringToFile(final String contents,
                                         final String filename)
        throws IOException {
        final BufferedWriter writer =
            new BufferedWriter(new FileWriter(filename));
        try {
            writer.write(contents);
        } finally {
            writer.close();
        }
    } // writeStringToFile

    public static void doCompile(final String inputFilename,
                                 final String outputFilename)
        throws IOException,
               TokenizerException,
               ParseException,
               IllTypedException {
        final Tokenizer tokenizer = Tokenizer.makeTokenizerFromFile(inputFilename);
        final Token[] tokens = tokenizer.tokenize();
        final Parser parser = new Parser(tokens);
        final Program program = parser.parseProgram();
        final Typechecker typechecker = new Typechecker();
        typechecker.isWellTyped(program);
        final CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.writeProgram(program);
        final String outputProgram = codeGenerator.wholeProgramString();
        writeStringToFile(outputProgram, outputFilename);
    } // doCompile
    
    public static void main(final String[] args)
        throws IOException,
               TokenizerException,
               ParseException,
               IllTypedException {
        if (args.length == 2) {
            doCompile(args[0], args[1]);
        } else {
            usage();
        }
    } // main            
} // Example
