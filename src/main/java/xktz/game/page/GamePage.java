package xktz.game.page;

import xktz.game.objects.card.Card;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.IBattleStage;
import xktz.game.serializable.SerializableList;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

// using RMI

public class GamePage implements Page{

    // alliance card list
    private List<Card> allianceCardList;
    // enemy card list
    private List<Card> enemyCardList;
    // battle stage
    private IBattleStage allianceStage;
    private IBattleStage enemyStage;
    // port
    int port;
    // registry
    private Registry registry;

    public GamePage(List<Card> allianceCardList, List<Card> enemyCardList, int port) throws RemoteException, AlreadyBoundException{
        // create
        init(allianceCardList, enemyCardList, port);
    }

    public GamePage(int port) throws RemoteException, NotBoundException {
        // get
        this.registry = LocateRegistry.getRegistry(port);
        // get the battle stage
        this.allianceStage = (IBattleStage) registry.lookup("stage-1");
        this.enemyStage = (IBattleStage) registry.lookup("stage-0");
    }

    public void init(List<Card> allianceCardList, List<Card> enemyCardList, int port) throws RemoteException, AlreadyBoundException {
        this.allianceCardList = allianceCardList;
        this.enemyCardList = enemyCardList;
        this.allianceStage = new BattleStage(mkHandCardList(allianceCardList, 0), mkHandCardList(enemyCardList, 1), 0);
        this.enemyStage = new BattleStage(
                allianceStage.getEnemyStack(), allianceStage.getAllianceStack(),
                allianceStage.getEnemyLine(), allianceStage.getFrontLine(), allianceStage.getAllianceLine(),
                allianceStage.getEnemyHand(), allianceStage.getAllianceHand(),
                allianceStage.getEnemyBuff(), allianceStage.getAllianceBuff(),
                allianceStage.getEnemyHeadQuarter(), allianceStage.getAllianceHeadQuarter(),
                1
        );
        this.port = port;
        initRegistry();
    }

    private void initRegistry() throws RemoteException, AlreadyBoundException {
        registry = LocateRegistry.createRegistry(port);
        // bind the registry (upload the rmi)c
        registry.bind(String.format("stage-%d",allianceStage.getOwner()), allianceStage);
        registry.bind(String.format("stage-%d", enemyStage.getOwner()), allianceStage);
    }

    private HandCard[] mkHandCardList(List<Card> cardList, int owner) {
        HandCard[] cards = new HandCard[cardList.size()];
        for (int i = 0; i < cardList.size(); i++) {
            cards[i] = new HandCard(cardList.get(i), owner);
        }
        return cards;
    }

}
