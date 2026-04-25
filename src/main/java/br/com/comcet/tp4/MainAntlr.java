package br.com.comcet.tp4;

import br.com.comcet.tp1.ast.AstNode;
import br.com.comcet.tp4.parser.MiniPascalLexer;
import br.com.comcet.tp4.parser.MiniPascalParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainAntlr {

    public static void main(String[] args) throws IOException {
        String source;

        if (args.length > 0) {
            source = new String(Files.readAllBytes(Paths.get(args[0])));
        } else {
            source = """
                    program teste;
                    var x: integer;
                    begin
                        x := 10 + 5 * 2;
                        writeln(x);
                    end.
                    """;
        }

        CharStream input = CharStreams.fromString(source);
        MiniPascalLexer lexer = new MiniPascalLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniPascalParser parser = new MiniPascalParser(tokens);

        ParseTree tree = parser.program();
        MyVisitor visitor = new MyVisitor();
        AstNode ast = visitor.visit(tree);

        System.out.println(ast.printTree());
    }
}