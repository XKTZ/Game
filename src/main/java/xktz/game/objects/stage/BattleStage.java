package xktz.game.objects.stage;

import xktz.game.attribute.buff.Buff;
import xktz.game.attribute.effect.condition.EffectSituation;
import xktz.game.attribute.target.BuffTargetType;
import xktz.game.objects.GameObject;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.card.soldier.SoldierType;
import xktz.game.objects.player.CardStack;
import xktz.game.objects.player.HeadQuarter;
import xktz.game.serializable.SerializableList;
import xktz.game.serializable.SerializableMap;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class BattleStage extends UnicastRemoteObject implements GameObject, IBattleStage {

    private Line enemyLine;
    private Line frontLine;
    private Line allianceLine;

    private CardStack allianceStack;
    private CardStack enemyStack;

    // the hand cards for alliance and enemy
    private SerializableList<HandCard> allianceHand;
    private SerializableList<HandCard> enemyHand;

    // the id of owner
    private int owner;

    private SerializableMap<BattleCard, Buff[]> allianceBuff;
    private SerializableMap<BattleCard, Buff[]> enemyBuff;

    // turn on
    private boolean start;

    public static final int LINE_MAX_LENGTH = 6;


    // the head quarter of enemy and alliance
    HeadQuarter allianceHeadQuarter;
    HeadQuarter enemyHeadQuarter;

    public BattleStage(HandCard[] cardAlliance, HandCard[] cardEnemy, int owner) throws RemoteException {
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
        // the owner
        this.owner = owner;
    }

    public BattleStage(CardStack allianceStack, CardStack enemyStack, Line allianceLine, Line frontLine, Line enemyLine,
                       SerializableList<HandCard> allianceHand, SerializableList<HandCard> enemyHand,
                       SerializableMap<BattleCard, Buff[]> allianceBuff, SerializableMap<BattleCard, Buff[]> enemyBuff,
                       HeadQuarter allianceHeadQuarter, HeadQuarter enemyHeadQuarter,
                       int owner)
            throws RemoteException {
        this.allianceStack = allianceStack;
        this.enemyStack = enemyStack;
        this.enemyLine = enemyLine;
        this.frontLine = frontLine;
        this.allianceLine = allianceLine;
        this.enemyHand = enemyHand;
        this.enemyBuff = enemyBuff;
        this.allianceBuff = allianceBuff;
        this.allianceHand = allianceHand;
        this.allianceHeadQuarter = allianceHeadQuarter;
        this.enemyHeadQuarter = enemyHeadQuarter;
        this.owner = owner;
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

    /**
     * Check the buff effect on the card
     *
     * @param card the card
     * @return attack add
     */
    public int getBuffAttackOnCard(BattleCard card) throws RemoteException {
        SoldierType type = card.getType();
        // the  hp & attack
        int result = 0;
        // the buff map
        Map<BattleCard, Buff[]> buffMap;
        // check the owner of the card
        if (this.owner == card.getOwner()) {
            buffMap = allianceBuff;
        } else {
            buffMap = enemyBuff;
        }
        // iterate the buffs
        for (Map.Entry<BattleCard, Buff[]> entry : buffMap.entrySet()) {
            // iterate the buff in this card
            for (Buff buff : entry.getValue()) {
                // check if it is satisfy
                if (buff.getTargetType().equals(BuffTargetType.ALL)) {
                    // if it is all, directly add
                    result += buff.getAddAttack();
                } else if (buff.getTargetType().equals(BuffTargetType.AIR) &&
                        (type.equals(SoldierType.AIR_BOMBER) || type.equals(SoldierType.AIR_FIGHTER))) {
                    // if the soldier is air and the buff is to air
                    result += buff.getAddAttack();
                } else if (buff.getTargetType().toString().equals(type.toString())) {
                    // if the type of soldier is equals the buff target type
                    result += buff.getAddAttack();
                }
            }
        }
        return result;
    }

    /**
     * Call a card on alliance line
     *
     * @param card the card
     * @return success or not
     */
    public boolean callCardOnStage(BattleCard card) throws RemoteException {
        boolean success = createCardOnLine(allianceLine, card);
        if (success) {
            card.effect(null, EffectSituation.CARD_CREATED);
        }
        return success;
    }

    /**
     * Create a card on stage
     *
     * @param line the line
     * @param card the card
     * @return success or not
     */
    public boolean createCardOnLine(Line line, BattleCard card) throws RemoteException {
        if (line.isFull()) {
            // return false
            return false;
        }
        // create card on stage
        line.add(card);
        return true;
    }

    /**
     * Refresh
     */
    public void refresh() throws RemoteException {
        Line[] lines = {allianceLine, frontLine, enemyLine};
        for (Line line : lines) {
            // iterate the cards
            for (BattleCard card : line.getCards()) {
                // if card has no hp, remove it
                if (card.getHp() <= 0) {
                    line.remove(card);
                }
            }
        }
    }

    public boolean isStart() throws RemoteException {
        return start;
    }

    public void turnStart() throws RemoteException {
        start = true;
    }

    public void turnFinish() throws RemoteException {
        start = false;
    }

    public CardStack getAllianceStack() throws RemoteException {
        return allianceStack;
    }

    public CardStack getEnemyStack() throws RemoteException {
        return enemyStack;
    }

    public SerializableList<HandCard> getAllianceHand() throws RemoteException {
        return allianceHand;
    }

    public SerializableList<HandCard> getEnemyHand() throws RemoteException {
        return enemyHand;
    }

    public SerializableMap<BattleCard, Buff[]> getAllianceBuff() throws RemoteException {
        return allianceBuff;
    }

    public SerializableMap<BattleCard, Buff[]> getEnemyBuff() throws RemoteException {
        return enemyBuff;
    }

    public HeadQuarter getAllianceHeadQuarter() throws RemoteException {
        return allianceHeadQuarter;
    }

    public HeadQuarter getEnemyHeadQuarter() throws RemoteException {
        return enemyHeadQuarter;
    }
}
