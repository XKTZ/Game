package xktz.fx.line;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import xktz.fx.GamePanel;
import xktz.fx.card.FxBattleCard;
import xktz.fx.card.FxHandCard;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.Line;

import java.util.ArrayList;
import java.util.List;

public class FxLine extends Pane {
    private Line line;
    private GamePanel panel;
    private List<FxBattleCard> listBattleCard;

    public FxLine(Line line, GamePanel panel) {
        this.line = line;
        this.panel = panel;
        this.setPrefHeight(160);
        this.setPrefWidth(840);
        refresh();
    }

    /**
     * Refresh
     */
    public void refresh() {
        double x = 0;
        List<FxBattleCard> allCards = new ArrayList<>(6);
        listBattleCard = new ArrayList<>(6);
        for (BattleCard card : line.getCards()) {
            FxBattleCard fxBattleCard = getRelativeFxBattleCard(card);
            if (fxBattleCard == null) {
                fxBattleCard = new FxBattleCard(card, panel);
            }
            allCards.add(fxBattleCard);
        }
        // clear all of the nodes
        getChildren().clear();
        for (FxBattleCard card : allCards) {
            card.setLayoutX(x);
            card.setLayoutY(0);
            getChildren().add(card);
            x += 140;
            listBattleCard.add(card);
        }
    }

    public double[] getNextAvailablePoint() {
        if (line.getLength() == 6) {
            return new double[]{-1, -1};
        }
        Bounds bounds = localToScene(getBoundsInLocal());
        double x = bounds.getMinX() + 140 * line.getLength();
        double y = bounds.getMinY();
        return new double[]{x, y};
    }

    /**
     * Get relative fx battle card from the line
     *
     * @param card the battle card
     * @return the fx
     */
    private FxBattleCard getRelativeFxBattleCard(BattleCard card) {
        for (Node node : this.getChildren()) {
            if (node instanceof FxBattleCard) {
                if (((FxBattleCard) node).getCard().equals(card)) {
                    return (FxBattleCard) node;
                }
            }
        }
        return null;
    }

    public FxBattleCard getBattleCardIn(double x) {
        for (int i = 0; i < line.getLength(); i++) {
            // check x in
            if (x < (i + 1)* 140 - 20) {
                return listBattleCard.get(i);
            }
        }
        return null;
    }

    public List<FxBattleCard> getListBattleCard() {
        return listBattleCard;
    }

    public FxBattleCard findCard(BattleCard card) {
        for (FxBattleCard battleCard: listBattleCard) {
            if (battleCard.getCard() == card) {
                return battleCard;
            }
        }
        return null;
    }

    public Line getLine() {
        return line;
    }
}
