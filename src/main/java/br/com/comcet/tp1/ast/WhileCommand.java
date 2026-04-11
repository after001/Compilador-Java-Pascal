package br.com.comcet.tp1.ast;

public final class WhileCommand extends Command {
    private final Expression condition;
    private final Command body;

    public WhileCommand(Expression condition, Command body) {
        this.condition = condition;
        this.body      = body;
    }

    public Expression condition() { return condition; }
    public Command body()         { return body; }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level)).append("WhileCommand\n");
        sb.append(indent(level + 1)).append("condition:\n");
        condition.printTree(sb, level + 2);
        sb.append(indent(level + 1)).append("body:\n");
        body.printTree(sb, level + 2);
    }
}