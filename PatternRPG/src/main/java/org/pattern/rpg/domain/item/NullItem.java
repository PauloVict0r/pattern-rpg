package org.pattern.rpg.domain.item;

public class NullItem implements Item {
    @Override
    public String getName() {
        return "Nenhum";
    }

    @Override
    public String getDescription() {
        return "Nada";
    }

    @Override
    public boolean isEquipable() {
        return false;
    }

    @Override
    public boolean isUsable() {
        return false;
    }
}
