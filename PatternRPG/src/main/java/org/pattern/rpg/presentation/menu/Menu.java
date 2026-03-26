package org.pattern.rpg.presentation.menu;

import org.pattern.rpg.application.GameManager;
import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.entity.enemy.Enemy;
import org.pattern.rpg.infrastructure.repository.SaveRepository;
import org.pattern.rpg.presentation.ui.ConsoleUI;

import java.util.List;
import java.util.Random;

public class Menu {
    private ConsoleUI ui;
    private GameManager facade;

    public Menu(ConsoleUI ui, GameManager facade) {
        this.ui = ui;
        this.facade = facade;
    }

    // =========================================================================
    // MENU PRINCIPAL DO JOGO
    // =========================================================================

    public void exibirMenuPrincipal() {
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
                    facade.continuarJogo();
                    break;
                case 2:
                    ui.imprimir("=> Opção escolhida: Iniciar NOVO JOGO.");
                    criarNovoPersonagem();
                    break;
                case 3:
                    ui.imprimir("=> Opção escolhida: Visualizar SCORES.");
                    exibirScores();
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
        while (item1 == item2) {
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
                if (escolha == 1) {
                    equipamentoEscolhido = equipamentos[item1];
                    escolhendo = false;
                } else if (escolha == 2) {
                    equipamentoEscolhido = equipamentos[item2];
                    escolhendo = false;
                } else if (escolha == 3) {
                    equipamentoEscolhido = equipamentos[item3];
                    escolhendo = false;
                } else {
                    ui.imprimir("Escolha inválida. Os deuses exigem um número entre 1 e 3.");
                    ui.pausar(2000);
                }
            } catch (NumberFormatException e) {
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
        facade.orquestrarNovoJogo(nomePersonagem, equipamentoEscolhido);
    }

    private void exibirScores() {
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

    // =========================================================================
    // UI ENTRE ANDARES
    // =========================================================================

    /**
     * Exibido após cada vitória de encontro, antes de iniciar o próximo andar.
     */
    public void exibirEntreAndares(int andarConcluido, int pontuacaoAtual, boolean descansou) {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("       ANDAR  " + andarConcluido + "  CONCLUÍDO!      ");
        ui.imprimir("=================================");
        ui.imprimir(String.format("  Pontuação acumulada: %-8d", pontuacaoAtual));
        ui.imprimir("---------------------------------");
        if (descansou) {
            ui.imprimir("  *** DESCANSO! HP restaurado! ***");
            ui.imprimir("---------------------------------");
        }
        ui.imprimir("  Próximo andar: " + (andarConcluido + 1));
        if (andarConcluido + 1 == 6) {
            ui.imprimir("  ⚠ Inimigos mais poderosos se aproximam!");
        }
        ui.imprimir("=================================");
        ui.imprimir("\n Pressione [ENTER] para descer...");
        ui.lerEntrada();
        ui.limparTerminal();
    }

    public void exibirFimDeJogo(String nomeJogador, String equipamentoEscolhido, int andarAtual) {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("            FIM DE JOGO           ");
        ui.imprimir("=================================");

        // FUTURAMENTE: Os dados virão da entidade Player
        ui.imprimir(String.format("| %-15s | %-11s |", "Personagem", nomeJogador));
        ui.imprimir(String.format("| %-15s | %-11s |", "Equipamento", equipamentoEscolhido));
        ui.imprimir(String.format("| %-15s | %-11s |", "Piso Final", andarAtual));
        ui.imprimir("=================================");

        ui.imprimir("\n Pressione [ENTER] para retornar ao Menu Principal...");
        ui.lerEntrada();
        ui.limparTerminal();
    }

    public SaveRepository.SaveData selecionarSave(List<SaveRepository.SaveData> saves) {
        ui.limparTerminal();
        ui.imprimir("=================================");
        ui.imprimir("        CARREGAR JOGO            ");
        ui.imprimir("=================================");
        ui.imprimir("0. [DELETAR SAVE]");
        for (int i = 0; i < saves.size(); i++) {
            SaveRepository.SaveData s = saves.get(i);
            ui.imprimir((i + 1) + ". " + s.name() + " (Andar: " + s.floor() + " | HP: " + s.hp() + ")");
        }
        ui.imprimir((saves.size() + 1) + ". [Voltar]");
        ui.imprimir("\nEscolha o save (0-" + (saves.size() + 1) + "): ");

        String entrada = ui.lerEntrada();
        try {
            int opcao = Integer.parseInt(entrada);
            if (opcao == 0) {
                // Retorna um objeto "fake" ou nulo com sinalização para o GameManager
                // Mas vamos simplificar: o Menu pergunta qual deletar.
                ui.imprimir("\nDigite o número da lista (1-" + saves.size() + ") que deseja APAGAR (ou Enter para cancelar):");
                String paraApagar = ui.lerEntrada();
                if (paraApagar.isEmpty()) return null;
                
                int idx = Integer.parseInt(paraApagar);
                if (idx >= 1 && idx <= saves.size()) {
                    SaveRepository.SaveData saveParaDeletar = saves.get(idx - 1);
                    facade.deletarSave(saveParaDeletar.id());
                    return null; // Força recarregar a lista
                }
            } else if (opcao >= 1 && opcao <= saves.size()) {
                return saves.get(opcao - 1);
            }
        } catch (NumberFormatException ignored) {}

        return null;
    }

    // =========================================================================
    // UI DE BATALHA — centralizada aqui conforme responsabilidade de apresentação
    // =========================================================================

    /**
     * Exibe o HUD completo da batalha: status do player, dos inimigos e o log de combate.
     */
    public void exibirTelaBatalha(Entity player, List<Enemy> enemies, String logBatalha) {
        ui.limparTerminal();
        ui.imprimir("=======================================================");

        StringBuilder cabecalhoInimigos = new StringBuilder();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            String info = e.getName() + " (HP: " + (e.isDead() ? "MORTO" : e.getHealth()) + ")";
            cabecalhoInimigos.append(info);
            if (i < enemies.size() - 1) cabecalhoInimigos.append(" | ");
        }

        ui.imprimir(String.format("%-25s %25s", "HP: " + player.getHP(), cabecalhoInimigos.toString()));
        ui.imprimir("-------------------------------------------------------");

        int espacosEsquerda = Math.max(0, (55 - logBatalha.length()) / 2);
        ui.imprimir(" ".repeat(espacosEsquerda) + logBatalha);
        ui.imprimir("-------------------------------------------------------");
    }

    /**
     * Menu principal de ação durante o combate. Retorna a escolha do jogador ("1"-"4").
     */
    public String exibirMenuPrincipalBatalha(String nomeJogador) {
        ui.imprimir("  1. Atacar      |  3. Fugir");
        ui.imprimir("  2. Usar item   |  4. Salvar e sair");
        ui.imprimir("\nO que " + nomeJogador + " fará? (1-4): ");
        return ui.lerEntrada();
    }

    public void exibirMensagemSaida(String mensagem) {
        ui.limparTerminal();
        ui.imprimir("=======================================================");
        ui.imprimir("  " + mensagem);
        ui.imprimir("=======================================================");
        ui.imprimir("\nPressione [ENTER] para continuar...");
        ui.lerEntrada();
        ui.limparTerminal();
    }

    /**
     * Submenu de ataque. Retorna a escolha do jogador ("1"-"3").
     */
    public String exibirMenuAtacar(String nomeArma) {
        ui.imprimir("  1. Ataque Rápido  (" + nomeArma + ")");
        ui.imprimir("  2. Ataque Pesado  (" + nomeArma + ")");
        ui.imprimir("  3. [Voltar]");
        ui.imprimir("\nEscolha seu ataque (1-3): ");
        return ui.lerEntrada();
    }

    /**
     * Submenu de itens. Retorna a escolha do jogador ("1"-"3").
     */
    public String exibirMenuItens() {
        ui.imprimir("  1. Poção de Vida Pequena");
        ui.imprimir("  2. Bomba de Fumaça");
        ui.imprimir("  3. [Voltar]");
        ui.imprimir("\nEscolha o item (1-3): ");
        return ui.lerEntrada();
    }

    /**
     * Exibe o resultado final da batalha (vitória ou derrota) e aguarda ENTER.
     */
    public void exibirResultadoBatalha(boolean vitoria, String nomeJogador) {
        ui.limparTerminal();
        ui.imprimir("=======================================================");
        if (vitoria) {
            ui.imprimir("                    *** VITÓRIA! ***");
            ui.imprimir("  " + nomeJogador + " venceu todos os inimigos!");
        } else {
            ui.imprimir("                    *** DERROTA... ***");
            ui.imprimir("  " + nomeJogador + " foi derrotado(a)...");
        }
        ui.imprimir("=======================================================");
        ui.imprimir("\nPressione [ENTER] para continuar...");
        ui.lerEntrada();
        ui.limparTerminal();
    }
}