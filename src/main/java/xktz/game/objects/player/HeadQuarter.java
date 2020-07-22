package xktz.game.objects.player;

import xktz.game.objects.GameObject;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.card.soldier.SoldierCard;

import java.rmi.RemoteException;

public class HeadQuarter extends BattleCard {

    private int minusHpPerRound = 0;

    public HeadQuarter(SoldierCard headQuarter, int owner) {
        super(headQuarter, owner);
    }

    /**
     * minus the hp per round
     */
    public void roundMinusHp() throws RemoteException {
        minusHpPerRound ++;
        beAttacked(minusHpPerRound);
    }
}
