package xktz.game.objects.card;

import xktz.game.objects.GameObject;

public interface Card extends GameObject {

    /**
     * Get the cost of the card
     * @return the cost
     */
    public int getOriginalCost();

    /**
     * Get the card type of the card
     * @return The card type
     */
    public CardType getCardType();

    /**
     * Get the rarity of the card
     * @return the rarity of the card
     */
    public Rarity getRarity();

    /**
     * Get the name of the card
     * @return the name of the card
     */
    public String getName();

    /**
     * Get the cost of the card
     * @return the cost
     */
    public int getCost();

}
