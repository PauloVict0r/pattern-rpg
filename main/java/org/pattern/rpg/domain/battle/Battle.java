package org.pattern.rpg.domain.battle;

import org.pattern.rpg.domain.entity.*;

import java.util.List;

public abstract class Battle {

    protected int battleState; //0: em andamento; 1: vitória; 2: derrota

    //TEMPLATE METHOD
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
