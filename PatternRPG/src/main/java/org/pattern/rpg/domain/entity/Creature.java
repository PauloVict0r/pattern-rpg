package org.pattern.rpg.domain.entity;


import org.pattern.rpg.domain.item.Item;
import org.pattern.rpg.domain.weapon_strategy.PunchStrategy;
import org.pattern.rpg.domain.weapon_strategy.WeaponStrategy;

import java.util.List;

public abstract class Creature implements Entity {
    private int hp;
    private int hpMaximo;
    private int defense;
    private int attack;
    private double criticalChance;
    private String name;
    private List<Item> inventory;
    private WeaponStrategy weapon;

    public Creature() {
        this.weapon = new PunchStrategy();
    }

    public int getAttack() {
        return this.attack;
    }

    public int getHP() {
        return this.hp;
    }

    private void decreaseHP(int value) {
        if (value <= this.hp) {
            this.hp -= value;
        } else {
            this.hp = 0;
        }
    }

    public int getDefense() {
        return this.defense;
    }

    public double getCriticalChance() {
        return this.criticalChance;
    }

    public void setCriticalChance(double criticalChance) {
        this.criticalChance = criticalChance;
    }

    public int attack(Entity target) {
        return this.weapon.attack(target, getAttack(), getCriticalChance());
    }

    public int receiveDamage(int damage) {
        int damage_taken = Math.max(0, damage - this.getDefense());
        this.decreaseHP(damage_taken);
        return damage_taken;
    }

    public void increaseHP(int value) {
        if (value > 0) {
            this.hp += value;
        }
    }

    /** Restaura HP pelo valor informado, respeitando o HP máximo. */
    public void heal(int amount) {
        this.hp = Math.min(this.hp + amount, this.hpMaximo > 0 ? this.hpMaximo : this.hp + amount);
    }

    public void setWeapon(WeaponStrategy weapon) {
        this.weapon = weapon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (hp > this.hpMaximo) {
            this.hpMaximo = hp; // sincroniza o máximo na inicialização
        }
    }

    /** Restaura o HP completamente ao máximo. Usado a cada 5 andares. */
    public void restaurarHp() {
        this.hp = this.hpMaximo;
    }

    /**
     * Incremento leve de status a cada andar vencido.
     * +5 HP máximo, +1 ataque, +0 defesa (expansão futura).
     */
    public void incrementarStatus() {
        this.hpMaximo += 5;
        this.attack   += 1;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getName() {
        return this.name;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public boolean isDead() {
        return this.hp <= 0;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

}