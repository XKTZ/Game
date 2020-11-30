package xktz.game.attribute.effect.content;

import xktz.game.objects.GameObject;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.IBattleStage;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface Effector extends GameObject {
    public boolean effect(IBattleStage stageMain, BattleCard mainCard, BattleCard enemyCard) throws RemoteException;
}
