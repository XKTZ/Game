package xktz.game.objects.card;

public class HandCard {
    private Card card;

    public HandCard(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    /**
     * Create a new battle card
     * @return the new battle card
     */
    public BattleCard createBattleCard() {
        return new BattleCard(this);
    }
}
