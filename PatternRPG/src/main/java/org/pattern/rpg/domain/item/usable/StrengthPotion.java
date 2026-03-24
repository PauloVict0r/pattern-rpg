package org.pattern.rpg.domain.item.usable;

public class StrengthPotion extends Usable {
    private final int strengthIncrease = 20;

    @Override
    public String getName() {
        return "Poção de Força";
    }

    @Override
    public String getDescription() {
        return "Aumenta temporariamente a força física.";
    }

    public int getStrengthIncrease() {
        return strengthIncrease;
    }
}
