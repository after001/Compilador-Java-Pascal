package br.com.comcet.tp1.ast;

import java.util.List;

public final class Program extends AstNode {
    private final String name;
    private final List<Command> commands;

    public Program(String name, List<Command> commands) {
        this.name = name;
        this.commands = commands;
    }

    public String name() { return name; }
    public List<Command> commands() { return commands; }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level)).append("Program(\"").append(name).append("\")\n");
        for (Command cmd : commands) {
            cmd.printTree(sb, level + 1);
        }
    }
}