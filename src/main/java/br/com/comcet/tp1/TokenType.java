package br.com.comcet.tp1;

public enum TokenType {
    // Literais
    INTEGER, BOOLEAN, STRING,
    // Identificadores e palavras reservadas
    IDENTIFIER, PROGRAM, VAR, BEGIN, END,
    IF, THEN, ELSE, WHILE, DO, REPEAT, UNTIL,
    READLN, WRITELN, TRUE, FALSE,
    // Operadores
    PLUS, MINUS, MULTIPLY, DIVIDE,
    ASSIGN, EQ, NEQ, LT, GT, LEQ, GEQ,
    AND, OR, NOT,
    // Símbolos especiais
    SEMICOLON, COLON, COMMA, DOT,
    LPAREN, RPAREN,
    // Controle
    EOF
}