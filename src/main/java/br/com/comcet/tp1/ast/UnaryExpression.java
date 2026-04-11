package br.com.comcet.tp1.ast;

public final class UnaryExpression extends Expression {
    private final String operator;
    private final Expression operand;

    public UnaryExpression(String operator, Expression operand) {
        this.operator = operator;
        this.operand  = operand;
    }

    public String operator() { return operator; }
    public Expression operand() { return operand; }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level))
          .append("UnaryExpression ").append(operator).append("\n");
        operand.printTree(sb, level + 1);
    }
}