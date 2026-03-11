package br.com.comcet.tp0;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        String input = "";
        boolean isFilePath = false;

        // 1. Prioridade: Argumentos de Linha de Comando (CLI)
        if (args.length > 0) {
            if (args[0].equals("-f") && args.length > 1) {
                input = args[1];
                isFilePath = true;
            } else {
                input = args[0];
                // Detecção automática simples se não houver flag
                if (input.toLowerCase().endsWith(".txt") && new File(input).exists()) {
                    isFilePath = true;
                }
            }
        } 
        // 2. Fallback: Entrada Interativa (Scanner)
        else {
            Scanner keyboard = new Scanner(System.in);
            System.out.print("Digite o texto ou o caminho de um arquivo .txt: ");
            input = keyboard.nextLine();
            if (input.toLowerCase().endsWith(".txt") && new File(input).exists()) {
                isFilePath = true;
            }
            keyboard.close();
        }

        String content = "";
        if (isFilePath) {
            try {
                content = new Scanner(new File(input)).useDelimiter("\\Z").next();
            } catch (FileNotFoundException e) {
                System.err.println("Erro: Arquivo não encontrado: " + input);
                return;
            }
        } else {
            content = input;
        }

        exibirEstatisticas(content);
    }

    public static void exibirEstatisticas(String text) {
        // TODO: Implementar a lógica de estatísticas aqui
        // 1. Limpar o texto (remover pontuação, converter para minúsculas)
        // 2. Contar caracteres (apenas letras a-zA-Z) -> "Caracteres: X"
        // 3. Contar total de palavras -> "Palavras: X"
        // 4. Encontrar letra mais frequente -> "Frequente: X" 
        //    (Critério: presente em mais palavras diferentes. Desempate: Alfabético A-Z)
        // 5. Encontrar palavra mais frequente -> "Frequente: X"
        //    (Desempate: Alfabético A-Z)
        
        System.out.println("LOGICA A SER IMPLEMENTADA");
    }
}