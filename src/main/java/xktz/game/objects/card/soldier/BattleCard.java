package xktz.game.objects.card.soldier;

import xktz.game.attribute.buff.Buff;
import xktz.game.attribute.effect.Effect;
import xktz.game.attribute.effect.condition.EffectSituation;
import xktz.game.callback.Checker;
import xktz.game.objects.GameObject;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.Line;
import xktz.game.util.StageUtil;

import java.rmi.RemoteException;

public class BattleCard implements GameObject {
    private SoldierCard soldierCard;

    private SoldierType type;
    // can move or not
    private boolean canMove;
    // can be attacked
    private boolean canBeAttack;
    // can attack
    private boolean canAttack;
    // the hp and attack
    private int maxHp;
    private int hp;
    private int attack;
    // the defense
    private int defense;
    // the owner
    private int owner;
    // the effects
    private Effect[] effects;
    // the buffs
    private Buff[] buffs;
    // is smoke
    private boolean smoke;
    // is decoy
    private boolean decoy;
    // is desperate
    private boolean desperate;

    // the battle stage
    private BattleStage stage;

    public static final String EMPTY_STRING = "";
    public static final String ATTACK_FAIL_MESSAGE_CANNOT_ATTACK = "This soldier cannot attack or have attacked";
    public static final String ATTACK_FAIL_MESSAGE_SMOKE = "You can not attack a unit with smoke";
    public static final String ATTACK_FAIL_MESSAGE_AIR_PROTECTOR = "You can not attack a air bomber with protector";
    public static final String ATTACK_FAIL_MESSAGE_DECOY = "You must attack the unit with decoy on the line";
    public static final String ATTACK_FAIL_TOO_FAR = "The target is too far to attack";
    public static final String ATTACK_FAIL_NO_ATTACK = "The attack of this unit is too low";

    public BattleCard(HandCard handCard, int owner) throws RemoteException {
        soldierCard = (SoldierCard) handCard.getCard();
        this.owner = owner;
        this.stage = StageUtil.getStage(owner);
        initVariables();
    }

    public BattleCard(HandCard handCard, int owner, int hp, int attack) throws RemoteException {
        this.owner = owner;
        soldierCard = (SoldierCard) handCard.getCard();
        this.stage = StageUtil.getStage(owner);
        initVariables(hp, attack);
    }

    public BattleCard(SoldierCard card, int owner) throws RemoteException {
        soldierCard = card;
        this.owner = owner;
        this.stage = StageUtil.getStage(owner);
        initVariables();
    }

    private void initVariables() {
        initVariables(soldierCard.getOriginalHP(), soldierCard.getOriginalAttack());
    }

    private void initVariables(int hpSet, int attackSet) {
        canMove = false;
        // init the hp
        this.hp = hpSet;
        this.maxHp = hpSet;
        // init the attack
        this.attack = attackSet;
        // check if can be attacked
        this.canBeAttack = soldierCard.isSmoke();
        // init the soldier type
        this.type = soldierCard.getSoldierType();
        if (isFlash()) {
            canMove = true;
            canAttack = true;
        } else {
            canMove = false;
            canAttack = false;
        }
        effects = soldierCard.getEffects();
        buffs = soldierCard.getBuffs();
        smoke = soldierCard.isSmoke();
        decoy = soldierCard.isDecoy();
        desperate = soldierCard.isDesperateFight();
        defense = soldierCard.getOriginalDefense();
    }

    /**
     * Effect all effects in the battle card
     */
    public void effect(BattleCard enemy, EffectSituation situation) throws RemoteException {
        switch (situation) {
            // if it is created, do the assault
            case CARD_CREATED:
                for (Effect effect : soldierCard.getAssaultEffects()) {
                    effect.effectAssault(stage, this, situation);
                }
                break;
            // if it is die, do the crash
            case CARD_DIE:
                for (Effect effect : soldierCard.getCrashEffects()) {
                    effect.effectCrash(stage, this, situation);
                }
                // if it is other, do the kep
            default:
                for (Effect effect : soldierCard.getKeepEffects()) {
                    effect.effectKept(stage, this, enemy, situation);
                }
        }
    }

    public AttackResult attack(BattleCard card) throws RemoteException {
        if (!card.canAttack) {
            return new AttackResult(false, ATTACK_FAIL_MESSAGE_CANNOT_ATTACK);
        }
        // if card attack is smoke, return false
        if (!card.isCanBeAttack()) {
            return new AttackResult(false, ATTACK_FAIL_MESSAGE_SMOKE);
        }
        // if self have no attack, return false
        if (attack <= 0) {
            return new AttackResult(false, ATTACK_FAIL_NO_ATTACK);
        }
        // get the line of this card
        Line enemyLineIn = card.getLineIn();
        Line unitLineIn = this.getLineIn();
        // if this card is artillery, attack directly
        // if card is air bomber, attack directly
        if (type == SoldierType.ARTILLERY || type == SoldierType.AIR_BOMBER) {
            // directly attack this card
            this.effect(card, EffectSituation.ATTACK_OTHER_BEFORE);
            card.beAttacked(this.getTotalAttack(), this, true, false);
            this.effect(card, EffectSituation.ATTACK_OTHER_AFTER);
            // let can be attack true
            this.canBeAttack = true;
            return new AttackResult(true, EMPTY_STRING);
        }
        // if this card is air fighter
        if (type == SoldierType.AIR_FIGHTER) {
            // check if the attack target is air bomber
            if (card.getType() == SoldierType.AIR_BOMBER) {
                // if there is a protector (air fighter), return false
                if (enemyLineIn.has(SoldierType.AIR_FIGHTER,
                        (c) -> {
                            return !card.isSmoke();
                        })) {
                    return new AttackResult(false, ATTACK_FAIL_MESSAGE_AIR_PROTECTOR);
                }
            }
            // if there is a decoy unit in the line and the card is not a decoy unit, return false
            if (enemyLineIn.isInDecoy() && !card.isDecoy()) {
                return new AttackResult(false, ATTACK_FAIL_MESSAGE_DECOY);
            }
            attackCard(card);
            // return true;
            return new AttackResult(true, EMPTY_STRING);
        }
        // check the distance between two lines
        // if the distance is 2, return false
        if (stage.getAllianceLine() == unitLineIn && stage.getEnemyLine() == enemyLineIn) {
            return new AttackResult(false, ATTACK_FAIL_TOO_FAR);
        }
        attackCard(card);
        // return true;
        return new AttackResult(true, EMPTY_STRING);
    }

