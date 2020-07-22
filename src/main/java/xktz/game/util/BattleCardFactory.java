package xktz.game.util;

import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.card.Card;
import xktz.game.objects.card.HandCard;

import java.rmi.RemoteException;
import java.util.Map;

public class BattleCardFactory {
    public static Map<String, Card> cardMap;

    public static BattleCard getBattleCard(String cardType, int owner) throws RemoteException {
        return new BattleCard(new HandCard(cardMap.get(cardType), owner), owner);
    }
}
