package org.pattern.rpg.domain.entity.enemy;

import org.pattern.rpg.domain.entity.Entity;

public class Vampire extends Enemy {
    public Vampire() {
        this.setName("Vampiro Sedento");
        this.setHP(50);
        this.setAttack(20);
        this.setDefense(5);
        this.setCriticalChance(0.08);
    }

    @Override
    public int attack(Entity target) {
        int damageDealt = target.receiveDamage(this.getAttack());
        this.increaseHP((int) (damageDealt * 0.25));
        return damageDealt;
    }
}

