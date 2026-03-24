package org.pattern.rpg.domain.factory;

import org.pattern.rpg.domain.entity.enemy.Dragon;
import org.pattern.rpg.domain.entity.enemy.Enemy;
import org.pattern.rpg.domain.entity.enemy.Goblin;
import org.pattern.rpg.domain.entity.enemy.Hollow;
import org.pattern.rpg.domain.entity.enemy.NullEnemy;
import org.pattern.rpg.domain.entity.enemy.Skeleton;
import org.pattern.rpg.domain.entity.enemy.Vampire;
import org.pattern.rpg.domain.entity.enemy.Wolf;

public class EnemyFactory {
    public static Enemy createEnemy(String type) {
        if (type == null) {
            return new NullEnemy();
        }

        switch (type.toLowerCase()) {
            case "skeleton":
            case "esqueleto":
                return new Skeleton();
            case "goblin":
                return new Goblin();
            case "vampire":
            case "vampiro":
                return new Vampire();
            case "wolf":
            case "lobo":
                return new Wolf();
            case "dragon":
            case "dragao":
            case "dragão":
                return new Dragon();
            case "hollow":
                return new Hollow();
            default:
                return new NullEnemy();
        }
    }
}
