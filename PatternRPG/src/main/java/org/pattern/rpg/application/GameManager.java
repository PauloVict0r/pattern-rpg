package org.pattern.rpg.application;

import org.pattern.rpg.domain.battle.TurnBattle;
import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.presentation.menu.Menu;
import org.pattern.rpg.presentation.ui.ConsoleUI;

import java.util.Scanner;

public class GameManager {
    private Scanner scanner;
    private ConsoleUI ui;
    private Menu menu;
    private InventoryManager inventoryManager;
    private boolean jogoRodando;

    // Estado da sessão atual
    private int andarAtual;
    private int pontuacao;

    public GameManager() {
        this.scanner = new Scanner(System.in);
        this.ui = new ConsoleUI(scanner);
        this.menu = new Menu(ui, this);
        this.inventoryManager = new InventoryManager(ui);
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
        // FUTURAMENTE: Player será construído via PlayerBuilder com stats completos.
        Player player = new Player(nomeJogador);
        this.andarAtual = 1;
        this.pontuacao = 0;
        boolean rendeu = false;

        while (player.isAlive() && !rendeu) {
            // Instancia e executa um encontro para o andar atual
            TurnBattle batalha = new TurnBattle(player, menu, equipamentoEscolhido, andarAtual);
            batalha.startBattle();

            rendeu = batalha.isRendido();

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

        // Ao sair do laço: player morreu ou se rendeu
        menu.exibirFimDeJogo(nomeJogador, equipamentoEscolhido, andarAtual);
    }

    public void continuarJogo() {
        ui.limparTerminal();
        // FUTURAMENTE: Aqui usaremos ConnectionDB e SaveRepository para carregar o estado do jogo.
        inventoryManager.mostrarInventario(100, 1000, "Mahoraga");
    }
}