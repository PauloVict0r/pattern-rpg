package org.pattern.rpg.domain.item.wearable;

public class GoldenArmor implements Wearable {
    private final int protection = 30;

    @Override
    public String getName() {
        return "Armadura de Ouro";
    }

    @Override
    public String getDescription() {
        return "Uma armadura brilhante feita de ouro puro. Muito pesada.";
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
