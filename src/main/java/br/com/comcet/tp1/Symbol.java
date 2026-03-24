package br.com.comcet.tp1;

public class Symbol {
    private final String name;
    private final String type;
    private final Object value;

    public Symbol(String name, String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String name() { return name; }
    public String type() { return type; }
    public Object value() { return value; }
}