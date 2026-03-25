package org.pattern.rpg.application;

import org.pattern.rpg.presentation.ui.ConsoleUI;

public class InventoryManager {
    private ConsoleUI ui;

    public InventoryManager(ConsoleUI ui) {
        this.ui = ui;
    }

    // FUTURAMENTE: Esse método receberá um objeto da classe Player que contém uma List<Item> 
    // em vez de receber Nível, HP e Arma soltos.
    public void mostrarInventario(int nivel, int hp, String equipamentoInicial) {
        boolean noInventario = true;
        String abaAtual = "ARMAS";

        // Variáveis estáticas temporárias
        String armaduraEquipada = "Armadura quebrada";
        String armaSecundaria = "Amuletos";

        while(noInventario) {
            ui.limparTerminal();
            ui.imprimir("=======================================================");
            ui.imprimir("HP: " + hp);
            ui.imprimir("");

            String tituloDireita = "";
            String item1 = "", item2 = "", item3 ="";

            if(abaAtual.equals("ARMAS")){
                tituloDireita = "ARMAS:";
                item1 = "1. " + equipamentoInicial;
                item2 = "2. Perna de mesa quebrada";
                item3 = "3. ";
            } else if(abaAtual.equals("ARMADURAS")){
                tituloDireita = "ARMADURAS:";
                item1 = "1. " + armaduraEquipada;
                item2 = "2. Túnica rasgada";
                item3 = "3. ";
            } else if(abaAtual.equals("ITENS")){
                tituloDireita = "ITENS:";
                item1 = "1. Poção de vida pequena";
                item2 = "2. Bomba de fumaça (x1)";
                item3 = "3. ";
            }

            ui.imprimir(String.format("%-35s |  %-20s", "Proteção:        " + armaduraEquipada, tituloDireita));
            ui.imprimir(String.format("%-35s |  %-20s", "Arma primária:   " + equipamentoInicial, item1));
            ui.imprimir(String.format("%-35s |  %-20s", "Arma secundária: " + armaSecundaria, item2));
            ui.imprimir(String.format("%-35s |  %-20s", "", item3));

            ui.imprimir("-------------------------------------------------------");
            ui.imprimir("Categorias:");
            ui.imprimir("  1. Armas");
            ui.imprimir("  2. Armaduras");
            ui.imprimir("  3. Itens");
            ui.imprimir("  4. [ Voltar ao Jogo ]");
            ui.imprimir("\nEscolha uma aba (1-4): ");

            String escolha = ui.lerEntrada();

            switch (escolha) {
                case "1": abaAtual = "ARMAS"; break;
                case "2": abaAtual = "ARMADURAS"; break;
                case "3": abaAtual = "ITENS"; break;
                case "4": noInventario = false; break;
                default:
                    ui.imprimir("=> Categoria Inválida!");
                    ui.pausar(1200);
                    break;
            }
        }
    }
}