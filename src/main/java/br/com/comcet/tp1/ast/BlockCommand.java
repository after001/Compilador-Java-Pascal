package br.com.comcet.tp1.ast;

import java.util.List;

public final class BlockCommand extends Command {
    private final List<Command> commands;

    public BlockCommand(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> commands() { return commands; }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level)).append("BlockCommand\n");
        for (Command cmd : commands) {
            cmd.printTree(sb, level + 1);
        }
    }
}