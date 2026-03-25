package org.pattern.rpg.application;

import org.pattern.rpg.presentation.ui.ConsoleUI;

public class BattleManager {
    
    // O BattleManager recebe o ConsoleUI para poder se comunicar com o jogador
    private ConsoleUI ui;

    public BattleManager(ConsoleUI ui) {
        this.ui = ui;
    }

    public void iniciarBatalhaTeste(String nomeJogador, String equipamentoEscolhido) {
        boolean emBatalha = true;

        int nivel = 1;
        int hp = 100;
        String logBatalha = "Um grupo de inimigos bloqueia seu caminho!";
        String estadoMenu = "PRINCIPAL";

        while (emBatalha) {
            ui.limparTerminal();
            ui.imprimir("=======================================================");
            ui.imprimir(String.format("%-25s %25s", "Nível: " + String.format("%02d", nivel), "Inimigo 1 (HP: 30)"));
            ui.imprimir(String.format("%-25s %25s", "HP: " + hp, "Inimigo 2 (HP: 45)"));
            ui.imprimir(String.format("%-25s %25s", "", "Inimigo 3 (HP: 20)"));

            ui.imprimir("-------------------------------------------------------");
            int espacosEsquerda = Math.max(0, (55 - logBatalha.length()) / 2);
            ui.imprimir(" ".repeat(espacosEsquerda) + logBatalha);
            ui.imprimir("-------------------------------------------------------");

            if (estadoMenu.equals("PRINCIPAL")) {
                ui.imprimir("  1. Atacar      |  3. Fugir (Sair do teste)");
                ui.imprimir("  2. Usar item   |");
                // Usamos print (sem 'ln') no ConsoleUI futuramente se quiser, 
                // mas imprimir com quebra de linha funciona bem aqui.
                ui.imprimir("\nO que " + nomeJogador + " fará? (1-3): ");

                String escolha = ui.lerEntrada();
                switch (escolha) {
                    case "1": estadoMenu = "ATACAR"; break;
                    case "2": estadoMenu = "ITEM"; break;
                    case "3": 
                        logBatalha = "Você fugiu covardemente...";
                        emBatalha = false; 
                        break;
                    default: 
                        logBatalha = "Ação inválida! Escolha de 1 a 3."; 
                        break;
                }
            } else if (estadoMenu.equals("ATACAR")) {
                ui.imprimir("  1. Ataque Rápido  (" + equipamentoEscolhido + ")");
                ui.imprimir("  2. Ataque Pesado  (" + equipamentoEscolhido + ")");
                ui.imprimir("  3. [Voltar]  ");
                ui.imprimir("\nEscolha seu ataque (1-3): ");

                String escolha = ui.lerEntrada();
                switch (escolha) {
                    case "1":
                        logBatalha = "Você usou Ataque Rápido. Causou 15 de dano.";
                        estadoMenu = "PRINCIPAL";
                        break;
                    case "2":
                        logBatalha = "Você usou Ataque Pesado. Causou 25 de dano!";
                        estadoMenu = "PRINCIPAL";
                        break;
                    case "3": estadoMenu = "PRINCIPAL"; break;
                    default: 
                        logBatalha = "Ataque inválido";
                        break;
                }
            } else if (estadoMenu.equals("ITEM")) {
                ui.imprimir("  1. Poção de Vida Pequena");
                ui.imprimir("  2. Bomba de Fumaça");
                ui.imprimir("  3. [Voltar]");
                ui.imprimir("\nEscolha o item (1-3): ");

                String escolha = ui.lerEntrada();
                switch (escolha) {
                    case "1":
                        logBatalha = "Você usou Poção de vida Pequena. Recuperou 20HP!";
                        hp = Math.min(100, hp + 20);
                        estadoMenu = "PRINCIPAL";
                        break;
                    case "2":
                        logBatalha = "Você usou Bomba de Fumaça. Os inimigos foram cegados temporariamente!";
                        estadoMenu = "PRINCIPAL";
                        break;
                    case "3":
                        estadoMenu = "PRINCIPAL";
                        break;
                    default:
                        logBatalha = "Item Inválido!";
                        break;
                }
            }
        }
        
        ui.imprimir("\nFim do combate inicial. Pressione [ENTER] para continuar...");
        ui.lerEntrada();
    }
}