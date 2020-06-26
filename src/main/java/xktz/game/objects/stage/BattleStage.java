package xktz.game.objects.stage;

import xktz.game.attribute.buff.Buff;
import xktz.game.objects.GameObject;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.player.CardStack;
import xktz.game.objects.player.HeadQuarter;
import xktz.game.serializable.SerializableList;
import xktz.game.serializable.SerializableMap;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BattleStage extends UnicastRemoteObject implements GameObject, Remote {

    private Line enemyLine;
    private Line frontLine;
    private Line allianceLine;

    private CardStack allianceStack;
    private CardStack enemyStack;

    // the hand cards for alliance and enemy
    private List<HandCard> allianceHand;
    private List<HandCard> enemyHand;

    // the id of owner
    private int owner;

    private SerializableMap<BattleCard, Buff[]> allianceBuff;
    private SerializableMap<BattleCard, Buff[]> enemyBuff;

    public static final int LINE_MAX_LENGTH = 6;


    // the head quarter of enemy and alliance
    HeadQuarter allianceHeadQuarter;
    HeadQuarter enemyHeadQuarter;

    public BattleStage(HandCard[] cardAlliance, HandCard[] cardEnemy) throws RemoteException {
        // create three lines
        enemyLine = new Line();
        frontLine = new Line();
        allianceLine = new Line();
        // create two hands
        allianceHand = new SerializableList<>();
        enemyHand = new SerializableList<>();
        // create the card stack
        allianceStack = new CardStack(cardAlliance);
        enemyStack = new CardStack(cardEnemy);
        // create buff
        allianceBuff = new SerializableMap<>();
        enemyBuff = new SerializableMap<>();
    }

    public BattleStage(CardStack allianceStack, CardStack enemyStack, Line enemyLine, Line frontLine, Line allianceLine,
                       List<HandCard> allianceHand, List<HandCard> enemyHand, HeadQuarter allianceHeadQuarter, HeadQuarter enemyHeadQuarter)
            throws RemoteException {
        this.allianceStack = allianceStack;
        this.enemyStack = enemyStack;
        this.enemyLine = enemyLine;
        this.frontLine = frontLine;
        this.allianceLine = allianceLine;
        this.enemyHand = enemyHand;
        this.allianceHand = allianceHand;
        this.allianceHeadQuarter = allianceHeadQuarter;
        this.enemyHeadQuarter = enemyHeadQuarter;
    }

    /**
     * Add a handcard into the front line
     * @param handCard the hand card need to add into front line
     * @return adding success or not (the front line is full or not)
     */
    public boolean addCardToAllianceLine(HandCard handCard) throws RemoteException{
        // if the stage is full, return false (unsuccess)
        if (allianceLine.getLength() >= LINE_MAX_LENGTH) {
            return false;
        }
        // get the battle card
        BattleCard battleCard = handCard.createBattleCard();
        // add the card into line
        allianceLine.add(battleCard);
        // get the buffs of battle card
        // add the buffs into map
        allianceBuff.put(battleCard, battleCard.getBuffs());
        return true;
    }

    /**
     * Draw a card (alliance)
     */
    public void drawCard() throws RemoteException {
        HandCard cardDraw = allianceStack.drawCard();
        // if there is no card any more, minus the hp
        if (cardDraw == null) {
            allianceHeadQuarter.roundMinusHp();
        } else {
            allianceHand.add(cardDraw);
        }
    }

    /**
     * Drop a card (alliance)
     */
    public void dropCard() throws RemoteException {
        HandCard cardDraw = allianceStack.drawCard();
        if (cardDraw == null) {
            allianceHeadQuarter.roundMinusHp();
        }
    }

    /**
     * Throw a card (alliance)
     */
    public void throwCard() throws RemoteException {
        if (allianceHand.size() > 0) {
            allianceHand.remove(0);
        }
    }

    public Line getEnemyLine() throws RemoteException {
        return enemyLine;
    }

    public Line getFrontLine() throws RemoteException {
        return frontLine;
    }

    public Line getAllianceLine() throws RemoteException {
        return allianceLine;
    }

    public int getOwner() throws RemoteException {
        return owner;
    }

    public int getNumberAlliance() throws RemoteException {
        int result = 0;
        if (frontLine.getOwner() == owner) {
            result += frontLine.getLength();
        }
        result += allianceLine.getLength();
        return result;
    }

    public int getNumberEnemy() throws RemoteException {
        int result = 0;
        if (frontLine.getOwner() == enemyLine.getOwner()) {
            result += frontLine.getLength();
        }
        result += enemyLine.getOwner();
        return result;
    }

    public boolean isOwningFrontLine() {
        return owner == enemyLine.getOwner();
    }
}
