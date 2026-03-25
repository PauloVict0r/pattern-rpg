package org.pattern.rpg.presentation.menu;

import org.pattern.rpg.application.GameManager;
import org.pattern.rpg.domain.entity.Save;
import org.pattern.rpg.presentation.ui.ConsoleUI;

import java.util.List;
import java.util.Random;

public class Menu {

    private final ConsoleUI ui;
    private final GameManager facade;

    public Menu(ConsoleUI ui, GameManager facade) {
        this.ui = ui;
        this.facade = facade;
    }

    public void showMainMenu() {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("      BEM-VINDO VIAJANTE!        ");
        ui.imprimir("=================================");
        ui.imprimir("\n--- MENU PRINCIPAL ---");
        ui.imprimir("1. Iniciar Jogo");
        ui.imprimir("2. Scores");
        ui.imprimir("3. Sair");
        ui.imprimir("Escolha uma opção (1-3): ");

        String input = ui.lerEntrada();

        try {
            int option = Integer.parseInt(input);

            switch (option) {
                case 1:
                    showStartGameMenu();
                    break;
                case 2:
                    showScores();
                    break;
                case 3:
                    ui.imprimir("=> Encerrando o jogo. Até a próxima aventura!");
                    ui.pausar(1000);
                    facade.stopGame();
                    break;
                default:
                    ui.imprimir("=> Opção inválida! Digite um número de 1 a 3.");
                    ui.pausar(2000);
                    break;
            }
        } catch (NumberFormatException e) {
            ui.imprimir("=> Entrada inválida! Digite apenas números.");
            ui.pausar(2000);
        }
    }

    private void showStartGameMenu() {
        boolean running = true;

        while (running) {
            ui.limparTerminal();
            ui.imprimir("=================================");
            ui.imprimir("          INICIAR JOGO           ");
            ui.imprimir("=================================");
            ui.imprimir("1. Novo Jogo");
            ui.imprimir("2. Carregar Jogo");
            ui.imprimir("3. Voltar");
            ui.imprimir("Escolha uma opção (1-3): ");

            String input = ui.lerEntrada();

            try {
                int option = Integer.parseInt(input);

                switch (option) {
                    case 1:
                        createNewCharacter();
                        running = false;
                        break;
                    case 2:
                        showLoadGameScreen();
                        running = false;
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        ui.imprimir("=> Opção inválida! Digite um número de 1 a 3.");
                        ui.pausar(2000);
                        break;
                }
            } catch (NumberFormatException e) {
                ui.imprimir("=> Entrada inválida! Digite apenas números.");
                ui.pausar(2000);
            }
        }
    }

    private void showLoadGameScreen() {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("          CARREGAR JOGO          ");
        ui.imprimir("=================================");

        List<Save> saves = facade.listSaves();

        if (saves.isEmpty()) {
            ui.imprimir("Nenhum save encontrado.");
            ui.imprimir("\nPressione [ENTER] para voltar...");
            ui.lerEntrada();
            return;
        }

        ui.imprimir(String.format("| %-5s | %-10s | %-10s |", "ID", "PISO", "SCORE"));
        ui.imprimir("-----------------------------------------");

        for (Save save : saves) {
            ui.imprimir(String.format("| %-5d | %-10d | %-10d |",
                    save.getId(),
                    save.getFloor(),
                    save.getScore()));
        }

        ui.imprimir("-----------------------------------------");
        ui.imprimir("Digite o ID do save que deseja carregar ou 0 para voltar:");

        try {
            int selectedId = Integer.parseInt(ui.lerEntrada());

            if (selectedId == 0) {
                return;
            }

            Save loadedSave = facade.loadSave(selectedId);

            if (loadedSave == null) {
                ui.imprimir("Save não encontrado.");
                ui.imprimir("\nPressione [ENTER] para continuar...");
                ui.lerEntrada();
                return;
            }

            facade.applyLoadedSave(loadedSave);

            ui.imprimir("Save carregado com sucesso!");
            ui.imprimir("Piso atual: " + loadedSave.getFloor());
            ui.imprimir("Score atual: " + loadedSave.getScore());
            ui.imprimir("\nPressione [ENTER] para continuar...");
            ui.lerEntrada();

            facade.continueGame();

        } catch (NumberFormatException e) {
            ui.imprimir("Entrada inválida! Digite apenas números.");
            ui.pausar(2000);
        }
    }

