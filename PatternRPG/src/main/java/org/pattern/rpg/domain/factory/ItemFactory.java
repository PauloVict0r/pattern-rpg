package org.pattern.rpg.domain.factory;

import org.pattern.rpg.domain.item.Item;
import org.pattern.rpg.domain.item.NullItem;
import org.pattern.rpg.domain.item.usable.DefensePotion;
import org.pattern.rpg.domain.item.usable.HealthPotion;
import org.pattern.rpg.domain.item.usable.StrengthPotion;
import org.pattern.rpg.domain.item.wearable.GoldenArmor;
import org.pattern.rpg.domain.item.wearable.MagicAmulet;
import org.pattern.rpg.domain.item.wearable.NormalArmor;

public class ItemFactory {
    public static Item createItem(String type) {
        if (type == null) {
            return new NullItem();
        }

        switch (type.toLowerCase()) {
            case "health potion":
            case "pocao de vida":
            case "poção de vida":
                return new HealthPotion();
            case "strength potion":
            case "pocao de forca":
            case "poção de força":
                return new StrengthPotion();
            case "defense potion":
            case "pocao de defesa":
            case "poção de defesa":
                return new DefensePotion();
            case "golden armor":
            case "armadura de ouro":
                return new GoldenArmor();
            case "normal armor":
            case "armadura normal":
                return new NormalArmor();
            case "magic amulet":
            case "amuleto magico":
            case "amuleto mágico":
                return new MagicAmulet();
            default:
                return new NullItem();
        }
    }
}