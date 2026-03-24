package org.pattern.rpg.domain.entity.enemy;

public class Dragon extends Enemy {
    public Dragon() {
        this.name = "Dragão Ancestral";
        this.health = 200;
        this.damage = 50;
    }

    @Override
    public void attack() {
        System.out.println(this.name + " cospe um mar de chamas causando " + this.damage + " de dano crítico!");
    }
}
