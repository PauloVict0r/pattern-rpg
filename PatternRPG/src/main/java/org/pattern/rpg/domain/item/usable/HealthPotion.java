package org.pattern.rpg.domain.item.usable;

import org.pattern.rpg.domain.entity.Creature;

public abstract class HealthPotion extends Usable {
    protected int healthRestore;

    public int getHealthRestore() {
        return healthRestore;
    }

    @Override
    public void use(Creature target) {
        target.increaseHP(this.healthRestore);
        System.out.println(target.getName() + " consumiu a " + this.getName() + " e curou " + this.healthRestore + " de HP!");
    }
}
