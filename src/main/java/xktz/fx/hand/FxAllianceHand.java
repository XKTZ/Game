package xktz.fx.hand;

import javafx.scene.Node;
import xktz.fx.card.FxHandCard;
import xktz.game.objects.card.HandCard;

import java.util.List;

public class FxAllianceHand extends FxHand{
    public FxAllianceHand(List<HandCard> handCards) {
        super(handCards);
    }

    @Override
    protected Node getCard(int i) {
        return new FxHandCard(super.cards.get(i));
    }
}
