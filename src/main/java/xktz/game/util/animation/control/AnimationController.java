package xktz.game.util.animation.control;

import xktz.fx.card.FxBattleCard;
import xktz.game.objects.card.Card;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.card.soldier.BattleCard;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AnimationController extends Remote {

    /**
     * Drop a hand card on the hand
     * @param card the hand card need to drop
     */
    public void dropCard(int owner, HandCard card) throws RemoteException;

    /**
     * Let a
     * @param attacker
     * @param beAttacked
     */
    public void attack(BattleCard attacker, BattleCard beAttacked) throws RemoteException;

    public void move(BattleCard mover) throws RemoteException;
}
