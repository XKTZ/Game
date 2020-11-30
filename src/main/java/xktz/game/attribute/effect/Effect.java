package xktz.game.attribute.effect;

import xktz.game.attribute.effect.condition.EffectCondition;
import xktz.game.attribute.effect.condition.EffectSituation;
import xktz.game.attribute.effect.content.EffectContent;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.IBattleStage;

import java.rmi.RemoteException;

public class Effect {

    private EffectType effectType;
    private EffectContent effectContent;
    private EffectCondition effectCondition;

    public Effect(EffectType effectType, EffectContent effectContent, EffectCondition effectCondition) {
        this.effectType = effectType;
        this.effectContent = effectContent;
        this.effectCondition = effectCondition;
    }

    /**
     * Effect
     * @param stage the battle stage
     * @param card the card
     * @param situation the situation (start turn, end turn, ... etc)
     * @return success or not
     * @throws RemoteException
     */
    public boolean effect(IBattleStage stage, BattleCard card, BattleCard enemyCard, EffectSituation situation) throws RemoteException {
        if (effectType == EffectType.ASSAULT) {
            return effectAssault(stage, card, situation);
        }
        if (effectType == EffectType.CRASH) {
            return effectCrash(stage, card, situation);
        }
        return effectKept(stage, card, enemyCard, situation);
    }

    /**
     * Effect (see the effect as assault)
     * @param stage the stage
     * @param card the card
     * @param situation the situation
     * @return success or not
     */
    public boolean effectAssault(IBattleStage stage, BattleCard card, EffectSituation situation) throws RemoteException{
        if (situation == EffectSituation.CARD_CREATED && effectCondition.effectCheck(stage, card, situation)) {
            effectContent.effect(stage, card, null);
            return true;
        }
        return false;
    }

    /**
     * Effect (see the effect as crash)
     * @param stage the stage
     * @param card the card
     * @param situation the situation
     * @return success or not
     */
    public boolean effectCrash(IBattleStage stage, BattleCard card, EffectSituation situation) throws RemoteException {
        if (situation == EffectSituation.CARD_DIE && effectCondition.effectCheck(stage, card, situation)) {
            effectContent.effect(stage, card, null);
            return true;
        }
        return false;
    }

    /**
     * Effect (see the effect as keep)
     * @param stage the stage
     * @param card the card
     * @param situation the situation
     * @return success or not
     */
    public boolean effectKept(IBattleStage stage, BattleCard card, BattleCard enemyCard, EffectSituation situation) throws RemoteException {
        if (effectCondition.effectCheck(stage, card, situation)) {
            effectContent.effect(stage, card, enemyCard);
            return true;
        }
        return false;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public EffectContent getEffectContent() {
        return effectContent;
    }

    public EffectCondition getEffectCondition() {
        return effectCondition;
    }

}