    /**
     * Attack a card
     *
     * @param card the card need to attack
     */
    private void attackCard(BattleCard card) throws RemoteException {
        int attackOther = card.getTotalAttack();
        // be attack, attack other
        this.effect(card, EffectSituation.ATTACK_OTHER_BEFORE);
        card.beAttacked(this.getTotalAttack(), this, true, false);
        beAttacked(attackOther, card, true, true);
        this.effect(card, EffectSituation.ATTACK_OTHER_AFTER);
        // let can be attack true
        this.canBeAttack = true;
    }

    /**
     * Get the line of the card in
     *
     * @return the line in
     */
    public Line getLineIn() throws RemoteException {
        if (stage.getFrontLine().has(this)) {
            return stage.getFrontLine();
        }
        return stage.getAllianceLine();
    }

    /**
     * be killed
     */
    public void beKilled() throws RemoteException {
        if (this.hp > 0) {
            this.hp = 0;
        }
        this.stage.getAllianceBuff().remove(this);
        this.effect(null, EffectSituation.CARD_DIE);
    }

    /**
     * be attacked
     *
     * @param attackHp the hp decrease
     */
    public void beAttacked(int attackHp, boolean initiative) throws RemoteException {
        beAttacked(attackHp, null, initiative);
    }

    /**
     * be attacked
     *
     * @param attackHp the hp decrease
     * @param enemy    the enemy attack
     */
    public void beAttacked(int attackHp, BattleCard enemy, boolean initiative) throws RemoteException {
        beAttacked(attackHp, enemy, true, initiative);
    }

    /**
     * be attacked
     *
     * @param attackHp  the hp decrease
     * @param canEffect can effect (be attacked)
     * @param enemy     the enemy attack
     */
    public void beAttacked(int attackHp, BattleCard enemy, boolean canEffect, boolean initiative) throws RemoteException {
        this.hp -= attackHp - defense;
        this.effect(enemy, EffectSituation.BE_ATTACK);
        if (this.hp <= 0) {
            beKilled();
            if (desperate) {
                desperate = false;
                this.hp = 1;
                if (this.maxHp < 0) {
                    this.maxHp = 1;
                }
            }
        }
    }

    /**
     * move to a line
     *
     * @return success
     */
    public boolean move(Line line) throws RemoteException {
        if (this.soldierCard.getSoldierType() == SoldierType.AIR_BOMBER ||
                this.soldierCard.getSoldierType() == SoldierType.AIR_FIGHTER) {
            return false;
        }
        if (line.isFull() || !canMove || line != stage.getFrontLine() || stage.getMoneyLeft() < soldierCard.getOriginalMoveCost()) {
            return false;
        }
        Line lineIn = getLineIn();
        lineIn.remove(this);
        stage.getFrontLine().add(this);
        stage.minusMoneyLeft(soldierCard.getOriginalMoveCost());
        if (this.soldierCard.getSoldierType() == SoldierType.INFANTRY || this.soldierCard.getSoldierType() == SoldierType.ARTILLERY) {
            this.canAttack = false;
        }
        return true;
    }

    /**
     * add the hp of this card
     *
     * @param addHpVal the hp added
     */
    public void addHp(int addHpVal) throws RemoteException {
        this.hp += addHpVal;
        if (this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }
    }


    /**
     * add the max hp of this card
     *
     * @param addMaxHp the max hp added
     */
    public void addMaxHp(int addMaxHp) throws RemoteException {
        this.maxHp += addMaxHp;
        this.hp += addMaxHp;
        if (this.maxHp <= 0) {
            this.beKilled();
        }
    }


    /**
     * add the attack of this card
     *
     * @param attackAdd the attack add
     */
    public void addAttack(int attackAdd) {
        this.attack += attackAdd;
        if (this.attack < 0) {
            this.attack = 0;
        }
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getTotalAttack() throws RemoteException {
        return stage.getBuffAttackOnCard(this) + this.attack;
    }

    public int getAttack() {
        return attack;
    }

    public int getOwner() {
        return owner;
    }

    public Buff[] getBuffs() {
        return buffs;
    }

    public SoldierType getType() {
        return type;
    }

    /**
     * Check if the soldier card is flash
     *
     * @return is flash or not
     */
    public boolean isFlash() {
        return soldierCard.isFlash();
    }

    /**
     * Check if the soldier card is smoke
     *
     * @return is smoke
     */
    public boolean isSmoke() {
        return smoke;
    }

    /**
     * Check if the soldier card is decoy
     */
    public boolean isDecoy() {
        return decoy;
    }

    public boolean isDesperate() {
        return desperate;
    }

    public boolean isCanBeAttack() {
        return canBeAttack;
    }

    public SoldierCard getSoldierCard() {
        return soldierCard;
    }
}
