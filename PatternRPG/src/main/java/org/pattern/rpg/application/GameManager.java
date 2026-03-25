package org.pattern.rpg.application;

import org.pattern.rpg.presentation.menu.Menu;
import org.pattern.rpg.presentation.ui.ConsoleUI;
import java.util.Scanner;

public class GameManager {
    private Scanner scanner;
    private ConsoleUI ui;
    private Menu menu;
    private BattleManager battleManager;
    private InventoryManager inventoryManager; // Adicionado
    private boolean jogoRodando;

    public GameManager() {
        this.scanner = new Scanner(System.in);
        this.ui = new ConsoleUI(scanner);
        this.menu = new Menu(ui, this);
        this.battleManager = new BattleManager(ui);
        this.inventoryManager = new InventoryManager(ui); // Inicializado
    }

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

    public void orquestrarNovoJogo(String nomeJogador, String equipamentoEscolhido) {
        // FUTURAMENTE: 
        // 1. Criar o Player usando PlayerBuilder: Player p = new PlayerBuilder().withName(nomeJogador)...
        // 2. Passar o objeto Player para o BattleManager e para o fim de jogo
        
        // Chamando a Batalha
        battleManager.iniciarBatalhaTeste(nomeJogador, equipamentoEscolhido);
        
        // Chamando o Fim de Jogo logo após a batalha, exatamente como no seu código original
        menu.exibirFimDeJogo(nomeJogador, equipamentoEscolhido);
    }

    public void continuarJogo() {
        ui.limparTerminal();
        // FUTURAMENTE:
        // Aqui usaremos ConnectionDB e SaveRepository para carregar o estado do jogo.
        // Por enquanto, apenas abrimos o inventário estático como no case 1 original.
        inventoryManager.mostrarInventario(100, 1000, "Mahoraga");
    }
}