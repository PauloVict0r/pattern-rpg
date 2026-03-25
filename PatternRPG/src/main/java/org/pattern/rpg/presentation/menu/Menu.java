package org.pattern.rpg.presentation.menu;

import org.pattern.rpg.application.GameManager;
import org.pattern.rpg.presentation.ui.ConsoleUI;
import org.pattern.rpg.domain.entity.enemy.Enemy;
import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.domain.factory.*;
import org.pattern.rpg.domain.entity.*;
import java.util.Random;
import java.util.List;

public class Menu {
    private ConsoleUI ui;
    private GameManager facade;

    public Menu(ConsoleUI ui, GameManager facade) {
        this.ui = ui;
        this.facade = facade;
    }

    public void exibirMenuPrincipal(ConsoleUI ui) {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("      BEM-VINDO VIAJANTE!       ");
        ui.imprimir("=================================");
        ui.imprimir("\n--- MENU PRINCIPAL ---");
        ui.imprimir("1. Continuar");
        ui.imprimir("2. Novo Jogo");
        ui.imprimir("3. Scores");
        ui.imprimir("4. Sair");
        ui.imprimir("Escolha uma opção (1-4): ");

        String entrada = ui.lerEntrada();

        try {
            int opcao = Integer.parseInt(entrada);
            switch (opcao) {
                case 1:
                    ui.imprimir("=> Opção escolhida: CONTINUAR o jogo.");
                    ui.pausar(2000);
                    // FUTURAMENTE: Aqui chamaremos o SaveRepository.java para buscar o save no Banco de Dados
                    facade.continuarJogo();
                    break;
                case 2:
                    ui.imprimir("=> Opção escolhida: Iniciar NOVO JOGO.");
                    criarNovoPersonagem();
                    break;
                case 3:
                    ui.imprimir("=> Opção escolhida: Visualizar SCORES.");
                    exibirScores(ui);
                    break;
                case 4:
                    ui.imprimir("=> Opção escolhida: SAIR. Até a próxima aventura!");
                    ui.pausar(1000);
                    ui.limparTerminal();
                    facade.encerrarJogo();
                    break;
                default:
                    ui.imprimir("=> Opção inválida! Por favor, digite um número de 1 a 4.");
                    ui.pausar(2000);
                    ui.limparTerminal();
                    break;
            }
        } catch (NumberFormatException e) {
            ui.imprimir("=> Entrada inválida! Digite apenas números.");
            ui.pausar(2000);
        }
    }

    private void criarNovoPersonagem() {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("            NOVO JOGO            ");
        ui.imprimir("=================================");

        ui.imprimir("Digite o nome do seu Personagem: ");
        String nomePersonagem = ui.lerEntrada();

        ui.imprimir("\nBem vindo(a), " + nomePersonagem + "!");
        ui.pausar(1500);

        // FUTURAMENTE: Essa lista de Strings será substituída por instâncias da classe Item
        // vindas de um ItemFactory ou lidas do banco de dados.
        String[] equipamentos = {
            "Faca cega",
            "Arco improvisado",
            "Armadura em frangalhos",
            "Cajado velho",
            "Porrete rachado",
            "Cabo de espada",
            "Katana de madeira",
            "Cabo de lança",
            "Espada de Oito Empunhaduras Divergente Sila Divina General Mahoraga"
        };

        Random random = new Random();
        int item1 = random.nextInt(equipamentos.length);
        int item2 = random.nextInt(equipamentos.length);
        while(item1 == item2){
            item2 = random.nextInt(equipamentos.length);
        }
        int item3 = random.nextInt(equipamentos.length);
        while (item3 == item1 || item3 == item2) {
            item3 = random.nextInt(equipamentos.length);
        }

        boolean escolhendo = true;
        String equipamentoEscolhido = "";

        while (escolhendo) {
            ui.limparTerminal();
            ui.imprimir("=================================");
            ui.imprimir("         ARSENAL INICIAL         ");
            ui.imprimir("=================================");
            ui.imprimir("        Sua escolha (1-3):       ");
            ui.imprimir("---------------------------------");

            ui.espacar(31, equipamentos[item1]);
            ui.espacar(31, equipamentos[item2]);
            ui.espacar(31, equipamentos[item3]);
            ui.imprimir("---------------------------------");

            String entrada = ui.lerEntrada();

            try {
                int escolha = Integer.parseInt(entrada);
                if(escolha == 1){
                    equipamentoEscolhido = equipamentos[item1];
                    escolhendo = false;
                } else if (escolha == 2){
                    equipamentoEscolhido = equipamentos[item2];
                    escolhendo = false;
                } else if (escolha == 3){
                    equipamentoEscolhido = equipamentos[item3];
                    escolhendo = false;
                } else {
                    ui.imprimir("Escolha inválida. Os deuses exigem um número entre 1 e 3.");
                    ui.pausar(2000);
                }
            } catch(NumberFormatException e) {
                ui.imprimir("=> Entrada inválida. Digite apenas números.");
                ui.pausar(2000);
            }
        }

        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("Você empunhou: " + equipamentoEscolhido);
        ui.imprimir("A masmorra aguarda, " + nomePersonagem);
        ui.imprimir("=================================");
        ui.imprimir("\nPressione [ENTER] para iniciar a descida...");
        ui.lerEntrada();
        ui.limparTerminal();

        // O Menu delega a continuação para o Facade
        facade.orquestrarNovoJogo(nomePersonagem);
    }

