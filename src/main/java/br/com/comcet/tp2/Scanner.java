package br.com.comcet.tp2;

import br.com.comcet.tp1.Token;
import br.com.comcet.tp1.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Scanner {

    private final String source;
    private int pos;
    private int line;
    private int column;

    // Mapa de palavras reservadas
    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();
    static {
        KEYWORDS.put("program",  TokenType.PROGRAM);
        KEYWORDS.put("var",      TokenType.VAR);
        KEYWORDS.put("begin",    TokenType.BEGIN);
        KEYWORDS.put("end",      TokenType.END);
        KEYWORDS.put("if",       TokenType.IF);
        KEYWORDS.put("then",     TokenType.THEN);
        KEYWORDS.put("else",     TokenType.ELSE);
        KEYWORDS.put("while",    TokenType.WHILE);
        KEYWORDS.put("do",       TokenType.DO);
        KEYWORDS.put("repeat",   TokenType.REPEAT);
        KEYWORDS.put("until",    TokenType.UNTIL);
        KEYWORDS.put("readln",   TokenType.READLN);
        KEYWORDS.put("writeln",  TokenType.WRITELN);
        KEYWORDS.put("true",     TokenType.TRUE);
        KEYWORDS.put("false",    TokenType.FALSE);
        KEYWORDS.put("integer",  TokenType.INTEGER);
        KEYWORDS.put("boolean",  TokenType.BOOLEAN);
        KEYWORDS.put("string",   TokenType.STRING);
        KEYWORDS.put("and",      TokenType.AND);
        KEYWORDS.put("or",       TokenType.OR);
        KEYWORDS.put("not",      TokenType.NOT);
    }

    public Scanner(String source) {
        this.source = source;
        this.pos    = 0;
        this.line   = 1;
        this.column = 1;
    }

    // ── Métodos auxiliares ──────────────────────────────────────────────────

    private boolean isAtEnd() {
        return pos >= source.length();
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(pos);
    }

    private char peekNext() {
        if (pos + 1 >= source.length()) return '\0';
        return source.charAt(pos + 1);
    }

    private char advance() {
        char c = source.charAt(pos++);
        if (c == '\n') { line++; column = 1; }
        else           { column++; }
        return c;
    }

    private void skipWhitespaceAndComments() {
        while (!isAtEnd()) {
            char c = peek();

            // Espaços, tabs, quebras de linha
            if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                advance();

            // Comentário { ... }
            } else if (c == '{') {
                advance();
                while (!isAtEnd() && peek() != '}') advance();
                if (!isAtEnd()) advance(); // consome '}'

            // Comentário (* ... *)
            } else if (c == '(' && peekNext() == '*') {
                advance(); advance(); // consome '(' e '*'
                while (!isAtEnd()) {
                    if (peek() == '*' && peekNext() == ')') {
                        advance(); advance(); // consome '*' e ')'
                        break;
                    }
                    advance();
                }
            } else {
                break;
            }
        }
    }

    // ── Método principal ────────────────────────────────────────────────────

    public Token nextToken() {
        skipWhitespaceAndComments();

        if (isAtEnd()) {
            return new Token(TokenType.EOF, "");
        }

        char c = peek();

        // Identificador ou palavra reservada
        if (Character.isLetter(c)) {
            return readIdentifierOrKeyword();
        }

        // Número inteiro
        if (Character.isDigit(c)) {
            return readNumber();
        }

        // String literal 'texto'
        if (c == '\'') {
            return readString();
        }

        // Operadores e delimitadores
        advance();
        switch (c) {
            case '+': return new Token(TokenType.OPERATOR,  "+");
            case '-': return new Token(TokenType.OPERATOR,  "-");
            case '*': return new Token(TokenType.OPERATOR,  "*");
            case '/': return new Token(TokenType.OPERATOR,  "/");
            case '(': return new Token(TokenType.DELIMITER, "(");
            case ')': return new Token(TokenType.DELIMITER, ")");
            case ';': return new Token(TokenType.DELIMITER, ";");
            case ',': return new Token(TokenType.DELIMITER, ",");
            case '.': return new Token(TokenType.DELIMITER, ".");

            case ':':
                if (peek() == '=') { advance(); return new Token(TokenType.OPERATOR,  ":="); }
                return new Token(TokenType.DELIMITER, ":");

            case '<':
                if (peek() == '=') { advance(); return new Token(TokenType.OPERATOR, "<="); }
                if (peek() == '>') { advance(); return new Token(TokenType.OPERATOR, "<>"); }
                return new Token(TokenType.OPERATOR, "<");

            case '>':
                if (peek() == '=') { advance(); return new Token(TokenType.OPERATOR, ">="); }
                return new Token(TokenType.OPERATOR, ">");

            case '=': return new Token(TokenType.OPERATOR, "=");

            default:
                throw new LexicalException(c, line, column - 1);
        }
    }

    // ── Leitores específicos ────────────────────────────────────────────────

    private Token readIdentifierOrKeyword() {
        StringBuilder sb = new StringBuilder();
        while (!isAtEnd() && (Character.isLetterOrDigit(peek()) || peek() == '_')) {
            sb.append(advance());
        }
        String text = sb.toString().toLowerCase();
        TokenType type = KEYWORDS.getOrDefault(text, TokenType.IDENTIFIER);
        // Palavras reservadas usam KEYWORD como categoria geral
        if (type != TokenType.IDENTIFIER) {
            return new Token(TokenType.KEYWORD, text);
        }
        return new Token(TokenType.IDENTIFIER, text);
    }

    private Token readNumber() {
        StringBuilder sb = new StringBuilder();
        while (!isAtEnd() && Character.isDigit(peek())) {
            sb.append(advance());
        }
        return new Token(TokenType.NUMBER, sb.toString());
    }

    private Token readString() {
        advance(); // consome a aspa inicial '
        StringBuilder sb = new StringBuilder();
        while (!isAtEnd() && peek() != '\'') {
            sb.append(advance());
        }
        if (!isAtEnd()) advance(); // consome a aspa final '
        return new Token(TokenType.STRING_LITERAL, sb.toString());
    }
}