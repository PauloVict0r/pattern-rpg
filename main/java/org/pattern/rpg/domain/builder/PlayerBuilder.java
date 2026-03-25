package org.pattern.rpg.domain.builder;

import org.pattern.rpg.domain.entity.Player;
import java.util.List;

public class PlayerBuilder implements CreatureBuilder{
    private Player player;

    public PlayerBuilder() {
        this.reset();
    }

    @Override
    public void reset() {
        this.player = new Player();
    }

    // Supondo que a classe Player já tenha os métodos prontos
    @Override
    public void setHP(int hp) {
        player.setHp(hp);
    }

    @Override
    public void setDefence(int numberDefence) {
        player.setDefence(numberDefence);
    }

    @Override
    public void setAttack(int numberAttack) {
        player.setAttack(numberAttack);
    }

    @Override
    public void setArmor(String itemArmor) {
        player.setArmor(itemArmor);
    }

    @Override
    public void setWeapon(String itemWeapon) {
        player.setWeapon(itemWeapon);
    }

    @Override
    public void setInventory(List<String> items) {
        player.setInventory(items);
    }

    public Player getResult() {
        Player product = this.player;
        this.reset();
        return product;
    }
}
