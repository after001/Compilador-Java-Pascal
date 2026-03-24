package br.com.comcet.tp1.ast;

public final class BinaryExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final String operator;

    public BinaryExpression(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Expression left() { return left; }
    public Expression right() { return right; }
    public String operator() { return operator; }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level))
          .append("BinaryExpression ").append(operator).append("\n");
        left.printTree(sb, level + 1);
        right.printTree(sb, level + 1);
    }
}