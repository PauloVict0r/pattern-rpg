package org.pattern.rpg.domain.entity.enemy;

import org.pattern.rpg.domain.entity.Creature;

public abstract class Enemy extends Creature {

    public int getHealth() { return this.getHP(); }
    public int getDamage() { return this.getAttack(); }

    public void takeDamage(int amount) {
        this.receiveDamage(amount);
        System.out.println(this.getName() + " recebeu " + amount + " de dano. Vida restante: " + this.getHP());
        
        if (this.isDead()) {
            die();
        }
    }

    public void die() {
        System.out.println(this.getName() + " foi derrotado!");
    }

    public boolean isDead() {
        return this.getHP() <= 0;
    }
}