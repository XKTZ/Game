package xktz.game.attribute.effect.condition;

import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.IBattleStage;

public interface EffectConditionChecker {

    public boolean check(IBattleStage stage, BattleCard card, EffectSituation situation);
}
