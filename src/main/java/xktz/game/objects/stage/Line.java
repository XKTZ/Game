package xktz.game.objects.stage;

import xktz.game.callback.Checker;
import xktz.game.objects.GameObject;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.card.soldier.SoldierType;
import xktz.game.serializable.SerializableList;

import java.rmi.RemoteException;
import java.util.List;

public class Line implements GameObject {
    private List<BattleCard> cards;
    private int owner;
    public Line() {
        // init the cards
        cards = new SerializableList<>(6);
    }

    /**
     * Add a soldier into the line
     * @param card the battle card
     */
    public void addSoldier(BattleCard card) {
        if (cards.size() < 6) {
            cards.add(card);
        }
    }

    public List<BattleCard> getCards() {
        return cards;
    }

    public BattleCard get(int index) {
        return cards.get(index);
    }

    /**
     * Add a battle card to line
     * @param card the battle ccard
     */
    public void add(BattleCard card) {
        cards.add(card);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Return the length of this line
     * @return the length of this line
     */
    public int getLength() {
        return cards.size();
    }


    public int getOwner() {
        return owner;
    }

    /**
     * Additions to kill all the people on the line
     */
    public void killAll() throws RemoteException {
        for(BattleCard card: cards) {
            card.beKilled();
        }
    }

    /**
     * Check if the card is on the line
     * @param card the card
     * @return is on or not
     */
    public boolean has(BattleCard card) {
        for (BattleCard cardOn: cards) {
            if (card == cardOn) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if there is the soldier type on this line
     * @param type the type
     * @return is or not
     */
    public boolean has(SoldierType type) {
        for (BattleCard cardOn: cards) {
            if (cardOn.getType() == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if there is the soldier type (and satisfy the requirement) on this line
     * @param type the type
     * @param checker requirement
     * @return is or not
     */
    public boolean has(SoldierType type, Checker<BattleCard> checker) {
        for (BattleCard cardOn: cards) {
            if (cardOn.getType() == type && checker.check(cardOn)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if this line is in decoy
     * @return is or not
     */
    public boolean isInDecoy() {
        for (BattleCard cardOn: cards) {
            if (cardOn.isDecoy() && !cardOn.isSmoke()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if this line is full
     * @return is full or not
     */
    public boolean isFull() {
        return this.getLength() >= 6;
    }

    /**
     * Remove the card
     * @param card the card
     */
    public void remove(BattleCard card) {
        for (int i = 0; i < getLength(); i++) {
            if (this.get(i).equals(card)) {
                this.cards.remove(card);
                return;
            }
        }
    }
}
