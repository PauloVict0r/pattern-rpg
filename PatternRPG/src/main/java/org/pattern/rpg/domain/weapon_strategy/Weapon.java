package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.item.Item;

public abstract class Weapon implements Item, WeaponStrategy {

    @Override
    public int attack(Entity target, int damageStat, double critStat) {
        return target.receiveDamage(this.weaponDamage() + damageStat);
    }

    @Override
    public boolean isEquipable() {
        return true;
    }

    @Override
    public boolean isUsable() {
        return false;
    }
}