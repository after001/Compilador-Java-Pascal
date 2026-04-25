package br.com.comcet.tp1.ast;

public final class ReadlnCommand extends Command {
    private final Identifier id;

    public ReadlnCommand(Identifier id) {
        this.id = id;
    }

    public Identifier id() { return id; }

    @Override
    protected void printTree(StringBuilder sb, int level) {
        sb.append(indent(level)).append("ReadlnCommand\n");
        id.printTree(sb, level + 1);
    }
}