    private void exibirScores(ConsoleUI ui) {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("            HIGHSCORES           ");
        ui.imprimir("=================================");
        ui.imprimir(String.format("| %-15s | %-11s |", "Personagem", "Último Piso"));
        ui.imprimir("---------------------------------");

        // FUTURAMENTE: Esses dados serão um List<Score> puxados do SaveRepository.java
        ui.imprimir(String.format("| %-15s | %-11s |", "Gandalf", "15"));
        ui.imprimir(String.format("| %-15s | %-11s |", "Arthur", "100"));
        ui.imprimir(String.format("| %-15s | %-11s |", "Gandalf", "11"));
        ui.imprimir("---------------------------------");

        ui.imprimir("\n Pressione [ENTER] para retornar ao Menu Principal...");
        ui.lerEntrada();
        ui.limparTerminal();
    }

    public void exibirFimDeJogo(String nomeJogador, ConsoleUI ui) {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("            FIM DE JOGO           ");
        ui.imprimir("=================================");
        
        // FUTURAMENTE: Os dados virão da entidade Player
        ui.imprimir(String.format("| %-15s | %-11s |", "Personagem", nomeJogador));
        //ui.imprimir(String.format("| %-15s | %-11s |", "Equipamento", equipamentoEscolhido));
        ui.imprimir(String.format("| %-15s | %-11s |", "Piso Final", "100"));
        ui.imprimir("=================================");

        ui.imprimir("\n Pressione [ENTER] para retornar ao Menu Principal...");
        ui.lerEntrada();
        ui.limparTerminal();
    }

    public static void Batalha(Player player, List<Enemy> enemiesList, String logBatalha, ConsoleUI ui) {
        boolean acao = true;
        int nivel = 1;
        int vida = 100;
        String nomeJogador = "rs da silva";

        //logBatalha = "Um grupo de inimigos bloqueia seu caminho!";
        String estadoMenu = "PRINCIPAL";
        while(acao){
            ui.limparTerminal();
            ui.imprimir("=======================================================");
            ui.imprimir(String.format("%-25s %25s", "Nível: " + String.format("%02d", nivel), enemiesList.get((1)).getName()));
            ui.imprimir(String.format("%-25s %25s", "HP: " + vida, enemiesList.get((2)).getName()));
            ui.imprimir(String.format("%-25s %25s", "", enemiesList.get((3)).getName()));

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
                        acao = false; 
                        break;
                    default: 
                        logBatalha = "Ação inválida! Escolha de 1 a 3."; 
                        break;
                }
            } else if (estadoMenu.equals("ATACAR")) {
                ui.imprimir("  1. Ataque Rápido  ");
                ui.imprimir("  2. Ataque Pesado  ");
                ui.imprimir("  3. [Voltar]  ");
                ui.imprimir("\nEscolha seu ataque (1-3): ");

                String escolha = ui.lerEntrada();
                switch (escolha) {
                    case "1":
                        logBatalha = "Você usou Ataque Rápido. Causou 15 de dano.";
                        estadoMenu = "PRINCIPAL";
                        acao = false;
                        break;
                    case "2":
                        logBatalha = "Você usou Ataque Pesado. Causou 25 de dano!";
                        estadoMenu = "PRINCIPAL";
                        acao = false;
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
                        vida = Math.min(100, vida + 20);
                        estadoMenu = "PRINCIPAL";
                        acao = false;
                        break;
                    case "2":
                        logBatalha = "Você usou Bomba de Fumaça. Os inimigos foram cegados temporariamente!";
                        estadoMenu = "PRINCIPAL";
                        acao = false;
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