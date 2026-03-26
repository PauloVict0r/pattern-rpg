package org.pattern.rpg.domain.entity.enemy;

import org.pattern.rpg.domain.entity.Creature;
import org.pattern.rpg.domain.entity.Entity;

public abstract class Enemy extends Creature {
    protected String name;
    protected int damage;

    /**
     * Inicializa o inimigo definindo seu nome, HP inicial e dano.
     * Usa setName/setHp de Creature para manter a fonte de verdade de HP
     * centralizada — assim isAlive() e isDead() herdados funcionam corretamente.
     */
    protected void initStats(String name, int hp, int damage) {
        this.name = name;
        this.damage = damage;
        setName(name);
        setHp(hp);
    }

    public String getName() { return name; }
    public int getDamage() { return damage; }

    /**
     * Retorna o HP atual do inimigo — delega para getHP() de Creature.
     * Mantido por compatibilidade com código que chame getHealth().
     */
    public int getHealth() { return getHP(); }

    /** Aplica dano direto (sem defesa do inimigo por ora). */
    public void takeDamage(int amount) {
        receiveDamage(amount);
    }

    /** Ataca um alvo e retorna o dano efetivo causado. */
    public int attackTarget(Entity target) {
        return target.receiveDamage(this.damage);
    }

    /** Callback para quando o inimigo morre — pode ser expandido com loot/XP. */
    public void die() {
        // Expansão futura: drop de item, XP, animação etc.
    }
}