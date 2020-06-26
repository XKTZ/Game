package xktz.game.objects.card;

import xktz.game.objects.GameObject;
import xktz.game.objects.card.soldier.BattleCard;

public class HandCard implements GameObject {
    private Card card;
    private int owner;

    public HandCard(Card card, int owner) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    /**
     * Create a new battle card
     *
     * @return the new battle card
     */
    public BattleCard createBattleCard() {
        return new BattleCard(this, owner);
    }

}
