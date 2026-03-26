package org.pattern.rpg.domain.builder;

public class PlayerDirector {

    public void basePlayer(PlayerBuilder playerBuilder) {
        playerBuilder.reset();
        playerBuilder.setHP(100);
        playerBuilder.setAttack(5);
        playerBuilder.setDefense(5);
        playerBuilder.setCriticalChance(0.05);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 4543bc425957374648bc203e70ece873fc2b3385
