package org.pattern.rpg.domain.item.wearable;

public class MagicAmulet implements Wearable {
    private final int protection = 5;

    @Override
    public String getName() {
        return "Amuleto Mágico";
    }

    @Override
    public String getDescription() {
        return "Um amuleto que brilha com uma energia misteriosa.";
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
