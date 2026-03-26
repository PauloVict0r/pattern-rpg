package org.pattern.rpg.domain.entity.enemy;

public class Skeleton extends Enemy {
    public Skeleton() {
        initStats("Esqueleto Arqueiro", 20, 8);
    }

    @Override
    public void attack() {
        System.out.println(this.name + " atira uma flecha causando " + this.damage + " de dano!");
    }
}
