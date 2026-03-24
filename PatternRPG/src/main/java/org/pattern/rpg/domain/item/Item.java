package org.pattern.rpg.domain.item;

public interface Item {
    String getName();
    String getDescription();
    boolean isEquipable();
    boolean isUsable();
}
