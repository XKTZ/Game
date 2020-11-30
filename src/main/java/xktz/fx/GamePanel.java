package xktz.fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import xktz.fx.card.FxBattleCard;
import xktz.fx.card.FxHandCard;
import xktz.fx.card.FxUnknownCard;
import xktz.fx.hand.FxAllianceHand;
import xktz.fx.hand.FxHand;
import xktz.fx.line.FxLine;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.card.soldier.SoldierCard;
import xktz.game.objects.stage.IBattleStage;
import xktz.game.objects.stage.Line;
import xktz.game.util.animation.AnimationFactory;
import xktz.game.util.fx.CardFactory;
import xktz.game.util.fx.ChoosingUtil;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class GamePanel extends Pane {


    public FxLine lineEnemy;

    public FxLine lineFront;

    public FxLine lineAlliance;

    private List<HandCard> allianceCards;
    private List<HandCard> enemyCards;

    public FxHand allianceHand;
    public FxHand enemyHand;
    
    public static double X_LINES_START = 300.0;
    public static double X_LINES_END = 1140.0;
    public static double Y_ENEMY = 90.0;
    public static double Y_FRONT = 250;
    public static double Y_ALLIANCE = 420.0;
    public static double Y_LINES_END = 580.0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private IBattleStage stage;

    private int[] mousePosition = {0, 0};

    private static Background BACK = new Background(new BackgroundImage(
            new Image(GamePanel.class.getResourceAsStream("/fx/image/background.png")),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));

    public GamePanel(IBattleStage stage) throws RemoteException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/GamePanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
        this.setBackground(BACK);
        initHands();
        initLines();
    }

    private void initHands() {
        HandCard card = new HandCard(CardFactory.getSoldierCard("eng_infantry"), null, 0);
        allianceCards = new ArrayList<>(Arrays.asList(new HandCard[]{card, card, card, card,card, card,card,card}));
        enemyCards = new ArrayList<>(Arrays.asList(new HandCard[]{card, card, card, card,card, card,card,card}));
        // init the alliance hand
        allianceHand = new FxAllianceHand(allianceCards);
        allianceHand.setLayoutX(250);
        allianceHand.setLayoutY(600);
        // init the enemy hand
        enemyHand = new FxHand(enemyCards);
        // totolly rotate
        enemyHand.setRotate(180);
        enemyHand.setLayoutX(250);
        enemyHand.setLayoutY(-140);
        getChildren().addAll(allianceHand, enemyHand);
    }

    private void initLines()throws RemoteException {
        Line enemyLine = new Line(), frontLine = new Line(), allianceLine = new Line();
        enemyLine.add(new BattleCard(CardFactory.getSoldierCard("eng_infantry"), 0));
        enemyLine.add(new BattleCard(CardFactory.getSoldierCard("eng_infantry"), 0));
        allianceLine.add(new BattleCard(CardFactory.getSoldierCard("eng_infantry"), 1));
        lineEnemy = new FxLine(enemyLine, this);
        lineEnemy.setLayoutX(300);
        lineEnemy.setLayoutY(85);
        getChildren().add(lineEnemy);
        lineFront = new FxLine(frontLine, this);
        lineFront.setLayoutX(300);
        lineFront.setLayoutY(250);  
        getChildren().add(lineFront);
        lineAlliance = new FxLine(allianceLine, this);
        lineAlliance.setLayoutX(300);
        lineAlliance.setLayoutY(420);
        getChildren().add(lineAlliance);
    }

    private BattleCard test_mkBattleCard() throws RemoteException{
        return new BattleCard(CardFactory.getSoldierCard("eng_infantry"), 0);
    }

    @FXML
    void initialize() {
    }

    public void refresh() {
    }

    public List<HandCard> getAllianceCards() {
        return allianceCards;
    }

    public FxBattleCard findFxBattleCard(BattleCard card) {
        FxBattleCard cardResult;
        if ((cardResult = lineAlliance.findCard(card)) != null) {
            return cardResult;
        } else if ((cardResult = lineFront.findCard(card)) != null) {
            return cardResult;
        } else {
            return lineEnemy.findCard(card);
        }
    }

    public int getOwner() throws RemoteException {
        return stage.getOwner();
    }
}
