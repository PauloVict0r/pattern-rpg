package org.pattern.rpg.application;

import org.pattern.rpg.domain.battle.TurnBattle;
import org.pattern.rpg.domain.builder.PlayerBuilder;
import org.pattern.rpg.domain.builder.PlayerDirector;
import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.domain.factory.ItemFactory;
import org.pattern.rpg.domain.item.Item;
import org.pattern.rpg.domain.item.wearable.*;
import org.pattern.rpg.domain.weapon_strategy.*;
import org.pattern.rpg.infrastructure.repository.SaveRepository;
import org.pattern.rpg.presentation.menu.Menu;
import org.pattern.rpg.presentation.ui.ConsoleUI;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    private Scanner scanner;
    private ConsoleUI ui;
    private Menu menu;
    private SaveRepository saveRepository;
    private boolean jogoRodando;

    private int andarAtual;
    private int pontuacao;
    private String armaduraEquipadaNome = "Nenhuma";

    public GameManager() {
        this.scanner = new Scanner(System.in);
        this.ui = new ConsoleUI(scanner);
        this.menu = new Menu(ui, this);
        this.saveRepository = new SaveRepository();
        this.saveRepository.initDatabase();
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
        PlayerBuilder builder = new PlayerBuilder(Player::new);
        PlayerDirector director = new PlayerDirector();
        director.basePlayer(builder);
        builder.setName(nomeJogador);
        builder.setInventory(new ArrayList<>());

        WeaponStrategy arma = mapearArma(equipamentoEscolhido);
        builder.setWeapon(arma);

        Player player = builder.getResult();
        player.setInventory(new ArrayList<>());

        this.andarAtual = 1;
        this.pontuacao = 0;
        this.armaduraEquipadaNome = "Nenhuma";

        loopDeJogo(player, nomeJogador, equipamentoEscolhido);
    }

    private void loopDeJogo(Entity playerEntity, String nomeOriginal, String armaNome) {
        boolean rendeu = false;
        boolean savedAndQuit = false;

        while (playerEntity.isAlive() && !rendeu && !savedAndQuit) {
            TurnBattle batalha = new TurnBattle((Player) extrairBase(playerEntity), menu, armaNome, andarAtual);
            batalha.startBattle();

            rendeu = batalha.isRendido();
            savedAndQuit = batalha.isSavedAndQuit();

            if (savedAndQuit) {
                saveRepository.saveGame((Player) extrairBase(playerEntity), andarAtual, armaNome, armaduraEquipadaNome);
                menu.exibirMensagemSaida("Progresso salvo com sucesso!");
                break;
            }

            if (playerEntity.isAlive() && !rendeu) {
                pontuacao += andarAtual * 100;
                ((Player) extrairBase(playerEntity)).incrementarStatus();

                playerEntity = processarLoot(playerEntity);

                if (andarAtual % 5 == 0) {
                    ((Player) extrairBase(playerEntity)).restaurarHp();
                    menu.exibirEntreAndares(andarAtual, pontuacao, true);
                } else {
                    menu.exibirEntreAndares(andarAtual, pontuacao, false);
                }
                andarAtual++;
            }
        }

        if (!savedAndQuit && !playerEntity.isAlive()) {
            ui.imprimir("\n[PERMADEATH] " + nomeOriginal + " tombou. O save foi removido.");
            menu.exibirFimDeJogo(nomeOriginal, armaNome, andarAtual);
        }
    }

    private Entity processarLoot(Entity player) {
        // 1. Loot de Item OBRIGATÓRIO (Factory)
        String[] possiveis = { "health potion", "strength potion", "defense potion" };
        Item novoItem = ItemFactory.createItem(possiveis[new Random().nextInt(possiveis.length)]);

        ui.imprimir("\n[RECOMPENSA] Você encontrou: " + novoItem.getName());
        List<Item> inv = ((Player) extrairBase(player)).getInventory();
        if (inv == null) {
            inv = new ArrayList<>();
            ((Player) extrairBase(player)).setInventory(inv);
        }

        if (inv.size() >= 4) {
            ui.imprimir("Seu inventário está cheio (4/4).");
            ui.imprimir("Escolha um item para SUBSTITUIR (0 para descartar o novo):");
            for (int i = 0; i < inv.size(); i++)
                ui.imprimir((i + 1) + ". " + inv.get(i).getName());

            try {
                int esc = Integer.parseInt(ui.lerEntrada());
                if (esc >= 1 && esc <= 4) {
                    inv.set(esc - 1, novoItem);
                    ui.imprimir("Item substituído!");
                }
            } catch (Exception e) {
                ui.imprimir("Item novo descartado.");
            }
        } else {
            inv.add(novoItem);
            ui.imprimir("Item guardado!");
        }
        return player;
    }

    private Entity aplicarArmadura(Entity base, String nomeArmadura) {
        switch (nomeArmadura) {
            case "Armadura de Ferro":
                return new IronArmor(base);
            case "Armadura Dourada":
                return new GoldenArmor(base);
            case "Armadura de Sombras":
                return new ShadowArmor(base);
            default:
                return base;
        }
    }

    private Entity extrairBase(Entity e) {
        if (e instanceof WearableDecorator) {
            return extrairBase(((WearableDecorator) e).getWrappedEntity());
        }
        return e;
    }

    private WeaponStrategy mapearArma(String nome) {
        switch (nome) {
            case "Espada Longa":
                return new LongSwordStrategy();
            case "Espada Curta":
                return new ShortSwordStrategy();
            case "Machado de Batalha":
                return new AxeStrategy();
            case "Arco Longo":
                return new BowStrategy();
            case "Adaga Furtiva":
                return new DaggerStrategy();
            case "Cajado Mágico":
                return new StaffStrategy();
            case "Lâmina do Dragão":
                return new DragonBladeStrategy();
            case "Arco Forte":
                return new StrongBowStrategy();
            case "Espada de Oito Empunhaduras":
                return new SwordStrategy();
            default:
                return new PunchStrategy();
        }
    }

    public void continuarJogo() {
        List<SaveRepository.SaveData> saves = saveRepository.getAllSaves();
        if (saves.isEmpty()) {
            ui.imprimir("Nenhum save encontrado!");
            ui.pausar(2000);
            return;
        }
        SaveRepository.SaveData escolhido = menu.selecionarSave(saves);
        if (escolhido != null) {
            this.andarAtual = escolhido.floor();
            this.armaduraEquipadaNome = escolhido.armor();

            Player playerBase = new Player(escolhido.name(), escolhido.hp(), escolhido.attack(), escolhido.defense(),
                    escolhido.crit());
            playerBase.setWeapon(mapearArma(escolhido.weapon()));

            // Lógica de Deserialização do Inventário
            List<Item> invCarregado = new ArrayList<>();
            if (escolhido.items() != null && !escolhido.items().isEmpty()) {
                String[] nomesItens = escolhido.items().split(",");
                for (String nome : nomesItens) {
                    if (!nome.trim().isEmpty()) {
                        invCarregado.add(ItemFactory.createItem(nome));
                    }
                }
            }
            playerBase.setInventory(invCarregado);

            Entity playerDecorado = aplicarArmadura(playerBase, armaduraEquipadaNome);
            loopDeJogo(playerDecorado, escolhido.name(), escolhido.weapon());
        }
    }

    public void deletarSave(int id) {
        saveRepository.deleteSave(id);
        ui.imprimir("Save deletado!");
        ui.pausar(1500);
    }

    public List<SaveRepository.SaveData> carregarHighScores() {
        return saveRepository.getHighScores();
    }
}
