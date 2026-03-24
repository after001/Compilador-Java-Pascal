package br.com.comcet.tp1;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Symbol> table = new HashMap<>();

    public void add(String name, Symbol symbol) {
        if (table.containsKey(name)) {
            throw new IllegalArgumentException("Simbolo ja declarado: " + name);
        }
        table.put(name, symbol);
    }

    public Symbol get(String name) {
        return table.get(name);
    }
}