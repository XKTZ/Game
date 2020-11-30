package xktz.game.objects.stage;

import xktz.game.attribute.buff.Buff;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.player.CardStack;
import xktz.game.objects.player.HeadQuarter;
import xktz.game.serializable.SerializableList;
import xktz.game.serializable.SerializableMap;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBattleStage extends Remote {
    public void drawCard() throws RemoteException;
    public void dropCard() throws RemoteException;
    public boolean useCard(HandCard card) throws RemoteException;
    public void throwCard() throws RemoteException;
    public Line getEnemyLine() throws RemoteException;
    public Line getFrontLine() throws RemoteException;
    public Line getAllianceLine() throws RemoteException;
    public int getOwner() throws RemoteException;
    public int getNumberAlliance() throws RemoteException;
    public int getNumberEnemy() throws RemoteException;
    public boolean isOwningFrontLine() throws RemoteException;
    public int getBuffAttackOnCard(BattleCard card) throws RemoteException;
    public boolean callCardOnStage(BattleCard card) throws RemoteException;
    public boolean createCardOnLine(Line line, BattleCard card) throws RemoteException;
    public void refresh() throws RemoteException;
    public boolean isStart() throws RemoteException;
    public void turnStart() throws RemoteException;
    public void turnFinish()throws RemoteException;
    public CardStack getAllianceStack()throws RemoteException;
    public CardStack getEnemyStack() throws RemoteException;
    public SerializableList<HandCard> getAllianceHand()throws RemoteException;
    public SerializableList<HandCard> getEnemyHand() throws RemoteException;
    public SerializableMap<BattleCard, Buff[]> getAllianceBuff() throws RemoteException;
    public SerializableMap<BattleCard, Buff[]> getEnemyBuff() throws RemoteException;
    public HeadQuarter getAllianceHeadQuarter() throws RemoteException;
    public HeadQuarter getEnemyHeadQuarter() throws RemoteException;
    public int getMoneyLeft() throws RemoteException;
    public int getMaximumMoney() throws RemoteException;
    public int minusMoneyLeft(int i) throws RemoteException;
}
