package org.pattern.rpg.application;

<<<<<<< HEAD
import org.pattern.rpg.domain.entity.Save;
import org.pattern.rpg.infrastructure.database.SaveRepository;
import org.pattern.rpg.presentation.menu.Menu;
import org.pattern.rpg.presentation.ui.ConsoleUI;
<<<<<<< HEAD
import org.pattern.rpg.domain.battle.*;
import org.pattern.rpg.domain.entity.*;
=======

import java.util.List;
>>>>>>> c1cf550de9946faab49543fd234cc0bb1ea0c7f9
=======
import org.pattern.rpg.domain.battle.TurnBattle;
import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.presentation.menu.Menu;
import org.pattern.rpg.presentation.ui.ConsoleUI;

>>>>>>> 4543bc425957374648bc203e70ece873fc2b3385
import java.util.Scanner;

public class GameManager {

    private Scanner scanner;
    private ConsoleUI ui;
    private Menu menu;
<<<<<<< HEAD
<<<<<<< HEAD
    private InventoryManager inventoryManager; // Adicionado
=======
    private InventoryManager inventoryManager;
>>>>>>> 4543bc425957374648bc203e70ece873fc2b3385
    private boolean jogoRodando;
    private TurnBattle battle;
    private Player player;

    // Estado da sessão atual
    private int andarAtual;
    private int pontuacao;

    public GameManager() {
        this.scanner = new Scanner(System.in);
        this.player = new Player();
        this.ui = new ConsoleUI(scanner);
        this.menu = new Menu(ui, this);
<<<<<<< HEAD
        this.inventoryManager = new InventoryManager(ui); // Inicializado
        this.battle = new TurnBattle(player, scanner, ui);
=======
        this.inventoryManager = new InventoryManager(ui);
>>>>>>> 4543bc425957374648bc203e70ece873fc2b3385
    }

    // -------------------------------------------------------------------------
    // Loop principal
    // -------------------------------------------------------------------------

    public void iniciarAplicacao() {
        this.jogoRodando = true;
        while (jogoRodando) {
            menu.exibirMenuPrincipal(ui);
=======
    private BattleManager battleManager;
    private InventoryManager inventoryManager;
    private SaveRepository saveRepository;
    private boolean isRunning;

    private int currentFloor;
    private int currentScore;
    private String[] currentItems;

    public GameManager() {
        scanner = new Scanner(System.in);
        ui = new ConsoleUI(scanner);
        menu = new Menu(ui, this);
        battleManager = new BattleManager(ui);
        inventoryManager = new InventoryManager(ui);
        saveRepository = new SaveRepository();

        currentFloor = 1;
        currentScore = 0;
        currentItems = new String[]{"Sword", "Potion"};
    }

    public void startApplication() {
        isRunning = true;
        while (isRunning) {
            menu.showMainMenu();
>>>>>>> c1cf550de9946faab49543fd234cc0bb1ea0c7f9
        }
    }

    public void stopGame() {
        isRunning = false;
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public void orquestrarNovoJogo(String nomeJogador) {
        battle.startBattle();
        
        // Chamando o Fim de Jogo logo após a batalha, exatamente como no seu código original
        menu.exibirFimDeJogo(player.getName(), ui);
=======
    public void startNewGame(String name, String equipment) {

        currentFloor = 1;
        currentScore = 100;
        currentItems = new String[]{equipment, "Potion"};

        battleManager.startTestBattle(name, equipment);

        currentFloor = 2;
        currentScore = 250;

        menu.showGameOver(name, equipment);
>>>>>>> c1cf550de9946faab49543fd234cc0bb1ea0c7f9
    }

    public void continueGame() {
        inventoryManager.showInventory(100, currentScore, "Player");
=======
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

                // Incrementa levemente os status do player a cada andar vencido
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
>>>>>>> 4543bc425957374648bc203e70ece873fc2b3385
    }

    // SAVE

    public List<Save> listSaves() {
        return saveRepository.listAllSaves();
    }

    public Save loadSave(int id) {
        return saveRepository.loadSave(id);
    }

    public void applyLoadedSave(Save save) {
        if (save == null) return;

        currentFloor = save.getFloor();
        currentScore = save.getScore();
        currentItems = save.getItems();
    }

    public void createSave() {
        saveRepository.createSave(new Save(currentItems, currentFloor, currentScore));
    }

    public void overwriteSave(int id) {
        saveRepository.overwriteSave(new Save(id, currentItems, currentFloor, currentScore));
    }

    public void deleteSave(int id) {
        saveRepository.deleteSave(id);
    }

    public int getCurrentFloor() { return currentFloor; }
    public int getCurrentScore() { return currentScore; }
}