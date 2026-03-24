package br.com.comcet.tp1.ast;

public final class Identifier extends Expression {
    private final String name;

    public Identifier(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level))
          .append("Identifier(\"").append(name).append("\")\n");
    }
}