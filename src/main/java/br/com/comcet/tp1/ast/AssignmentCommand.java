package br.com.comcet.tp1.ast;

public final class AssignmentCommand extends Command {
    private final Identifier id;
    private final Expression expr;

    public AssignmentCommand(Identifier id, Expression expr) {
        this.id = id;
        this.expr = expr;
    }

    public Identifier id() { return id; }
    public Expression expr() { return expr; }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level)).append("AssignmentCommand\n");
        id.printTree(sb, level + 1);
        expr.printTree(sb, level + 1);
    }
}