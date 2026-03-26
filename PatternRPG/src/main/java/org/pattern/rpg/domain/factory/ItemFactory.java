package org.pattern.rpg.domain.factory;

import org.pattern.rpg.domain.item.Item;
import org.pattern.rpg.domain.item.NullItem;
import org.pattern.rpg.domain.item.usable.SmallHealthPotion;
import org.pattern.rpg.domain.item.usable.DefensePotion;
import org.pattern.rpg.domain.item.usable.StrengthPotion;

public class ItemFactory {
    public static Item createItem(String type) {
        if (type == null) {
            return new NullItem();
        }

        switch (type.toLowerCase()) {
            case "health potion":
            case "pocao de vida":
            case "poção de vida":
                return new SmallHealthPotion();
            case "strength potion":
            case "pocao de forca":
            case "poção de força":
                return new StrengthPotion();
            case "defense potion":
            case "pocao de defesa":
            case "poção de defesa":
                return new DefensePotion();
            default:
                return new NullItem();
        }
    }
}