    private void createNewCharacter() {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("            NOVO JOGO            ");
        ui.imprimir("=================================");

        ui.imprimir("Digite o nome do seu personagem: ");
        String playerName = ui.lerEntrada();

        ui.imprimir("\nBem-vindo(a), " + playerName + "!");
        ui.pausar(1500);

        String[] equipments = {
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
        int item1 = random.nextInt(equipments.length);
        int item2 = random.nextInt(equipments.length);
        while (item1 == item2) {
            item2 = random.nextInt(equipments.length);
        }
        int item3 = random.nextInt(equipments.length);
        while (item3 == item1 || item3 == item2) {
            item3 = random.nextInt(equipments.length);
        }

        boolean choosing = true;
        String chosenEquipment = "";

        while (choosing) {
            ui.limparTerminal();
            ui.imprimir("=================================");
            ui.imprimir("         ARSENAL INICIAL         ");
            ui.imprimir("=================================");
            ui.imprimir("1. " + equipments[item1]);
            ui.imprimir("2. " + equipments[item2]);
            ui.imprimir("3. " + equipments[item3]);
            ui.imprimir("---------------------------------");
            ui.imprimir("Escolha seu equipamento (1-3): ");

            String input = ui.lerEntrada();

            try {
                int choice = Integer.parseInt(input);

                if (choice == 1) {
                    chosenEquipment = equipments[item1];
                    choosing = false;
                } else if (choice == 2) {
                    chosenEquipment = equipments[item2];
                    choosing = false;
                } else if (choice == 3) {
                    chosenEquipment = equipments[item3];
                    choosing = false;
                } else {
                    ui.imprimir("Escolha inválida. Digite um número entre 1 e 3.");
                    ui.pausar(2000);
                }
            } catch (NumberFormatException e) {
                ui.imprimir("Entrada inválida. Digite apenas números.");
                ui.pausar(2000);
            }
        }

        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("Você escolheu: " + chosenEquipment);
        ui.imprimir("A masmorra aguarda, " + playerName + "!");
        ui.imprimir("=================================");
        ui.imprimir("\nPressione [ENTER] para iniciar...");
        ui.lerEntrada();

        facade.startNewGame(playerName, chosenEquipment);
    }

    private void showScores() {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("            HIGHSCORES           ");
        ui.imprimir("=================================");
        ui.imprimir(String.format("| %-15s | %-11s |", "Personagem", "Último Piso"));
        ui.imprimir("---------------------------------");
        ui.imprimir(String.format("| %-15s | %-11s |", "Gandalf", "15"));
        ui.imprimir(String.format("| %-15s | %-11s |", "Arthur", "100"));
        ui.imprimir(String.format("| %-15s | %-11s |", "Merlin", "11"));
        ui.imprimir("---------------------------------");

        ui.imprimir("\nPressione [ENTER] para retornar ao menu principal...");
        ui.lerEntrada();
    }

    public void showGameOver(String playerName, String chosenEquipment) {
        boolean onGameOverScreen = true;

        while (onGameOverScreen) {
            ui.limparTerminal();
            ui.imprimir("=================================");
            ui.imprimir("            FIM DE JOGO          ");
            ui.imprimir("=================================");
            ui.imprimir(String.format("| %-15s | %-11s |", "Personagem", playerName));
            ui.imprimir(String.format("| %-15s | %-11s |", "Equipamento", chosenEquipment));
            ui.imprimir(String.format("| %-15s | %-11s |", "Piso Final", String.valueOf(facade.getCurrentFloor())));
            ui.imprimir(String.format("| %-15s | %-11s |", "Pontuação", String.valueOf(facade.getCurrentScore())));
            ui.imprimir("=================================");
            ui.imprimir("1. Criar novo save");
            ui.imprimir("2. Sobrescrever save existente");
            ui.imprimir("3. Voltar ao menu principal");
            ui.imprimir("Escolha uma opção (1-3): ");

            String input = ui.lerEntrada();

            try {
                int option = Integer.parseInt(input);

                switch (option) {
                    case 1:
                        facade.createSave();
                        ui.imprimir("Save criado com sucesso!");
                        ui.imprimir("\nPressione [ENTER] para continuar...");
                        ui.lerEntrada();
                        onGameOverScreen = false;
                        break;

                    case 2:
                        List<Save> saves = facade.listSaves();

                        if (saves.isEmpty()) {
                            ui.imprimir("Nenhum save encontrado para sobrescrever.");
                            ui.imprimir("\nPressione [ENTER] para continuar...");
                            ui.lerEntrada();
                            break;
                        }

                        ui.imprimir(String.format("| %-5s | %-10s | %-10s |", "ID", "PISO", "SCORE"));
                        ui.imprimir("-----------------------------------------");

                        for (Save save : saves) {
                            ui.imprimir(String.format("| %-5d | %-10d | %-10d |",
                                    save.getId(),
                                    save.getFloor(),
                                    save.getScore()));
                        }

                        ui.imprimir("-----------------------------------------");
                        ui.imprimir("Digite o ID do save que deseja sobrescrever:");

                        int overwriteId = Integer.parseInt(ui.lerEntrada());
                        facade.overwriteSave(overwriteId);

                        ui.imprimir("Save sobrescrito com sucesso!");
                        ui.imprimir("\nPressione [ENTER] para continuar...");
                        ui.lerEntrada();
                        onGameOverScreen = false;
                        break;

                    case 3:
                        onGameOverScreen = false;
                        break;

                    default:
                        ui.imprimir("Opção inválida!");
                        ui.pausar(2000);
                        break;
                }

            } catch (NumberFormatException e) {
                ui.imprimir("Entrada inválida! Digite apenas números.");
                ui.pausar(2000);
            }
        }

        ui.limparTerminal();
    }
}