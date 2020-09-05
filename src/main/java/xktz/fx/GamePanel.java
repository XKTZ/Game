package xktz.fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import xktz.fx.card.FxBattleCard;
import xktz.fx.card.FxUnknownCard;
import xktz.fx.hand.FxHand;
import xktz.fx.line.FxLine;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.IBattleStage;
import xktz.game.objects.stage.Line;
import xktz.game.util.animation.AnimationFactory;
import xktz.game.util.fx.CardFactory;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class GamePanel extends Pane {


    @FXML
    private FlowPane list_enemy;

    @FXML
    private FlowPane list_front;

    @FXML
    private FlowPane list_alliance;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private IBattleStage stage;

    private int[] mousePosition = {0, 0};


    public GamePanel(IBattleStage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/GamePanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
        Line line = new Line();
        FxLine fxLine = new FxLine(line, this);
        fxLine.setLayoutX(0);
        fxLine.setLayoutY(0);
        try {
            for (int i = 0; i < 6; i++) {
                line.add(new BattleCard(CardFactory.getSoldierCard("eng_infantry"), 1));
            }
            this.getChildren().add(fxLine);
            fxLine.refresh();
        } catch (RemoteException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
//        List<HandCard> cards = Arrays.asList(new HandCard[]{null, null});
//        FxHand hand = new FxHand(cards);
//        hand.setLayoutX(150);
//        hand.setLayoutY(150);
//        getChildren().addAll(hand);
        FxUnknownCard unknownCard = new FxUnknownCard();
        unknownCard.getTransforms().add(new Rotate(30));
        unknownCard.setLayoutX(200);
        unknownCard.setLayoutY(200);
        FxUnknownCard unknownCard1 = new FxUnknownCard();
        unknownCard1.setLayoutX(200);
        unknownCard1.setLayoutY(200);
        this.getChildren().addAll(unknownCard1, unknownCard);
    }


    @FXML
    void initialize() {
    }

    public void refresh() {

    }

}
