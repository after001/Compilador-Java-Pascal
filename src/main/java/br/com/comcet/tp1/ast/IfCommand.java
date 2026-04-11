package br.com.comcet.tp1.ast;

public final class IfCommand extends Command {
    private final Expression condition;
    private final Command thenBranch;
    private final Command elseBranch; // pode ser null

    public IfCommand(Expression condition, Command thenBranch, Command elseBranch) {
        this.condition  = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public Expression condition()  { return condition; }
    public Command thenBranch()    { return thenBranch; }
    public Command elseBranch()    { return elseBranch; }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level)).append("IfCommand\n");
        sb.append(indent(level + 1)).append("condition:\n");
        condition.printTree(sb, level + 2);
        sb.append(indent(level + 1)).append("then:\n");
        thenBranch.printTree(sb, level + 2);
        if (elseBranch != null) {
            sb.append(indent(level + 1)).append("else:\n");
            elseBranch.printTree(sb, level + 2);
        }
    }
}