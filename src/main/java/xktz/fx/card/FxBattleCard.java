package xktz.fx.card;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import xktz.fx.GamePanel;
import xktz.fx.animation.component.DynamicTransformationAnimation;
import xktz.fx.card.components.AimIcon;
import xktz.fx.card.components.data.AttackShape;
import xktz.fx.card.components.data.BattleCardCostShape;
import xktz.fx.card.components.data.BloodShape;
import xktz.fx.line.FxLine;
import xktz.game.objects.card.soldier.AttackResult;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.util.animation.AnimationUtil;
import xktz.game.util.fx.FxUtil;
import xktz.game.util.fx.GameUtil;
import xktz.game.util.fx.ImageUtil;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Consumer;


public class FxBattleCard extends Pane {

    public static final Paint FX_BATTLE_CARD_BORDER_COLOR = Paint.valueOf("e6e4d8");
    public static final Background FX_BATTLE_CARD_BACKGROUND = new Background(
            new BackgroundFill(FX_BATTLE_CARD_BORDER_COLOR, new CornerRadii(5), Insets.EMPTY)
    );

    private static final AimIcon AIM = new AimIcon();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label name;

    @FXML
    private BloodShape blood;

    @FXML
    private AttackShape attack;

    @FXML
    private BattleCardCostShape moveCostShape;

    private BattleCard card;

    private ImageView imagePane;

    private GamePanel gamePanel;
    private final FxInBattleDescriptionCard popupCard;


    public FxBattleCard(BattleCard card, GamePanel gamePanel) {
        this.setBackground(FX_BATTLE_CARD_BACKGROUND);
//        try {
        // set the card
        this.card = card;
        this.gamePanel = gamePanel;
        // set the width and height
        this.setPrefWidth(120);
        this.setPrefHeight(150);
        initImagePane();
        initData();
        this.getChildren().addAll(moveCostShape, imagePane, attack, blood);
        initHover();
        // if owned, init drag
//        if (card.getOwner() == gamePanel.getOwner()) {
            initDrag();
//        }
//        } catch (RemoteException re) {
//            System.exit(1);
//            re.printStackTrace();
//        }
        popupCard = new FxInBattleDescriptionCard(card);
        popupCard.setLayoutX(30);
        popupCard.setLayoutY(gamePanel.getPrefHeight() / 2 - FxInBattleDescriptionCard.POPUP_CARD_HEIGHT / 2 - 10);
    }

    private void initData() {
        blood = new BloodShape(card.getHp());
        attack = new AttackShape(card.getAttack());
        attack.setLayoutX(15);
        attack.setLayoutY(110);
        blood.setLayoutX(70);
        blood.setLayoutY(110);
        moveCostShape = new BattleCardCostShape(card.getSoldierCard().getOriginalMoveCost());
        moveCostShape.setLayoutX(0);
        moveCostShape.setLayoutY(0);
    }

    private void initImagePane() {
        // set the image
        imagePane = new ImageView();
        imagePane.setImage(ImageUtil.getHeadImage(card.getSoldierCard().getName()));
        imagePane.setFitWidth(120);
        imagePane.setFitHeight(100);
        imagePane.setLayoutX(0);
        imagePane.setLayoutY(20);
    }

    public void initHover() {
        this.setOnMouseEntered((e) -> {
            if (!gamePanel.getChildren().contains(popupCard)) {
                gamePanel.getChildren().add(popupCard);
            }
        });
        this.setOnMouseExited((e) -> {
            gamePanel.getChildren().remove(popupCard);
        });
    }

    public void initDrag() {
        this.setOnMouseDragged((e) -> {
            Bounds bounds = localToScene(getBoundsInLocal());
            double refX = bounds.getMinX();
            double refY = bounds.getMinY();
            if (!gamePanel.getChildren().contains(AIM)) {
                gamePanel.getChildren().add(AIM);
            }
            AIM.setLayoutX(refX + e.getX() - 15);
            AIM.setLayoutY(refY + e.getY() - 15);
        });
        this.setOnMouseReleased((e) -> {
            gamePanel.getChildren().remove(AIM);
            try {
                FxBattleCard battleCard = null;
                if (e.getSceneX() < GamePanel.X_LINES_START || e.getSceneX() > GamePanel.X_LINES_END) {
                    return;
                }
                if (e.getSceneY() < GamePanel.Y_ENEMY) {
                    return;
                }
                // at the enemy line
                else if (e.getSceneY() < GamePanel.Y_FRONT) {
                    battleCard = gamePanel.lineEnemy.getBattleCardIn(e.getSceneX() - GamePanel.X_LINES_START);
                }
                // at the front line (check if need to go into)
                else if (e.getSceneY() < GamePanel.Y_ALLIANCE && !gamePanel.lineFront.getChildren().contains(this)) {
                    if (gamePanel.lineFront.getLine().getOwner() == this.getCard().getOwner()
                            || gamePanel.lineFront.getLine().getOwner() == -1) {
                        moveFront();
                        return;
                    } else {
                        battleCard = gamePanel.lineFront.getBattleCardIn(e.getSceneX() - GamePanel.X_LINES_START);
                    }
                }
                if (battleCard != null && battleCard.getCard().getOwner() != card.getOwner()) {
                    attackOther(battleCard);
                }
            } catch (RemoteException re) {
                re.printStackTrace();
                System.exit(1);
            }
        });
    }

    private void attackOther(FxBattleCard cardOther) throws RemoteException {
        AttackResult attackResult = card.attack(cardOther.card);
//        if (attackResult.isSuccess()) {
        AnimationUtil.CONTROLLER_ALLIANCE.attack(card, cardOther.card);
//            AnimationUtil.CONTROLLER_ENEMY.attack(this.getCard(), cardOther.card);
//        }
    }

    private void moveFront() throws RemoteException {
        AnimationUtil.CONTROLLER_ALLIANCE.move(card);
    }

    public BattleCard getCard() {
        return card;
    }
}
