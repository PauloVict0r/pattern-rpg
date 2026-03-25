package org.pattern.rpg.domain.builder;

import org.pattern.rpg.domain.entity.Player;

public class PlayerDirector {

    public void basePlayer(PlayerBuilder playerBuilder) {
        playerBuilder.reset();
        playerBuilder.setHP(100);
        playerBuilder.setAttack(5);
        playerBuilder.setDefense(5);
        playerBuilder.setCriticalChance(0.05);
    }
}
