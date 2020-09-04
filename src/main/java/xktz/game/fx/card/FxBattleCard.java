package xktz.game.fx.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import xktz.game.fx.GamePanel;
import xktz.game.fx.card.components.data.AttackShape;
import xktz.game.fx.card.components.data.BloodShape;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.util.AnimationFactory;
import xktz.game.util.ImageUtil;

import java.net.URL;
import java.util.ResourceBundle;


public class FxBattleCard extends Pane {

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

    private BattleCard card;

    private Pane imagePane;

    private GamePanel gamePanel;
    private FxInBattleDescriptionCard popupCard;


    public FxBattleCard(BattleCard card, GamePanel gamePanel) {
//        try {
        // set the card
        this.card = card;
        this.gamePanel = gamePanel;
        // set the width and height
        this.setPrefWidth(80);
        this.setPrefHeight(100);
        initImagePane();
        initData();
        this.getChildren().addAll(imagePane, blood, attack);
        initHover();
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
        attack.setLayoutX(0);
        attack.setLayoutY(70);
        blood.setLayoutX(55);
        blood.setLayoutY(70);
    }

    private void initImagePane() {
        // set the image
        imagePane = new Pane();
        imagePane.setPrefWidth(70);
        imagePane.setPrefHeight(100);
        imagePane.setBackground(new Background(new BackgroundImage(
                ImageUtil.getHeadImage(card.getSoldierCard().getName()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        )));
        imagePane.setLayoutX(5);
        imagePane.setLayoutY(0);
    }

    public void initHover() {
        imagePane.setOnMouseEntered((e) -> {
//            gamePanel.getChildren().add(popupCard);
            AnimationFactory.doAnimationFrom(this, "attack");
        });
        imagePane.setOnMouseExited((e) -> {
            gamePanel.getChildren().remove(popupCard);
        });
    }

}
