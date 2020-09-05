package xktz.fx.card;

import xktz.fx.card.components.data.MoveCostShape;
import xktz.game.objects.card.soldier.BattleCard;

public class FxInBattleDescriptionCard extends FxDescriptionCard {

    private BattleCard card;

    public FxInBattleDescriptionCard(BattleCard card) {
        super(card.getSoldierCard());
        this.card = card;
        super.blood.setValue(card.getMaxHp());
        super.attack.setValue(card.getAttack());
    }
}
