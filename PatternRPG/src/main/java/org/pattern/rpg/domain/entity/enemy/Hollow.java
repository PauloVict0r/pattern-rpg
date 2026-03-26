package org.pattern.rpg.domain.entity.enemy;

import org.pattern.rpg.domain.entity.Entity;

public class Hollow extends Enemy {
    public Hollow() {
        this.setName("Hollow");
        this.setHP(15);
        this.setAttack(5);
        this.setDefense(0);
        this.setCriticalChance(0.01);
    }
}