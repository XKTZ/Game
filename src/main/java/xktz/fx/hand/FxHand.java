package xktz.fx.hand;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import xktz.fx.card.FxUnknownCard;
import xktz.game.objects.card.HandCard;

import java.util.Arrays;
import java.util.List;

public class FxHand extends Pane {

    protected List<HandCard> cards;
    private static double START_DEGREE = 60D;
    private static double AVAILABLE_DEGREE = 60D;
    public FxHand(List<HandCard> cards) {
        this.cards = cards;
        this.setPrefWidth(700);
        this.setPrefHeight(200);
        refresh();
    }

    public void refresh() {
        this.getChildren().clear();
        int numbers = cards.size();
        if (numbers < 4) {
            for (int i = 0; i < numbers; i++) {
                Node unknownCard = getCard(i);
                unknownCard.setLayoutX(getXSmallerThanThree(numbers, i));
                unknownCard.setLayoutY(0);
                getChildren().add(unknownCard);
            }
        } else {
            double xPerCard = 700D / (numbers + 1);
            double degPerCard = START_DEGREE / numbers;
            for (int i = 0; i < numbers; i++) {
                Node card = getCard(i);
                card.setLayoutX(xPerCard * i);
                card.setLayoutY(getYMoreThanThree(numbers, i));
                card.setRotate(270 + START_DEGREE + i * degPerCard);
                getChildren().add(card);
            }
        }
    }

    private int getXSmallerThanThree(int num, int index) {
        index++;
        if (num == 1) {
            return 250;
        } else if (num == 2) {
            if (index == 1) {
                return 240;
            } else {
                return 360;
            }
        } else {
            if (index == 1) {
                return 115;
            } else if (index == 2) {
                return 275;
            } else {
                return 435;
            }
        }
    }

    private int getYMoreThanThree(int num, int index) {
        index++;
        return Math.abs(num / 2 - index) * (10 + Math.abs(num / 2 - index) * 2);
    }

    protected Node getCard(int i) {
        return new FxUnknownCard();
    }
}
