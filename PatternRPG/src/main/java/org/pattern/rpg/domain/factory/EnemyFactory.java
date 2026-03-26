package org.pattern.rpg.domain.factory;

import org.pattern.rpg.domain.entity.enemy.Dragon;
import org.pattern.rpg.domain.entity.enemy.Enemy;
import org.pattern.rpg.domain.entity.enemy.Goblin;
import org.pattern.rpg.domain.entity.enemy.Hollow;
import org.pattern.rpg.domain.entity.enemy.NullEnemy;
import org.pattern.rpg.domain.entity.enemy.Skeleton;
import org.pattern.rpg.domain.entity.enemy.Vampire;
import org.pattern.rpg.domain.entity.enemy.Wolf;

import org.pattern.rpg.domain.builder.EnemyBuilder;
import org.pattern.rpg.domain.builder.EnemyDirector;

public class EnemyFactory {
    public static Enemy createEnemy(String type) {
        if (type == null) {
            return new NullEnemy();
        }

        switch (type.toLowerCase()) {
            case "skeleton":
            case "esqueleto":
                return createWithBuilder(Skeleton::new);
            case "goblin":
                return createWithBuilder(Goblin::new);
            case "vampire":
            case "vampiro":
                return createWithBuilder(Vampire::new);
            case "wolf":
            case "lobo":
                return createWithBuilder(Wolf::new);
            case "dragon":
            case "dragao":
            case "dragão":
                return createWithBuilder(Dragon::new);
            case "hollow":
                return createWithBuilder(Hollow::new);
            default:
                return new NullEnemy();
        }
    }

    private static Enemy createWithBuilder(java.util.function.Supplier<Enemy> supplier) {
        EnemyBuilder builder = new EnemyBuilder(supplier);
        
        double chance = Math.random();
        EnemyDirector director = new EnemyDirector();
        
        if (chance <= 0.05) {
            director.makeBoss(builder);
        } else if (chance <= 0.20) {
            director.makeChampion(builder);
        } else if (chance <= 0.50) {
            director.makeMinion(builder);
        }

        return builder.getResult();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 4543bc425957374648bc203e70ece873fc2b3385
