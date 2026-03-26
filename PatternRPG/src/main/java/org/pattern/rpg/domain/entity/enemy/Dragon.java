package org.pattern.rpg.domain.entity.enemy;

public class Dragon extends Enemy {
    public Dragon() {
        initStats("Dragão Ancestral", 200, 50);
    }

    @Override
    public void attack() {
        System.out.println(this.name + " cospe um mar de chamas causando " + this.damage + " de dano crítico!");
    }
}
