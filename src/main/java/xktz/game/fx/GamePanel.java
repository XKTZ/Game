package xktz.game.fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import xktz.game.fx.card.FxBattleCard;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.IBattleStage;
import xktz.game.util.CardFactory;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
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
        try {
            for (int i = 0; i < 6; i++) {
                FxBattleCard newCard = new FxBattleCard(new BattleCard(CardFactory.getSoldierCard("eng_infantry"), 1), this);
                list_alliance.getChildren().add(newCard);
            }
        } catch (RemoteException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
        this.stage = stage;
    }

    @FXML
    void initialize() {
    }

    public void refresh() {

    }

}
