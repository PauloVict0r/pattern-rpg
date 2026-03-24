package org.pattern.rpg.domain.entity.enemy;

public abstract class Enemy {
    protected String name;
    protected int health;
    protected int damage;

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }

    public void takeDamage(int amount) {
        this.health -= amount;
        System.out.println(this.name + " recebeu " + amount + " de dano. Vida restante: " + this.health);
        
        if (this.health <= 0) {
            die();
        }
    }

    public void die() {
        System.out.println(this.name + " foi derrotado!");
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public abstract void attack();
}