package org.pattern.rpg.domain.item.usable;

import org.pattern.rpg.domain.item.Item;

public abstract class Usable implements Item {
    @Override
    public boolean isEquipable() {
        return false;
    }

    @Override
    public boolean isUsable() {
        return true;
    }

    public abstract void use(org.pattern.rpg.domain.entity.Creature target);
}