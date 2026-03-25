package org.pattern.rpg.domain.entity.enemy;

public class Skeleton extends Enemy {
    public Skeleton() {
        this.name = "Esqueleto Arqueiro";
        this.health = 20;
        this.damage = 8;
    }

    @Override
    public void attack() {
        System.out.println(this.name + " atira uma flecha causando " + this.damage + " de dano!");
    }
}
