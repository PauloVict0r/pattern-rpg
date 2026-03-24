package org.pattern.rpg.domain.entity.enemy;

public class Goblin extends Enemy{
    public Goblin() {
        this.name = "Goblin Sorrateiro";
        this.health = 30;
        this.damage = 5;
    }

    @Override
    public void attack() {
        System.out.println(this.name + " esfaqueia o jogador causando " + this.damage + " de dano!");
    }
}
