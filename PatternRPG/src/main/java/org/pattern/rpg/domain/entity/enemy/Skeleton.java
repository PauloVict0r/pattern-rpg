package org.pattern.rpg.domain.entity.enemy;

import org.pattern.rpg.domain.entity.Entity;

public class Skeleton extends Enemy { 
    public Skeleton() {
        this.setName("Esqueleto Arqueiro");
        this.setHP(20);
        this.setAttack(8);
        this.setDefense(3);
        this.setCriticalChance(0.10);
    }
}
