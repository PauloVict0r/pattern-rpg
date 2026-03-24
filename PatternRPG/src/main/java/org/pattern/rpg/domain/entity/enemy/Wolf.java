package org.pattern.rpg.domain.entity.enemy;

public class Wolf extends Enemy {
    public Wolf() {
        this.name = "Lobo Selvagem";
        this.health = 25;
        this.damage = 12;
    }

    @Override
    public void attack() {
        System.out.println(this.name + " morde ferozmente causando " + this.damage + " de dano!");
    }
}
