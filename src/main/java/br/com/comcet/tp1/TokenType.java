package br.com.comcet.tp1;

public enum TokenType {
    // Categorias gerais (usadas pelo Scanner)
    KEYWORD,
    IDENTIFIER,
    NUMBER,
    STRING_LITERAL,
    OPERATOR,
    DELIMITER,
    EOF,

    // Palavras reservadas específicas (usadas pelo Parser)
    PROGRAM, VAR, BEGIN, END,
    IF, THEN, ELSE,
    WHILE, DO,
    REPEAT, UNTIL,
    READLN, WRITELN,
    TRUE, FALSE,
    INTEGER, BOOLEAN, STRING,
    AND, OR, NOT,

    // Operadores específicos (usados pelo Parser)
    PLUS, MINUS, MULTIPLY, DIVIDE,
    ASSIGN, EQ, NEQ, LT, GT, LEQ, GEQ,

    // Delimitadores específicos (usados pelo Parser)
    SEMICOLON, COLON, COMMA, DOT,
    LPAREN, RPAREN
}