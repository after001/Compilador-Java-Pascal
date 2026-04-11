package br.com.comcet.tp3;

import br.com.comcet.tp1.ast.Program;
import br.com.comcet.tp2.Scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainParser {

    public static void main(String[] args) throws IOException {
        String source;

        if (args.length > 0) {
            source = new String(Files.readAllBytes(Paths.get(args[0])));
        } else {
            // Exemplo embutido para teste rápido
            source = "x := 10 + 5 * 2;";
        }

        Scanner scanner = new Scanner(source);
        Parser parser   = new Parser(scanner);

        Program program = parser.parseProgram();
        System.out.println(program.printTree());
    }
}