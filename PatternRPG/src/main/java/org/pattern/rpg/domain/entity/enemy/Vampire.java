package org.pattern.rpg.domain.entity.enemy;

public class Vampire extends Enemy {
    public Vampire() {
        initStats("Vampiro Sedento", 50, 20);
    }

    @Override
    public void attack() {
        int healAmount = (int) (this.damage * 0.25);
        // Vampiro drena vida ao atacar — heal() de Creature garante que o HP sobe corretamente
        this.heal(healAmount);
    }
}

