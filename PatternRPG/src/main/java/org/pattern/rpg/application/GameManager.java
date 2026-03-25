package org.pattern.rpg.application;

import org.pattern.rpg.presentation.menu.Menu;
import org.pattern.rpg.presentation.ui.ConsoleUI;
import org.pattern.rpg.domain.battle.*;
import org.pattern.rpg.domain.entity.*;
import java.util.Scanner;

public class GameManager {
    private Scanner scanner;
    private ConsoleUI ui;
    private Menu menu;
    private InventoryManager inventoryManager; // Adicionado
    private boolean jogoRodando;
    private TurnBattle battle;
    private Player player;

    public GameManager() {
        this.scanner = new Scanner(System.in);
        this.player = new Player();
        this.ui = new ConsoleUI(scanner);
        this.menu = new Menu(ui, this);
        this.inventoryManager = new InventoryManager(ui); // Inicializado
        this.battle = new TurnBattle(player, scanner, ui);
    }

    public void iniciarAplicacao() {
        this.jogoRodando = true;
        while (jogoRodando) {
            menu.exibirMenuPrincipal(ui);
        }
        scanner.close();
    }

    public void encerrarJogo() {
        this.jogoRodando = false;
    }

    public void orquestrarNovoJogo(String nomeJogador) {
        battle.startBattle();
        
        // Chamando o Fim de Jogo logo após a batalha, exatamente como no seu código original
        menu.exibirFimDeJogo(player.getName(), ui);
    }

    public void continuarJogo() {
        ui.limparTerminal();
        // FUTURAMENTE:
        // Aqui usaremos ConnectionDB e SaveRepository para carregar o estado do jogo.
        // Por enquanto, apenas abrimos o inventário estático como no case 1 original.
        inventoryManager.mostrarInventario(100, 1000, "Mahoraga");
    }
}