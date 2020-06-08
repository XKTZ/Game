package xktz.game.objects.stage;

import xktz.game.objects.card.BattleCard;
import xktz.game.objects.card.soldier.SoldierCard;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private List<BattleCard> cards;

    public Line() {
        // init the cards
        cards = new ArrayList<BattleCard>(6);
    }

    /**
     * Add a soldier into the line
     * @param card the battle card
     */
    public void addSoldier(BattleCard card) {
        if (cards.size() < 6) {
            cards.add(card);
        }
    }

    public List<BattleCard> getCards() {
        return cards;
    }

    public BattleCard get(int index) {
        return cards.get(index);
    }
}
