package br.com.comcet.tp3;

import br.com.comcet.tp1.TokenType;

public class SyntaxException extends RuntimeException {
    private final TokenType expected;
    private final TokenType found;

    public SyntaxException(TokenType expected, TokenType found) {
        super("Erro sintatico: esperado " + expected + ", encontrado " + found);
        this.expected = expected;
        this.found    = found;
    }

    public SyntaxException(String message) {
        super("Erro sintatico: " + message);
        this.expected = null;
        this.found    = null;
    }

    public TokenType expected() { return expected; }
    public TokenType found()    { return found; }
}