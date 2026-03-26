package org.pattern.rpg.domain.entity.enemy;

public class Wolf extends Enemy {
    public Wolf() {
        initStats("Lobo Selvagem", 25, 12);
    }

    @Override
    public void attack() {
        System.out.println(this.name + " morde ferozmente causando " + this.damage + " de dano!");
    }
}
