package xktz.game.attribute.effect.condition;

import xktz.game.objects.GameObject;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.util.custom.script.CustomConditionFactory;

import java.rmi.RemoteException;

public class EffectCondition implements GameObject {
    private String objectA;
    private EffectConditionType objectAType;
    private EffectConditionType objectBType;
    private EffectSymbol symbol;

    public EffectCondition(String objectA, EffectConditionType objectAType,
                           EffectConditionType objectBType,
                           EffectSymbol symbol) {
        this.objectA = objectA;
        this.objectAType = objectAType;
        this.objectBType = objectBType;
        this.symbol = symbol;
    }

    /**
     * Check if need to effect
     * @param stage the battle stage
     * @param card the card
     * @param situation the situation (start turn, end turn, ... etc)
     * @return success or not
     * @throws RemoteException
     */
    public boolean effectCheck(BattleStage stage, BattleCard card, EffectSituation situation) throws RemoteException {
        switch (objectAType) {
            case INTEGER:
                int aInt = Integer.parseInt(objectA);
                switch (objectBType) {
                    case SOLDIER_HP:
                        return checkInteger(aInt, card.getHp());
                    case SOLDIER_MAX_HP:
                        return checkInteger(aInt, card.getMaxHp());
                    case SOLDIER_ATTACK:
                        return checkInteger(aInt, card.getAttack());
                    case NUM_BATTLE_STAGE_ALLIANCE_LINE:
                        return checkInteger(aInt, stage.getNumberAlliance());
                    case NUM_BATTLE_STAGE_ENEMY_LINE:
                        return checkInteger(aInt, stage.getNumberEnemy());
                }
            case EFFECT_SITUATION:
                return EffectSituation.valueOf(objectA).equals(situation);
            case OWNER_FRONT_LINE:
                return stage.isOwningFrontLine();
            case CUSTOM:
                return CustomConditionFactory.getChecker(objectA).check(stage, card, situation);
        }
        return false;
    }

    /**
     * Compare the integer
     * @param a a
     * @param b b
     * @return true or false
     */
    private boolean checkInteger(int a, int b) {
        switch (symbol) {
            case LESS:
                return b < a;
            case MORE:
                return b > a;
            case LESS_EQUAL:
                return b <= a;
            case MORE_EQUAL:
                return b >= a;
            case EQUALS:
                return b == a;
            default:
                return false;
        }
    }
}
