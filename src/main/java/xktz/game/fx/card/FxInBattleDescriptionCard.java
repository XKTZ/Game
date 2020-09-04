package xktz.game.fx.card;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import xktz.game.fx.card.components.TypeIcon;
import xktz.game.fx.card.components.data.CostShape;
import xktz.game.fx.card.components.data.MoveCostShape;
import xktz.game.objects.card.soldier.BattleCard;

public class FxInBattleDescriptionCard extends FxDescriptionCard {

    private MoveCostShape moveCostShape;
    private BattleCard card;

    public FxInBattleDescriptionCard(BattleCard card) {
        super(card.getSoldierCard());
        this.card = card;
        super.blood.setValue(card.getMaxHp());
        super.attack.setValue(card.getAttack());
        initMoveCost();
    }

    private void initMoveCost() {
        moveCostShape = new MoveCostShape(card.getSoldierCard().getOriginalCost());
        this.getChildren().add(moveCostShape);
        moveCostShape.setLayoutX(150);
        moveCostShape.setLayoutY(30);
    }
}
