package xktz.fx.hand;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import xktz.fx.card.FxUnknownCard;
import xktz.game.objects.card.HandCard;

import java.util.Arrays;
import java.util.List;

public class FxHand extends Pane {

    private List<HandCard> cards;
    private static double START_DEGREE = 40D;
    private static double AVAILABLE_DEGREE = 100D;
    private static double DIAGONAL_DEGREE = 33.690067525979785;
    private static double LEN_DIAGONAL = 180.27756377319946;

    public FxHand(List<HandCard> cards) {
        this.cards = cards;
        this.setPrefWidth(300);
        this.setPrefHeight(150);
        refresh();
    }

    private void refresh() {
        int numbers = cards.size();
        double[] coordinate;
        double unitDegree = AVAILABLE_DEGREE / numbers;
        for (int i = 0; i < numbers; i++) {

        }
    }

    private Node getCard(int i) {
        return new FxUnknownCard();
    }
}
