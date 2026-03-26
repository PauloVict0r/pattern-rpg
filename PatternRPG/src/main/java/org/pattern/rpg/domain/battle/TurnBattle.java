package org.pattern.rpg.domain.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.item.Item;
import org.pattern.rpg.domain.entity.enemy.Enemy;
import org.pattern.rpg.domain.factory.EnemyFactory;
import org.pattern.rpg.presentation.menu.Menu;


public class TurnBattle extends Battle {

    private List<Enemy> enemies;
    private Player player;

    // Fila de turnos (player + inimigos)
    private List<Entity> turnQueue;
    private int currentTurnIndex;

    // Menu injetado — é a UI quem exibe, não o domínio
    private Menu menu;

    // Arma inicial do player (string placeholder até integrar WeaponStrategy)
    private String nomeArmaPlayer;

    // Andar atual da masmorra — controla a dificuldade do spawn
    private int andarAtual;

    // Flag de rendição/fuga
    private boolean rendeu;

    // Flag de save and quit
    private boolean savedAndQuit;

    // Log de combate exibido no HUD
    private String logBatalha;

    public TurnBattle(Player player, Menu menu, String nomeArmaPlayer, int andarAtual) {
        this.player = player;
        this.menu = menu;
        this.nomeArmaPlayer = nomeArmaPlayer;
        this.andarAtual = andarAtual;
        this.logBatalha = "Um grupo de inimigos bloqueia seu caminho!";
    }

    @Override
    protected List<Enemy> createEnemies() {
        // Inimigos comuns: disponíveis desde o andar 1
        List<String> tiposComuns = new java.util.ArrayList<>(java.util.Arrays.asList(
                "goblin", "wolf", "skeleton", "hollow"
        ));

        // Inimigos de elite: só aparecem a partir do andar 6
        if (andarAtual >= 6) {
            tiposComuns.add("vampire");
            tiposComuns.add("dragon");
        }

        List<Enemy> spawnados = new ArrayList<>();
        int numInimigos = new Random().nextInt(3) + 1; // 1 a 3

        for (int i = 0; i < numInimigos; i++) {
            String tipo = tiposComuns.get(new Random().nextInt(tiposComuns.size()));
            spawnados.add(EnemyFactory.createEnemy(tipo));
        }
        return spawnados;
    }

    @Override
    protected void setup() {
        this.battleState = 0; // em andamento
        this.rendeu = false;
        this.savedAndQuit = false;
        this.enemies = createEnemies();

        this.turnQueue = new ArrayList<>();
        this.turnQueue.add(player);
        this.turnQueue.addAll(enemies);
        this.currentTurnIndex = 0;
    }

    @Override
    protected Entity nextTurn() {
        Entity atual = turnQueue.get(currentTurnIndex);
        currentTurnIndex = (currentTurnIndex + 1) % turnQueue.size();

        // Pula entidades mortas
        int tentativas = 0;
        while (!atual.isAlive() && tentativas < turnQueue.size()) {
            atual = turnQueue.get(currentTurnIndex);
            currentTurnIndex = (currentTurnIndex + 1) % turnQueue.size();
            tentativas++;
        }
        return atual;
    }

    @Override
    protected void executeTurn(Entity entidade) {
        if (entidade instanceof Player) {
            executarTurnoPlayer();
        } else if (entidade instanceof Enemy) {
            executarTurnoInimigo((Enemy) entidade);
        }
    }

