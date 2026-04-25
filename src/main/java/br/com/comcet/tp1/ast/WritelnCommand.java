package br.com.comcet.tp1.ast;

public final class WritelnCommand extends Command {
    private final Expression expr;

    public WritelnCommand(Expression expr) {
        this.expr = expr;
    }

    public Expression expr() { return expr; }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level)).append("WritelnCommand\n");
        expr.printTree(sb, level + 1);
    }
}
