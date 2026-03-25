package org.pattern.rpg.domain.battle;

import org.pattern.rpg.domain.entity.*;
<<<<<<< HEAD
import org.pattern.rpg.domain.entity.enemy.*;
=======
import org.pattern.rpg.domain.entity.enemy.Enemy;
>>>>>>> c1cf550de9946faab49543fd234cc0bb1ea0c7f9

import java.util.List;

public abstract class Battle {

    protected int battleState; //0: em andamento; 1: vitória; 2: derrota

    public final void startBattle() {
        setup();

        while (!isOver()) {
            Creature currentCreature = nextTurn();
            executeTurn(currentCreature);
        }

        finish();
    }

    protected abstract void setup();
    protected abstract Creature nextTurn();
    protected abstract void executeTurn(Creature creature);
    protected abstract boolean isOver();
    protected abstract void finish();
    protected abstract List<Enemy> createEnemies();
}
