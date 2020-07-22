package xktz.game.objects.card;

import xktz.game.objects.GameObject;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.card.soldier.SoldierCard;

public class HandCard implements GameObject {
    private Card card;
    private int owner;

    private int hp;
    private int attack;

    private boolean soldierCard;

    public HandCard(Card card, int owner) {
        // set the card
        this.card = card;
        // check if it is instance of soldier card
        if (card instanceof SoldierCard) {
            // if it is, set soldier card to true
            soldierCard = true;
            // create a soldier card
            SoldierCard soldierCard = (SoldierCard) card;
            // set the hp & attack
            hp = soldierCard.getOriginalHP();
            attack = soldierCard.getOriginalAttack();
        }
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
        return new BattleCard(this, owner, hp, attack);
    }

}
