package br.com.comcet.tp3;

import br.com.comcet.tp1.ast.*;
import br.com.comcet.tp2.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void respeitaPrecedenciaMultiplicacao() {
        // Esperado: 10 + (5 * 2)
        String codigo = "x := 10 + 5 * 2;";
        Scanner scanner = new Scanner(codigo);
        Parser parser = new Parser(scanner);

        Command cmd = parser.parseCommand();

        assertTrue(cmd instanceof AssignmentCommand);
        AssignmentCommand a = (AssignmentCommand) cmd;

        assertTrue(a.expr() instanceof BinaryExpression);
        BinaryExpression plus = (BinaryExpression) a.expr();
        assertEquals("+", plus.operator());

        assertTrue(plus.left() instanceof Literal);
        assertTrue(plus.right() instanceof BinaryExpression);

        BinaryExpression mult = (BinaryExpression) plus.right();
        assertEquals("*", mult.operator());
    }

    @Test
    void parentesesAlteramPrecedencia() {
        // Esperado: (10 + 5) * 2
        String codigo = "x := (10 + 5) * 2;";
        Scanner scanner = new Scanner(codigo);
        Parser parser = new Parser(scanner);

        Command cmd = parser.parseCommand();

        assertTrue(cmd instanceof AssignmentCommand);
        AssignmentCommand a = (AssignmentCommand) cmd;

        assertTrue(a.expr() instanceof BinaryExpression);
        BinaryExpression mult = (BinaryExpression) a.expr();
        assertEquals("*", mult.operator());

        assertTrue(mult.left() instanceof BinaryExpression);
        BinaryExpression plus = (BinaryExpression) mult.left();
        assertEquals("+", plus.operator());
    }

    @Test
    void parseia_atribuicao_simples() {
        String codigo = "x := 10;";
        Scanner scanner = new Scanner(codigo);
        Parser parser = new Parser(scanner);

        Command cmd = parser.parseCommand();

        assertTrue(cmd instanceof AssignmentCommand);
        AssignmentCommand a = (AssignmentCommand) cmd;
        assertEquals("x", a.id().name());
        assertTrue(a.expr() instanceof Literal);
    }

    @Test
    void parseia_if_sem_else() {
        String codigo = "if x then y := 1;";
        Scanner scanner = new Scanner(codigo);
        Parser parser = new Parser(scanner);

        Command cmd = parser.parseCommand();

        assertTrue(cmd instanceof IfCommand);
        IfCommand ic = (IfCommand) cmd;
        assertNull(ic.elseBranch());
        assertTrue(ic.thenBranch() instanceof AssignmentCommand);
    }

    @Test
    void parseia_if_com_else() {
        String codigo = "if x then y := 1; else y := 2;";
        Scanner scanner = new Scanner(codigo);
        Parser parser = new Parser(scanner);

        Command cmd = parser.parseCommand();

        assertTrue(cmd instanceof IfCommand);
        IfCommand ic = (IfCommand) cmd;
        assertNotNull(ic.elseBranch());
        assertTrue(ic.thenBranch() instanceof AssignmentCommand);
        assertTrue(ic.elseBranch() instanceof AssignmentCommand);
    }

    @Test
    void parseia_while() {
        String codigo = "while x do y := 1;";
        Scanner scanner = new Scanner(codigo);
        Parser parser = new Parser(scanner);

        Command cmd = parser.parseCommand();

        assertTrue(cmd instanceof WhileCommand);
        WhileCommand wc = (WhileCommand) cmd;
        assertTrue(wc.condition() instanceof Identifier);
        assertTrue(wc.body() instanceof AssignmentCommand);
    }
}