package br.com.comcet.tp2;

public class LexicalException extends RuntimeException {
    private final char character;
    private final int line;
    private final int column;

    public LexicalException(char character, int line, int column) {
        super("Caractere invalido '" + character + "' na linha " + line + ", coluna " + column);
        this.character = character;
        this.line = line;
        this.column = column;
    }

    public char character() { return character; }
    public int line() { return line; }
    public int column() { return column; }
}