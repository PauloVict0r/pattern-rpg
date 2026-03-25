package org.pattern.rpg.domain.entity.enemy;

import org.pattern.rpg.domain.entity.Entity;

public class Dragon extends Enemy {
    public Dragon() {
        this.setName("Dragão Ancestral");
        this.setHP(200);
        this.setAttack(50);
        this.setDefense(20);
        this.setCriticalChance(0.15);
    }
}
