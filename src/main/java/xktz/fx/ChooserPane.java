package xktz.fx;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import xktz.fx.card.FxBattleCard;
import xktz.fx.line.FxLine;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.util.fx.ChoosingUtil;
import xktz.game.util.fx.GameUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ChooserPane extends Pane {

    private static final Rectangle2D SCREEN_REC = Screen.getPrimary().getBounds();
    private static final Border ON_CHOOSE = new Border(
            new BorderStroke(
                    Paint.valueOf("#ffff1a")
                    ,BorderStrokeStyle.SOLID, new CornerRadii(0, 10, 10, 10, false), new BorderWidths(5),
                    new Insets(-5)));

    public ChooserPane(ChoosingUtil.CardChecker checker, ChoosingUtil.CardConsumer consumer) {
        List<FxBattleCard> fxBattleCardAvailable = null;
        try {
            this.setPrefWidth(SCREEN_REC.getWidth());
            this.setPrefHeight(SCREEN_REC.getHeight());
            this.setOpacity(1.0);
            this.setLayoutX(0);
            this.setLayoutY(0);
            fxBattleCardAvailable = getAvailableCards(checker);
            for (FxBattleCard battleCard: fxBattleCardAvailable) {
                setAvailableToChoose(battleCard);
            }
        } catch (RemoteException re) {
            re.printStackTrace();
            System.exit(1);
        }
        final List<FxBattleCard> fxBattleCardAvailableFinal = fxBattleCardAvailable;
        this.setOnMouseClicked((event) -> {
            try {
                if (event.getButton() == MouseButton.PRIMARY) {
                    FxBattleCard battleCard = getCardChoose(GameUtil.PANEL_ALLIANCE, event.getSceneX(), event.getSceneY());
                    // if it is null, return
                    if (battleCard == null || !fxBattleCardAvailableFinal.contains(battleCard)) {
                        return;
                    }
                    for (FxBattleCard battleCardAvailable: fxBattleCardAvailableFinal) {
                        battleCardAvailable.setBorder(Border.EMPTY);
                    }
                    consumer.run(battleCard.getCard());
                }
                GameUtil.PANEL_ALLIANCE.getChildren().remove(this);
            } catch (RemoteException re) {
                re.printStackTrace();
            }
        });
    }

    public ChooserPane(ChoosingUtil.LineChecker checker, ChoosingUtil.LineConsumer consumer) {
        List<FxLine> fxBattleCardAvailable = null;
        try {
            this.setPrefWidth(SCREEN_REC.getWidth());
            this.setPrefHeight(SCREEN_REC.getHeight());
            this.setOpacity(1.0);
            this.setLayoutX(0);
            this.setLayoutY(0);
            fxBattleCardAvailable = getAvailableLines(checker);
            for (FxLine battleCard: fxBattleCardAvailable) {
                setAvailableToChoose(battleCard);
            }
        } catch (RemoteException re) {
            re.printStackTrace();
            System.exit(1);
        }
        final List<FxLine> fxBattleCardAvailableFinal = fxBattleCardAvailable;
        this.setOnMouseClicked((event) -> {
            try {
                if (event.getButton() == MouseButton.PRIMARY) {
                    FxLine lineChoose = getLineChoose(GameUtil.PANEL_ALLIANCE, event.getSceneX(), event.getSceneY());
                    // if it is null, return
                    if (lineChoose == null || !fxBattleCardAvailableFinal.contains(lineChoose)) {
                        return;
                    }
                    for (FxLine battleCardAvailable: fxBattleCardAvailableFinal) {
                        battleCardAvailable.setBorder(Border.EMPTY);
                    }
                    consumer.run(lineChoose.getLine());
                }
                GameUtil.PANEL_ALLIANCE.getChildren().remove(this);
            } catch (RemoteException re) {
                re.printStackTrace();
            }
        });
    }

    private static List<FxBattleCard> getAvailableCards(ChoosingUtil.CardChecker checker) throws RemoteException {
        List<FxBattleCard> fxBattleCards = new ArrayList<>();
        for (FxBattleCard card: GameUtil.PANEL_ALLIANCE.lineEnemy.getListBattleCard()) {
            if (checker.check(card.getCard())) {
                fxBattleCards.add(card);
            }
        }
        for (FxBattleCard card: GameUtil.PANEL_ALLIANCE.lineAlliance.getListBattleCard()) {
            if (checker.check(card.getCard())) {
                fxBattleCards.add(card);
            }
        }
        for (FxBattleCard card: GameUtil.PANEL_ALLIANCE.lineFront.getListBattleCard()) {
            if (checker.check(card.getCard())) {
                fxBattleCards.add(card);
            }
        }
        return fxBattleCards;
    }

    private static List<FxLine> getAvailableLines(ChoosingUtil.LineChecker checker) throws RemoteException {
        List<FxLine> fxLineChecker = new ArrayList<>();
        if (checker.check(GameUtil.PANEL_ALLIANCE.lineFront.getLine())) {
            fxLineChecker.add(GameUtil.PANEL_ALLIANCE.lineFront);
        }
        if (checker.check(GameUtil.PANEL_ALLIANCE.lineAlliance.getLine())) {
            fxLineChecker.add(GameUtil.PANEL_ALLIANCE.lineAlliance);
        }
        if (checker.check(GameUtil.PANEL_ALLIANCE.lineEnemy.getLine())) {
            fxLineChecker.add(GameUtil.PANEL_ALLIANCE.lineEnemy);
        }
        return fxLineChecker;
    }

    private static FxBattleCard getCardChoose(GamePanel gamePanel, double x, double y) {
        FxBattleCard battleCard = null;
        if (x < GamePanel.X_LINES_START || x > GamePanel.X_LINES_END) {
            return null;
        }
        if (y < GamePanel.Y_ENEMY) {
            return null;
        }
        else if (y < GamePanel.Y_FRONT) {
            battleCard = gamePanel.lineEnemy.getBattleCardIn(x - GamePanel.X_LINES_START);
        }
        else if (y < GamePanel.Y_ALLIANCE) {
            battleCard = gamePanel.lineFront.getBattleCardIn(x - GamePanel.X_LINES_START);
        } else if (y < GamePanel.Y_LINES_END) {
            battleCard = gamePanel.lineAlliance.getBattleCardIn(x - GamePanel.X_LINES_START);
        }
        return battleCard;
    }

    private static FxLine getLineChoose(GamePanel panel, double x, double y) {
        FxLine line = null;
        if (x < GamePanel.X_LINES_START || x > GamePanel.X_LINES_END) {
            return null;
        }
        if (y < GamePanel.Y_ENEMY) {
            return null;
        }
        else if (y < GamePanel.Y_FRONT) {
            line = panel.lineEnemy;
        }
        else if (y < GamePanel.Y_ALLIANCE) {
            line = panel.lineFront;
        } else if (y < GamePanel.Y_LINES_END) {
            line = panel.lineAlliance;
        }
        return line;
    }

    private static void setAvailableToChoose(Region region) {
        region.setBorder(ON_CHOOSE);
    }
}
