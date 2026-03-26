package br.com.comcet.tp2;

import br.com.comcet.tp1.Token;
import br.com.comcet.tp1.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScannerTest {

    @Test
    void reconheceDeclaracaoSimples() {
        String codigo = "var x : integer;";
        Scanner scanner = new Scanner(codigo);

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.KEYWORD, t1.type());
        assertEquals("var", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.IDENTIFIER, t2.type());
        assertEquals("x", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.DELIMITER, t3.type());
        assertEquals(":", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.KEYWORD, t4.type());
        assertEquals("integer", t4.text());

        Token t5 = scanner.nextToken();
        assertEquals(TokenType.DELIMITER, t5.type());
        assertEquals(";", t5.text());

        Token eof = scanner.nextToken();
        assertEquals(TokenType.EOF, eof.type());
    }

    @Test
    void reconheceAtribuicao() {
        String codigo = "x := 10;";
        Scanner scanner = new Scanner(codigo);

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.IDENTIFIER, t1.type());
        assertEquals("x", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.OPERATOR, t2.type());
        assertEquals(":=", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.NUMBER, t3.type());
        assertEquals("10", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.DELIMITER, t4.type());
        assertEquals(";", t4.text());

        assertEquals(TokenType.EOF, scanner.nextToken().type());
    }

    @Test
    void ignoraComentarioChaves() {
        String codigo = "{ isto e um comentario } var";
        Scanner scanner = new Scanner(codigo);

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.KEYWORD, t1.type());
        assertEquals("var", t1.text());
    }

    @Test
    void ignoraComentarioParenEstrela() {
        String codigo = "(* comentario *) begin";
        Scanner scanner = new Scanner(codigo);

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.KEYWORD, t1.type());
        assertEquals("begin", t1.text());
    }

    @Test
    void reconheceOperadoresCompostos() {
        String codigo = "<= >= <>";
        Scanner scanner = new Scanner(codigo);

        assertEquals("<=", scanner.nextToken().text());
        assertEquals(">=", scanner.nextToken().text());
        assertEquals("<>", scanner.nextToken().text());
    }

    @Test
    void lancaExcecaoParaCaractereInvalido() {
        String codigo = "@";
        Scanner scanner = new Scanner(codigo);

        assertThrows(LexicalException.class, scanner::nextToken);
    }
}