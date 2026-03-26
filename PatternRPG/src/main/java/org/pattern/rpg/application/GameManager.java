package org.pattern.rpg.application;

import org.pattern.rpg.domain.battle.TurnBattle;
import org.pattern.rpg.domain.entity.Player;
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
     * Ponto de entrada para um novo jogo: cria o Player e executa o
     * laço externo de masmorra — um novo combate por andar —
     * enquanto o player estiver vivo e não se render.
     */
    public void orquestrarNovoJogo(String nomeJogador, String equipamentoEscolhido) {
        Player player = new Player(nomeJogador);
        this.andarAtual = 1;
        this.pontuacao = 0;
        boolean rendeu = false;
        boolean savedAndQuit = false;

        while (player.isAlive() && !rendeu && !savedAndQuit) {
            // Instancia e executa um encontro para o andar atual
            TurnBattle batalha = new TurnBattle(player, menu, equipamentoEscolhido, andarAtual);
            batalha.startBattle();

            rendeu = batalha.isRendido();
            savedAndQuit = batalha.isSavedAndQuit();

            if (savedAndQuit) {
                saveRepository.saveGame(player, andarAtual, equipamentoEscolhido);
                menu.exibirMensagemSaida("Progresso salvo com sucesso! Retornando ao menu...");
                break;
            }

            // Se venceu o encontro E ainda está vivo: aplica progressão e avança
            if (player.isAlive() && !rendeu) {
                pontuacao += andarAtual * 100;
                player.incrementarStatus();

                // A cada 5 andares: restaura o HP completamente ("descansão")
                if (andarAtual % 5 == 0) {
                    player.restaurarHp();
                    menu.exibirEntreAndares(andarAtual, pontuacao, true);
                } else {
                    menu.exibirEntreAndares(andarAtual, pontuacao, false);
                }

                andarAtual++;
            }
        }

        // Ao sair do laço: se não foi salvar e sair, player morreu ou se rendeu
        if (!savedAndQuit) {
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
                // Se retornou null, ou o usuário clicou em Voltar ou Deletou algo.
                // Verificamos se ainda existem saves. Se não, saímos.
                if (saveRepository.getAllSaves().isEmpty()) return;
                
                // Se o usuário clicar em Voltar ou Cancelar a deleção, o menu retorna null.
                // Como não queremos loop infinito se ele só clicar em voltar, vamos sair se 
                // a lista não diminuiu (indicando que ele não deletou).
                // Mas para ser simples, vamos apenas permitir que ele saia se clicar em voltar.
                return; 
            }
        }
    }

    public void deletarSave(int id) {
        saveRepository.deleteSave(id);
        ui.imprimir("Save deletado com sucesso!");
        ui.pausar(1500);
        continuarJogo(); // Recarrega o menu de saves
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
            menu.exibirFimDeJogo(save.name(), save.weapon(), andarAtual);
        }
    }
}
