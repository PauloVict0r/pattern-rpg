package org.pattern.rpg.domain.entity.enemy;

public class Vampire extends Enemy {
    public Vampire() {
        this.name = "Vampiro Sedento";
        this.health = 50;
        this.damage = 20;
    }

    @Override
    public void attack() {
        int healAmount = (int) (this.damage * 0.25); 
        this.health += healAmount;
        
        System.out.println(this.name + " morde e drena sangue causando " + this.damage + " de dano!");
        System.out.println(this.name + " absorveu " + healAmount + " pontos de vida. Vida atual: " + this.health);
    }
}
