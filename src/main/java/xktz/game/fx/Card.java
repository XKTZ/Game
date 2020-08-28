package xktz.game.fx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.util.HeadImageUtil;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class Card extends Pane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label name;

    @FXML
    private Blood blood;

    @FXML
    private Attack attack;

    private BattleCard card;


    public Card(BattleCard card) {
//        try {
        // set the card
        this.card = card;
        // set the width and height
        this.setPrefWidth(60);
        this.setPrefHeight(80);
        // set the blood
        blood = new Blood(card.getHp());
        attack = new Attack(card.getAttack());
        this.getChildren().addAll(blood, attack);
        attack.setLayoutX(0);
        attack.setLayoutY(60);
        blood.setLayoutX(40);
        blood.setLayoutY(60);
        // set the image
        this.setBackground(new Background(new BackgroundImage(
                Objects.requireNonNull(HeadImageUtil.getHeadImage(card.getSoldierCard().getName())),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
//        } catch (RemoteException re) {
//            System.exit(1);
//            re.printStackTrace();
//        }
    }

}
