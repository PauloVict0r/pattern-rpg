package org.pattern.rpg.domain.entity.enemy;

import org.pattern.rpg.domain.entity.Entity;

public class NullEnemy extends Enemy {
    public NullEnemy() {
        this.setName("Inimigo Inexistente");
        this.setHP(0);
        this.setAttack(0);
        this.setDefense(0);
        this.setCriticalChance(0.0);
    }
}