package org.pattern.rpg.domain.entity;


import org.pattern.rpg.domain.item.Item;
import org.pattern.rpg.domain.weapon_strategy.PunchStrategy;
import org.pattern.rpg.domain.weapon_strategy.WeaponStrategy;

import java.util.List;

public abstract class Creature implements Entity {
    private int hp;
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

    public void increaseHP(int value) {
        if (value > 0) {
            this.hp += value;
        }
    }

    public int getDefense() {
        return this.defense;
    }

    public double getCriticalChance() {
        return this.criticalChance;
    }

    public int attack(Entity target) {
        return this.weapon.attack(target);
    }

    public int receiveDamage(int damage) {
        int damage_taken = Math.max(0, damage - this.getDefense());
        this.decreaseHP(damage_taken);
        return damage_taken;
    }

    public void setWeapon(WeaponStrategy weapon) {
        this.weapon = weapon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setCriticalChance(double criticalChance) {
        this.criticalChance = criticalChance;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean isAlive() {
        return this.hp > 0;
    }

    @Override
    public boolean isDead() {
        return this.hp <= 0;
    }
}
