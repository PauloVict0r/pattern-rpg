package org.pattern.rpg.application;

import org.pattern.rpg.domain.battle.TurnBattle;
import org.pattern.rpg.domain.builder.PlayerBuilder;
import org.pattern.rpg.domain.builder.PlayerDirector;
import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.domain.weapon_strategy.*;
import org.pattern.rpg.infrastructure.repository.SaveRepository;
import org.pattern.rpg.presentation.menu.Menu;
import org.pattern.rpg.presentation.ui.ConsoleUI;

import java.util.Scanner;
import java.util.List;

public class GameManager {
    private Scanner scanner;
    private ConsoleUI ui;
    private Menu menu;
    private InventoryManager inventoryManager;
    private SaveRepository saveRepository;
    private boolean jogoRodando;

    // Estado da sessão atual
    private int andarAtual;
    private int pontuacao;

    public GameManager() {
        this.scanner = new Scanner(System.in);
        this.ui = new ConsoleUI(scanner);
        this.menu = new Menu(ui, this);
        this.inventoryManager = new InventoryManager(ui);
        this.saveRepository = new SaveRepository();
        this.saveRepository.initDatabase();
    }

    // -------------------------------------------------------------------------
    // Loop principal
    // -------------------------------------------------------------------------

    public void iniciarAplicacao() {
        this.jogoRodando = true;
        while (jogoRodando) {
            menu.exibirMenuPrincipal();
        }
        scanner.close();
    }

    public void encerrarJogo() {
        this.jogoRodando = false;
    }

    // -------------------------------------------------------------------------
    // Façade: orquestração das funcionalidades do jogo
    // -------------------------------------------------------------------------

    /**
     * Ponto de entrada para um novo jogo: cria o Player via Builder e Director
     * e executa o laço externo de masmorra.
     */
    public void orquestrarNovoJogo(String nomeJogador, String equipamentoEscolhido) {
        // Aplicação do padrão Builder + Director
        PlayerBuilder builder = new PlayerBuilder(Player::new);
        PlayerDirector director = new PlayerDirector();
        director.basePlayer(builder);
        builder.setName(nomeJogador);

        // Mapeamento da WeaponStrategy real baseada na escolha do Menu
        WeaponStrategy arma;
        switch (equipamentoEscolhido) {
            case "Espada Longa": arma = new LongSwordStrategy(); break;
            case "Espada Curta": arma = new ShortSwordStrategy(); break;
            case "Machado de Batalha": arma = new AxeStrategy(); break;
            case "Arco Longo": arma = new BowStrategy(); break;
            case "Adaga Furtiva": arma = new DaggerStrategy(); break;
            case "Cajado Mágico": arma = new StaffStrategy(); break;
            case "Lâmina do Dragão": arma = new DragonBladeStrategy(); break;
            case "Arco Forte": arma = new StrongBowStrategy(); break;
            case "Espada de Oito Empunhaduras": arma = new SwordStrategy(); break;
            default: arma = new PunchStrategy();
        }
        builder.setWeapon(arma);

        Player player = builder.getResult();

        this.andarAtual = 1;
        this.pontuacao = 0;
        boolean rendeu = false;
        boolean savedAndQuit = false;

        while (player.isAlive() && !rendeu && !savedAndQuit) {
            TurnBattle batalha = new TurnBattle(player, menu, equipamentoEscolhido, andarAtual);
            batalha.startBattle();

            rendeu = batalha.isRendido();
            savedAndQuit = batalha.isSavedAndQuit();

            if (savedAndQuit) {
                saveRepository.saveGame(player, andarAtual, equipamentoEscolhido);
                menu.exibirMensagemSaida("Progresso salvo com sucesso! Retornando ao menu...");
                break;
            }

            if (player.isAlive() && !rendeu) {
                pontuacao += andarAtual * 100;
                player.incrementarStatus();

                if (andarAtual % 5 == 0) {
                    player.restaurarHp();
                    menu.exibirEntreAndares(andarAtual, pontuacao, true);
                } else {
                    menu.exibirEntreAndares(andarAtual, pontuacao, false);
                }

                andarAtual++;
            }
        }

        if (!savedAndQuit) {
            if (!player.isAlive()) {
                ui.imprimir("\n[PERMADEATH] Seu personagem tombou. Jornada encerrada.");
            }
            menu.exibirFimDeJogo(nomeJogador, equipamentoEscolhido, andarAtual);
        }
    }

    public void continuarJogo() {
        while (true) {
            List<SaveRepository.SaveData> saves = saveRepository.getAllSaves();
            if (saves.isEmpty()) {
                ui.limparTerminal();
                ui.imprimir("Nenhum save encontrado!");
                ui.pausar(2000);
                return;
            }

            SaveRepository.SaveData escolhido = menu.selecionarSave(saves);
            if (escolhido != null) {
                retomarJogo(escolhido);
                return;
            } else {
                if (saveRepository.getAllSaves().isEmpty()) return;
                return; 
            }
        }
    }

    public void deletarSave(int id) {
        saveRepository.deleteSave(id);
        ui.imprimir("Save deletado com sucesso!");
        ui.pausar(1500);
    }

    public List<SaveRepository.SaveData> carregarHighScores() {
        return saveRepository.getHighScores();
    }

    private void retomarJogo(SaveRepository.SaveData save) {
        Player player = new Player(save.name(), save.hp(), save.attack(), save.defense(), save.crit());
        this.andarAtual = save.floor();
        this.pontuacao = (save.floor() - 1) * 100;
        boolean rendeu = false;
        boolean savedAndQuit = false;

        // Ao retomar, também mapeamos a arma guardada no save para a Strategy real
        WeaponStrategy arma;
        switch (save.weapon()) {
            case "Espada Longa": arma = new LongSwordStrategy(); break;
            case "Espada Curta": arma = new ShortSwordStrategy(); break;
            case "Machado de Batalha": arma = new AxeStrategy(); break;
            case "Arco Longo": arma = new BowStrategy(); break;
            case "Adaga Furtiva": arma = new DaggerStrategy(); break;
            case "Cajado Mágico": arma = new StaffStrategy(); break;
            case "Lâmina do Dragão": arma = new DragonBladeStrategy(); break;
            case "Arco Forte": arma = new StrongBowStrategy(); break;
            case "Espada de Oito Empunhaduras": arma = new SwordStrategy(); break;
            default: arma = new PunchStrategy();
        }
        player.setWeapon(arma);

        while (player.isAlive() && !rendeu && !savedAndQuit) {
            TurnBattle batalha = new TurnBattle(player, menu, save.weapon(), andarAtual);
            batalha.startBattle();

            rendeu = batalha.isRendido();
            savedAndQuit = batalha.isSavedAndQuit();

            if (savedAndQuit) {
                saveRepository.saveGame(player, andarAtual, save.weapon());
                menu.exibirMensagemSaida("Progresso salvo com sucesso! Retornando ao menu...");
                break;
            }

            if (player.isAlive() && !rendeu) {
                pontuacao += andarAtual * 100;
                player.incrementarStatus();

                if (andarAtual % 5 == 0) {
                    player.restaurarHp();
                    menu.exibirEntreAndares(andarAtual, pontuacao, true);
                } else {
                    menu.exibirEntreAndares(andarAtual, pontuacao, false);
                }
                
                andarAtual++;
            }
        }

        if (!savedAndQuit) {
            if (!player.isAlive()) {
                ui.imprimir("\n[PERMADEATH] Seu personagem tombou. Save deletado.");
                saveRepository.deleteSave(save.id());
            }
            menu.exibirFimDeJogo(save.name(), save.weapon(), andarAtual);
        }
    }
}
