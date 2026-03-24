package org.pattern.rpg.domain.item.wearable;

public abstract class WearableDecorator implements Wearable {
    protected Wearable wrappee;

    public WearableDecorator(Wearable wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public String getName() {
        return wrappee.getName();
    }

    @Override
    public String getDescription() {
        return wrappee.getDescription();
    }

    @Override
    public boolean isEquipable() {
        return wrappee.isEquipable();
    }

    @Override
    public boolean isUsable() {
        return wrappee.isUsable();
    }

    @Override
    public int getProtection() {
        return wrappee.getProtection();
    }
}
