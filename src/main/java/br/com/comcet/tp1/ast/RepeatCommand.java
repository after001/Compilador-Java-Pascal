package br.com.comcet.tp1.ast;

public final class RepeatCommand extends Command {
    private final Command body;
    private final Expression condition;

    public RepeatCommand(Command body, Expression condition) {
        this.body      = body;
        this.condition = condition;
    }

    public Command body()          { return body; }
    public Expression condition()  { return condition; }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level)).append("RepeatCommand\n");
        sb.append(indent(level + 1)).append("body:\n");
        body.printTree(sb, level + 2);
        sb.append(indent(level + 1)).append("until:\n");
        condition.printTree(sb, level + 2);
    }
}