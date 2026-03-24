package org.pattern.rpg.domain.entity.enemy;

public class Hollow extends Enemy {
    public Hollow() {
        this.name = "Hollow";
        this.health = 15;
        this.damage = 5;
    }

    @Override
    public void attack() {
        System.out.println(this.name + " ataca desengonçadamente causando " + this.damage + " de dano.");
    }
}
