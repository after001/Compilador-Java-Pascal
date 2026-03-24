package br.com.comcet.tp1.ast;

public final class Literal extends Expression {
    private final String value;

    public Literal(String value) {
        this.value = value;
    }

    public Literal(int value) {
        this.value = String.valueOf(value);
    }

    public String value() {
        return value;
    }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level))
          .append("Literal(").append(value).append(")\n");
    }
}