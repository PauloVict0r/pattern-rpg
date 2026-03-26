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

    /** Restaura HP pelo valor informado. Não ultrapassa o HP atual máximo (sem cap fixo por ora). */
    public void heal(int amount) {
        this.hp += amount;
    }

    public void setWeapon(WeaponStrategy weapon) {
        this.weapon = weapon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

}
