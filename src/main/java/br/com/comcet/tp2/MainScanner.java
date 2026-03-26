package br.com.comcet.tp2;

import br.com.comcet.tp1.Token;
import br.com.comcet.tp1.TokenType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainScanner {

    public static void main(String[] args) throws IOException {
        String source;

        if (args.length > 0) {
            source = new String(Files.readAllBytes(Paths.get(args[0])));
        } else {
            // Exemplo embutido para teste rápido
            source = "var x : integer;\nx := 10;";
        }

        Scanner scanner = new Scanner(source);
        Token token;

        do {
            token = scanner.nextToken();
            System.out.println("[" + token.type() + ", \"" + token.text() + "\"]");
        } while (token.type() != TokenType.EOF);
    }
}