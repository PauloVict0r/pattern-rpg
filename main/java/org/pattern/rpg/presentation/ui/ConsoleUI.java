package org.pattern.rpg.presentation.ui;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void limparTerminal() {
        try {
            String sistema = System.getProperty("os.name").toLowerCase();
            if (sistema.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao limpar o console: " + e.getMessage());
        }
    }

    public void pausar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void imprimir(String texto) {
        System.out.println(texto);
    }

    public void espacar(int largura, String string) {
        if (string.length() >= largura) {
            System.out.println("|" + string + "|");
        } else {
            int espacosEsq = (largura - string.length()) / 2;
            String resultado = String.format("%" + (espacosEsq + string.length()) + "s", string);
            resultado = String.format("|" + "%-" + largura + "s" + "|", resultado);
            System.out.println(resultado);
        }
    }
    
    public String lerEntrada() {
        return scanner.nextLine();
    }
}