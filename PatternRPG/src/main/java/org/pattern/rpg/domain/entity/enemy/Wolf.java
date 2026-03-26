package org.pattern.rpg.domain.entity.enemy;

import org.pattern.rpg.domain.entity.Entity;

public class Wolf extends Enemy {
    public Wolf() {
        this.setName("Lobo Selvagem");
        this.setHp(25);
        this.setAttack(12);
        this.setDefense(1);
        this.setCriticalChance(0.12);
    }
}
