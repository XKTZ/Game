package xktz.game.util.fx;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import xktz.fx.ChooserPane;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.Line;

import java.rmi.RemoteException;

public class ChoosingUtil {

    public static boolean chooseLine(int owner, LineConsumer consumer) {
        Pane button = new ChooserPane((line -> line.getOwner() == owner), consumer);
        GameUtil.PANEL_ALLIANCE.getChildren().add(button);
        return true;
    }

    public static boolean chooseCardByOwner(int owner,  CardConsumer after) {
        Pane button = new ChooserPane(card -> card.getOwner() == owner, after);
//        button.setBackground(new Background(new BackgroundFill(Paint.valueOf("000000"), CornerRadii.EMPTY, Insets.EMPTY)));
        GameUtil.PANEL_ALLIANCE.getChildren().add(button);
        return true;
    }

    public interface LineConsumer {
        public void run(Line line) throws RemoteException;
    }
    public interface LineChecker {
        public boolean check(Line line) throws RemoteException;
    }

    public interface CardConsumer {
        public void run(BattleCard card) throws RemoteException;
    }

    public interface CardChecker {
        public boolean check(BattleCard card) throws RemoteException;
    }

}
