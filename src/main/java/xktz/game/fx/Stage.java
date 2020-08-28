package xktz.game.fx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import xktz.game.attribute.buff.Buff;
import xktz.game.attribute.effect.Effect;
import xktz.game.objects.card.Rarity;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.card.soldier.SoldierCard;
import xktz.game.objects.card.soldier.SoldierType;
import xktz.game.util.CardFactory;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.ResourceBundle;


public class Stage {

    public Button btn;
    public FlowPane list;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    void initialize() {
        for (int i = 0; i < 10; i++) {
            try {
                Card card = new Card(new BattleCard(CardFactory.getSoldierCard("eng_infantry"), 1));
                list.getChildren().add(card);
            } catch (RemoteException remoteException ){
                remoteException.printStackTrace();
                System.exit(1);
            }
        }
    }

}
