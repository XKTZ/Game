package xktz.game.objects.card;

import xktz.game.attribute.effect.condition.EffectSituation;
import xktz.game.objects.GameObject;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.card.soldier.SoldierCard;
import xktz.game.objects.card.strategy.StrategyCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.IBattleStage;
import xktz.game.util.animation.AnimationUtil;
import xktz.game.util.fx.GameUtil;

import java.rmi.RemoteException;

public class HandCard implements GameObject {
    private Card card;
    private int owner;

    private int hp;
    private int attack;

    private boolean soldierCard;

    private IBattleStage stage;

    public HandCard(Card card, IBattleStage stageIn, int owner) {
        // set the card
        this.card = card;
        // check if it is instance of soldier card
        if (card instanceof SoldierCard) {
            // if it is, set soldier card to true
            this.soldierCard = true;
            // create a soldier card
            SoldierCard soldierCard = (SoldierCard) card;
            // set the hp & attack
            hp = soldierCard.getOriginalHP();
            attack = soldierCard.getOriginalAttack();
        }
        this.stage = stageIn;
    }

    public Card getCard() {
        return card;
    }

    /**
     * Use the card
     *
     * @return the use
     */
    public boolean use() throws RemoteException {
        GameUtil.showCardUse(card);
        AnimationUtil.CONTROLLER_ALLIANCE.dropCard(0, this);
        return true;
//        return stage.useCard(this);
    }

    /**
     * Create a new battle card
     *
     * @return the new battle card
     */
    public BattleCard createBattleCard() throws RemoteException {
        return new BattleCard(this, owner, hp, attack);
    }

    /**
     * Add into the line
     *
     * @return success
     */
    public BattleCard makeBattleCard() throws RemoteException {
        if (stage.getAllianceLine().isFull()) {
            return null;
        }
        BattleCard card = createBattleCard();
        stage.getAllianceLine().add(createBattleCard());
        stage.getAllianceHand().remove(this);
        return card;
    }

    /**
     * Return the name of the card
     *
     * @return name
     */
    public String getName() {
        return card.getName();
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
