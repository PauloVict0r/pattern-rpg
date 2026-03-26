package org.pattern.rpg.domain.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.domain.entity.Entity;
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

    // Flag de fuga
    private boolean fugiuDaBatalha;

    // Log de combate exibido no HUD
    private String logBatalha;

    public TurnBattle(Player player, Menu menu, String nomeArmaPlayer) {
        this.player = player;
        this.menu = menu;
        this.nomeArmaPlayer = nomeArmaPlayer;
        this.logBatalha = "Um grupo de inimigos bloqueia seu caminho!";
    }

    @Override
    protected List<Enemy> createEnemies() {
        List<String> tipos = List.of("goblin", "wolf", "skeleton", "vampire", "dragon", "hollow");
        List<Enemy> spawnados = new ArrayList<>();
        int numInimigos = new Random().nextInt(3) + 1; // 1 a 3

        for (int i = 0; i < numInimigos; i++) {
            String tipo = tipos.get(new Random().nextInt(tipos.size()));
            spawnados.add(EnemyFactory.createEnemy(tipo));
        }
        return spawnados;
    }

    @Override
    protected void setup() {
        this.battleState = 0; // em andamento
        this.fugiuDaBatalha = false;
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
                        logBatalha = player.getName() + " fugiu covardemente...";
                        this.fugiuDaBatalha = true;
                        acaoRealizada = true;
                        break;
                    default:
                        logBatalha = "Ação inválida! Escolha de 1 a 3.";
                }

            } else if (estadoMenu.equals("ATACAR")) {
                String escolha = menu.exibirMenuAtacar(nomeArmaPlayer);
                switch (escolha) {
                    case "1": // Ataque Rápido
                        Enemy alvoRapido = primeiroInimigoVivo();
                        if (alvoRapido != null) {
                            int dano = 15; // FUTURAMENTE: virá da WeaponStrategy
                            alvoRapido.takeDamage(dano);
                            logBatalha = player.getName() + " usou Ataque Rápido em "
                                         + alvoRapido.getName() + ". Causou " + dano + " de dano.";
                        }
                        acaoRealizada = true;
                        break;
                    case "2": // Ataque Pesado
                        Enemy alvoPesado = primeiroInimigoVivo();
                        if (alvoPesado != null) {
                            int dano = 25; // FUTURAMENTE: virá da WeaponStrategy
                            alvoPesado.takeDamage(dano);
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
                String escolha = menu.exibirMenuItens();
                switch (escolha) {
                    case "1": // Poção de Vida Pequena
                        // FUTURAMENTE: remover item do inventário real do Player
                        int cura = 20;
                        player.heal(cura);
                        logBatalha = player.getName() + " usou Poção de Vida Pequena. Recuperou " + cura + " HP!";
                        acaoRealizada = true;
                        break;
                    case "2": // Bomba de Fumaça
                        logBatalha = player.getName() + " usou Bomba de Fumaça. Os inimigos foram cegados!";
                        acaoRealizada = true;
                        break;
                    case "3": estadoMenu = "PRINCIPAL"; break;
                    default:
                        logBatalha = "Item inválido!";
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
        if (fugiuDaBatalha) {
            this.battleState = 3; // fuga
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
        if (battleState == 3) {
            menu.exibirResultadoBatalha(false, player.getName() + " (fugiu)");
        } else {
            boolean vitoria = (battleState == 1);
            menu.exibirResultadoBatalha(vitoria, player.getName());
        }
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

    /** Expõe o estado final: 0 = em andamento, 1 = vitória, 2 = derrota. */
    public int getBattleState() {
        return battleState;
    }
}
