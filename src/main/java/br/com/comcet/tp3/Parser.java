package br.com.comcet.tp3;

import br.com.comcet.tp1.Token;
import br.com.comcet.tp1.TokenType;
import br.com.comcet.tp1.ast.*;
import br.com.comcet.tp2.Scanner;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final Scanner scanner;
    private Token currentToken;

    public Parser(Scanner scanner) {
        this.scanner      = scanner;
        this.currentToken = scanner.nextToken(); // Carrega o primeiro token
    }

    // ── Métodos auxiliares ──────────────────────────────────────────────────

    private void advance() {
        currentToken = scanner.nextToken();
    }

    private void match(TokenType expected) {
        if (currentToken.type() == expected) {
            advance();
        } else {
            throw new SyntaxException(expected, currentToken.type());
        }
    }

    private boolean check(TokenType type) {
        return currentToken.type() == type;
    }

    private boolean checkText(String text) {
        return currentToken.text().equals(text);
    }

    private boolean isKeyword(String keyword) {
        return currentToken.type() == TokenType.KEYWORD
            && currentToken.text().equalsIgnoreCase(keyword);
    }

    private boolean isOperator(String op) {
        return currentToken.type() == TokenType.OPERATOR
            && currentToken.text().equals(op);
    }

    private boolean isDelimiter(String delim) {
        return currentToken.type() == TokenType.DELIMITER
            && currentToken.text().equals(delim);
    }

    // ── Ponto de entrada ────────────────────────────────────────────────────

    public Program parseProgram() {
        // program NomeDoPrograma;
        String name = "program";
        if (isKeyword("program")) {
            advance();
            name = currentToken.text();
            match(TokenType.IDENTIFIER);
            matchDelimiter(";");
        }

        // var declarations (opcional)
        if (isKeyword("var")) {
            skipVarDeclarations();
        }

        // begin ... end.
        List<Command> commands = new ArrayList<>();
        if (isKeyword("begin")) {
            advance();
            while (!isKeyword("end") && !check(TokenType.EOF)) {
                try {
                    commands.add(parseCommand());
                } catch (SyntaxException e) {
                    System.err.println(e.getMessage());
                    sincronizar();
                }
            }
            if (isKeyword("end")) advance();
            if (isDelimiter(".")) advance();
        }

        return new Program(name, commands);
    }

    // Pula declarações de variáveis (serão tratadas na Etapa 5 - Semântica)
    private void skipVarDeclarations() {
        while (isKeyword("var") || (check(TokenType.IDENTIFIER) && !isKeyword("begin"))) {
            advance();
            if (isKeyword("begin")) break;
        }
    }

    // ── Comandos ────────────────────────────────────────────────────────────

    public Command parseCommand() {
        // if
        if (isKeyword("if")) {
            return parseIf();
        }
        // while
        if (isKeyword("while")) {
            return parseWhile();
        }
        // begin (bloco)
        if (isKeyword("begin")) {
            return parseBlock();
        }
        // atribuição: id :=
        if (check(TokenType.IDENTIFIER)) {
            return parseAssignment();
        }

        throw new SyntaxException("Token inesperado: " + currentToken.text());
    }

    private AssignmentCommand parseAssignment() {
        String name = currentToken.text();
        match(TokenType.IDENTIFIER);
        Identifier id = new Identifier(name);

        if (!isOperator(":=")) {
            throw new SyntaxException("Esperado ':=', encontrado: " + currentToken.text());
        }
        advance(); // consome :=

        Expression expr = parseExpression();
        matchDelimiter(";");

        return new AssignmentCommand(id, expr);
    }

    private IfCommand parseIf() {
        advance(); // consome 'if'
        Expression condition = parseExpression();

        if (!isKeyword("then")) {
            throw new SyntaxException("Esperado 'then', encontrado: " + currentToken.text());
        }
        advance(); // consome 'then'

        Command thenBranch = parseCommand();
        Command elseBranch = null;

        if (isKeyword("else")) {
            advance(); // consome 'else'
            elseBranch = parseCommand();
        }

        return new IfCommand(condition, thenBranch, elseBranch);
    }

    private WhileCommand parseWhile() {
        advance(); // consome 'while'
        Expression condition = parseExpression();

        if (!isKeyword("do")) {
            throw new SyntaxException("Esperado 'do', encontrado: " + currentToken.text());
        }
        advance(); // consome 'do'

        Command body = parseCommand();
        return new WhileCommand(condition, body);
    }

    private BlockCommand parseBlock() {
        advance(); // consome 'begin'
        List<Command> commands = new ArrayList<>();

        while (!isKeyword("end") && !check(TokenType.EOF)) {
            try {
                commands.add(parseCommand());
            } catch (SyntaxException e) {
                System.err.println(e.getMessage());
                sincronizar();
            }
        }
        if (isKeyword("end")) advance(); // consome 'end'

        return new BlockCommand(commands);
    }

    // ── Expressões (com precedência) ─────────────────────────────────────────

    // Nível 1: + e - (menor precedência)
    public Expression parseExpression() {
        Expression left = parseTerm();

        while (isOperator("+") || isOperator("-")
            || isOperator("=")  || isOperator("<>")
            || isOperator("<")  || isOperator(">")
            || isOperator("<=") || isOperator(">=")) {

            String op = currentToken.text();
            advance();
            Expression right = parseTerm();
            left = new BinaryExpression(left, right, op);
        }

        return left;
    }

    // Nível 2: * e / (maior precedência)
    private Expression parseTerm() {
        Expression left = parseFactor();

        while (isOperator("*") || isOperator("/")) {
            String op = currentToken.text();
            advance();
            Expression right = parseFactor();
            left = new BinaryExpression(left, right, op);
        }

        return left;
    }

    // Nível 3: literais, identificadores, parênteses, unário
    private Expression parseFactor() {
        // - unário
        if (isOperator("-")) {
            advance();
            Expression operand = parseFactor();
            return new UnaryExpression("-", operand);
        }

        // not unário
        if (isKeyword("not")) {
            advance();
            Expression operand = parseFactor();
            return new UnaryExpression("not", operand);
        }

        // número
        if (check(TokenType.NUMBER)) {
            String value = currentToken.text();
            advance();
            return new Literal(value);
        }

        // string literal
        if (check(TokenType.STRING_LITERAL)) {
            String value = currentToken.text();
            advance();
            return new Literal(value);
        }

        // true / false
        if (isKeyword("true") || isKeyword("false")) {
            String value = currentToken.text();
            advance();
            return new Literal(value);
        }

        // identificador
        if (check(TokenType.IDENTIFIER)) {
            String name = currentToken.text();
            advance();
            return new Identifier(name);
        }

        // parênteses: ( expression )
        if (isDelimiter("(")) {
            advance(); // consome '('
            Expression expr = parseExpression();
            if (!isDelimiter(")")) {
                throw new SyntaxException("Esperado ')', encontrado: " + currentToken.text());
            }
            advance(); // consome ')'
            return expr;
        }

        throw new SyntaxException("Expressao invalida: " + currentToken.text());
    }

    // ── Recuperação de erros (Panic Mode) ────────────────────────────────────

    private void sincronizar() {
        while (!check(TokenType.EOF)) {
            if (isDelimiter(";") || isKeyword("end")) {
                advance();
                return;
            }
            advance();
        }
    }

    // ── Auxiliar para delimitadores ──────────────────────────────────────────

    private void matchDelimiter(String delim) {
        if (isDelimiter(delim)) {
            advance();
        } else {
            throw new SyntaxException("Esperado '" + delim + "', encontrado: " + currentToken.text());
        }
    }
}