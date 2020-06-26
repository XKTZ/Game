package xktz.game.attribute.effect.condition;

import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.BattleStage;

public interface EffectConditionChecker {

    public boolean check(BattleStage stage, BattleCard card, EffectSituation situation);
}
