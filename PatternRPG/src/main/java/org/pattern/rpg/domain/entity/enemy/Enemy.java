package org.pattern.rpg.domain.entity.enemy;

import org.pattern.rpg.domain.entity.Creature;
import org.pattern.rpg.domain.entity.Entity;

public abstract class Enemy extends Creature {

    public int getHealth() { return this.getHP(); }
    public int getDamage() { return this.getAttack(); }

    /**
     * Retorna o HP atual do inimigo — delega para getHP() de Creature.
     * Mantido por compatibilidade com código que chame getHealth().
     */

    /** Aplica dano direto (sem defesa do inimigo por ora). */
    public void takeDamage(int amount) {
        this.receiveDamage(amount);
        System.out.println(this.getName() + " recebeu " + amount + " de dano. Vida restante: " + this.getHP());
        
        if (this.isDead()) {
            die();
        }
    }

    /** Ataca um alvo e retorna o dano efetivo causado. */

    /** Callback para quando o inimigo morre — pode ser expandido com loot/XP. */
    public void die() {
        System.out.println(this.getName() + " foi derrotado!");
    }

    public boolean isDead() {
        return this.getHP() <= 0;
    }
}