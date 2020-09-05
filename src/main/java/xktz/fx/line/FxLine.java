package xktz.fx.line;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import xktz.fx.GamePanel;
import xktz.fx.card.FxBattleCard;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.Line;

import java.util.ArrayList;
import java.util.List;

public class FxLine extends Pane {
    private Line line;
    private GamePanel panel;

    public FxLine(Line line, GamePanel panel) {
        this.line = line;
        this.panel = panel;
        this.setPrefHeight(100);
        this.setPrefWidth(570);
        refresh();
    }

    /**
     * Refresh
     */
    public void refresh() {
        double x = 0;
        List<FxBattleCard> allCards = new ArrayList<>(6);
        for (BattleCard card: line.getCards()) {
            FxBattleCard fxBattleCard = getRelativeFxBattleCard(card);
            if (fxBattleCard == null) {
                fxBattleCard = new FxBattleCard(card, panel);
            }
            allCards.add(fxBattleCard);
        }
        // clear all of the nodes
        getChildren().clear();
        for (FxBattleCard card: allCards) {
            card.setLayoutX(x);
            card.setLayoutY(0);
            getChildren().add(card);
            x += 95;
        }
    }

    /**
     * Get relative fx battle card from the line
     * @param card the battle card
     * @return the fx
     */
    private FxBattleCard getRelativeFxBattleCard(BattleCard card) {
        for (Node node: this.getChildren()) {
            if (node instanceof FxBattleCard) {
                if (((FxBattleCard) node).getCard().equals(card)) {
                    return (FxBattleCard) node;
                }
            }
        }
        return null;
    }
}
