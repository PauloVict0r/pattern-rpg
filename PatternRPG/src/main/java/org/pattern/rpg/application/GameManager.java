package org.pattern.rpg.application;
import java.util.ArrayList;
import java.util.List;

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
     * Ponto de entrada para um novo jogo: cria o Player e delega o combate.
     */
    public void orquestrarNovoJogo(String nomeJogador, String equipamentoEscolhido) {
        // FUTURAMENTE: Player será construído via PlayerBuilder com stats completos.
        Player player = new Player(nomeJogador);
        orquestrarCombate(player, equipamentoEscolhido);

        // Após a batalha exibe o fim de jogo
        menu.exibirFimDeJogo(nomeJogador, equipamentoEscolhido);
    }

    /**
     * Instancia TurnBattle e inicia o Loop de combate via Template Method.
     * GameManager não sabe COMO o combate acontece — apenas o inicia.
     */
    public void orquestrarCombate(Player player, String nomeArma) {
        TurnBattle batalha = new TurnBattle(player, menu, nomeArma);
        batalha.startBattle();
    }

    public void continuarJogo() {
        ui.limparTerminal();
        // FUTURAMENTE: Aqui usaremos ConnectionDB e SaveRepository para carregar o estado do jogo.
        inventoryManager.mostrarInventario(100, 1000, "Mahoraga");
    }
}