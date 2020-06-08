package xktz.game.factory;

import xktz.game.objects.card.BattleCard;
import xktz.game.objects.card.Card;

import java.util.Map;

public class BattleCardFactory {
    public static Map<String, Card> cardMap;

    public static BattleCard getBattleCard(String cardType) {
        return cardMap.get(cardType).createBattleCard();
    }
}
