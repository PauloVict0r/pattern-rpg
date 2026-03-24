package org.pattern.rpg.domain.item.wearable;

public class NormalArmor implements Wearable {
    private final int protection = 10;

    @Override
    public String getName() {
        return "Armadura Normal";
    }

    @Override
    public String getDescription() {
        return "Uma armadura de ferro padrão.";
    }

    @Override
    public boolean isEquipable() {
        return true;
    }

    @Override
    public boolean isUsable() {
        return false;
    }

    @Override
    public int getProtection() {
        return protection;
    }
}