    // -------------------------------------------------------------------------
    // Lógica do turno do Player
    // -------------------------------------------------------------------------
    private void executarTurnoPlayer() {
        boolean acaoRealizada = false;
        String estadoMenu = "PRINCIPAL";

        while (!acaoRealizada) {
            menu.exibirTelaBatalha(player, enemies, logBatalha);

            if (estadoMenu.equals("PRINCIPAL")) {
                String escolha = menu.exibirMenuPrincipalBatalha(player.getName());
                switch (escolha) {
                    case "1": estadoMenu = "ATACAR"; break;
                    case "2": estadoMenu = "ITEM";   break;
                    case "3":
                        logBatalha = player.getName() + " se rendeu e recuou...";
                        this.rendeu = true;
                        acaoRealizada = true;
                        break;
                    case "4":
                        logBatalha = player.getName() + " decidiu salvar o progresso e sair...";
                        this.savedAndQuit = true;
                        acaoRealizada = true;
                        break;
                    default:
                        logBatalha = "Ação inválida! Escolha de 1 a 4.";
                }

            } else if (estadoMenu.equals("ATACAR")) {
                String escolha = menu.exibirMenuAtacar(nomeArmaPlayer);
                switch (escolha) {
                    case "1": // Ataque Rápido
                        Enemy alvoRapido = primeiroInimigoVivo();
                        if (alvoRapido != null) {
                            int dano = player.attack(alvoRapido); 
                            logBatalha = player.getName() + " usou Ataque Rápido em "
                                         + alvoRapido.getName() + ". Causou " + dano + " de dano.";
                        }
                        acaoRealizada = true;
                        break;
                    case "2": // Ataque Pesado
                        Enemy alvoPesado = primeiroInimigoVivo();
                        if (alvoPesado != null) {
                            // Simulando um ataque pesado dobrando o dano base da arma
                            int danoBase = player.getAttack();
                            player.setAttack(danoBase * 2);
                            int dano = player.attack(alvoPesado);
                            player.setAttack(danoBase); // Volta ao normal
                            
                            logBatalha = player.getName() + " usou Ataque Pesado em "
                                         + alvoPesado.getName() + ". Causou " + dano + " de dano!";
                        }
                        acaoRealizada = true;
                        break;
                    case "3": estadoMenu = "PRINCIPAL"; break;
                    default:
                        logBatalha = "Ataque inválido!";
                }

            } else if (estadoMenu.equals("ITEM")) {
                List<Item> inventario = player.getInventory();
                String escolha = menu.exibirMenuItens(inventario);
                
                try {
                    int idx = Integer.parseInt(escolha) - 1;
                    if (idx >= 0 && idx < inventario.size()) {
                        Item itemUsado = inventario.get(idx);
                        if (itemUsado instanceof org.pattern.rpg.domain.item.usable.Usable) {
                            ((org.pattern.rpg.domain.item.usable.Usable) itemUsado).use(player);
                            logBatalha = player.getName() + " usou " + itemUsado.getName() + "!";
                            inventario.remove(idx); // Remove após usar
                            acaoRealizada = true;
                        } else {
                            logBatalha = "Este item não pode ser usado agora.";
                        }
                    } else {
                        estadoMenu = "PRINCIPAL"; // Voltou ou opção inválida
                    }
                } catch (NumberFormatException e) {
                    estadoMenu = "PRINCIPAL";
                }
            }
        }
    }

    // -------------------------------------------------------------------------
    // Lógica do turno do Inimigo
    // -------------------------------------------------------------------------
    private void executarTurnoInimigo(Enemy inimigo) {
        if (inimigo.isDead()) return; // segurança: não age se morto

        int danoRecebido = inimigo.attackTarget(player);
        logBatalha = inimigo.getName() + " atacou " + player.getName()
                     + " e causou " + danoRecebido + " de dano!";

        menu.exibirTelaBatalha(player, enemies, logBatalha);
        // Pequena pausa para o jogador ver o ataque do inimigo
        try { Thread.sleep(1200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // -------------------------------------------------------------------------
    // Condição de fim e resultado
    // -------------------------------------------------------------------------
    @Override
    protected boolean isOver() {
        if (savedAndQuit) {
            this.battleState = 4; // fuga/rendição
            return true;
        }

        if (rendeu) {
            this.battleState = 3; // fuga/rendição
            return true;
        }

        if (!player.isAlive()) {
            this.battleState = 2; // derrota
            return true;
        }

        boolean todosInimigos = enemies.stream().allMatch(Enemy::isDead);
        if (todosInimigos) {
            this.battleState = 1; // vitória
            return true;
        }

        return false;
    }

    @Override
    protected void finish() {
        if (battleState == 1) {
            menu.exibirResultadoBatalha(true, player.getName());
        } else if (battleState == 3) {
            // rendição: apenas informa, GameManager cuidará do fim de jogo
            menu.exibirResultadoBatalha(false, player.getName() + " (se rendeu)");
        }
        // battleState == 4 não faz nada, GameManager exibe mensagem e volta pro menu
        // battleState == 2 (derrota do player): GameManager exibe o fim de jogo
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------
    private Enemy primeiroInimigoVivo() {
        for (Enemy e : enemies) {
            if (!e.isDead()) return e;
        }
        return null;
    }

    /** Expõe o estado final: 0 = em andamento, 1 = vitória, 2 = derrota, 3 = rendição, 4 = saved and quit. */
    public int getEstadoBatalha() {
        return battleState;
    }

    /** Retorna true se o player se rendeu/fugiu neste encontro. */
    public boolean isRendido() {
        return rendeu;
    }

    public boolean isSavedAndQuit() {
        return savedAndQuit;
    }
}