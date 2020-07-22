package xktz.game.objects.card.soldier;

import xktz.game.attribute.buff.Buff;
import xktz.game.attribute.effect.Effect;
import xktz.game.attribute.effect.condition.EffectSituation;
import xktz.game.callback.Checker;
import xktz.game.objects.GameObject;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.Line;

import java.rmi.RemoteException;

public class BattleCard implements GameObject {
    private SoldierCard soldierCard;

    private SoldierType type;
    // can move or not
    private boolean canMove;
    // can be attacked
    private boolean canBeAttack;
    // the hp and attack
    private int maxHp;
    private int hp;
    private int attack;
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
    private BattleStage stageCardIn;

    public static final String EMPTY_STRING = "";
    public static final String ATTACK_FAIL_MESSAGE_SMOKE = "You can not attack a unit with smoke";
    public static final String ATTACK_FAIL_MESSAGE_AIR_PROTECTOR = "You can not attack a air bomber with protector";
    public static final String ATTACK_FAIL_MESSAGE_DECOY = "You must attack the unit with decoy on the line";
    public static final String ATTACK_FAIL_TOO_FAR = "The target is too far to attack";
    public static final String ATTACK_FAIL_NO_ATTACK = "The attack of this unit is too low";

    public BattleCard(HandCard handCard, int owner) {
        soldierCard = (SoldierCard) handCard.getCard();
        this.owner = owner;
        initVariables();
    }

    public BattleCard(HandCard handCard, int owner, int hp, int attack) {
        initVariables(hp, attack);
    }

    public BattleCard(SoldierCard card, int owner) {
        soldierCard = card;
        this.owner = owner;
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
        }
        effects = soldierCard.getEffects();
        buffs = soldierCard.getBuffs();
        smoke = soldierCard.isSmoke();
        decoy = soldierCard.isDecoy();
        desperate = soldierCard.isDesperateFight();
    }

    /**
     * Effect all effects in the battle card
     */
    public void effect(BattleStage stage, BattleCard enemy, EffectSituation situation) throws RemoteException {
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

    public AttackResult attack(BattleStage stage, BattleCard card) throws RemoteException {
        // if card attack is smoke, return false
        if (card.isCanBeAttack()) {
            return new AttackResult(false, ATTACK_FAIL_MESSAGE_SMOKE);
        }
        // if self have no attack, return false
        if (attack <= 0) {
            return new AttackResult(false, ATTACK_FAIL_NO_ATTACK);
        }
        // get the line of this card
        Line enemyLineIn = card.getLineIn(stage);
        Line unitLineIn = this.getLineIn(stage);
        // if this card is artillery, attack directly
        // if card is air bomber, attack directly
        if (type == SoldierType.ARTILLERY || type == SoldierType.AIR_BOMBER) {
            // directly attack this card
            card.beAttacked(this.getTotalAttack(), this, true, false);
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
            attackCard(stage, card);
            // return true;
            return new AttackResult(true, EMPTY_STRING);
        }
        // check the distance between two lines
        // if the distance is 2, return false
        if (stage.getAllianceLine() == unitLineIn && stage.getEnemyLine() == enemyLineIn) {
            return new AttackResult(false, ATTACK_FAIL_TOO_FAR);
        }
        attackCard(stage, card);
        // return true;
        return new AttackResult(true, EMPTY_STRING);
    }

    /**
     * Attack a card
     *
     * @param card the card need to attack
     */
    private void attackCard(BattleStage stage, BattleCard card) throws RemoteException {
        int attackOther = card.getTotalAttack();
        // be attack, attack other
        beAttacked(attackOther, card, true, true);
        card.beAttacked(this.getTotalAttack(), this, true, false);
        // let can be attack true
        this.canBeAttack = true;
    }

    /**
     * Get the line of the card in
     *
     * @param stage the stage
     * @return the line in
     */
    public Line getLineIn(BattleStage stage) throws RemoteException {
        if (stage.getFrontLine().has(this)) {
            return stage.getFrontLine();
        }
        return stage.getAllianceLine();
    }

    /**
     * be killed
     */
    public void beKilled() throws RemoteException {
        beAttacked(this.hp, false);
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
        beAttacked(attackHp, enemy, true);
    }

    /**
     * be attacked
     *
     * @param attackHp  the hp decrease
     * @param canEffect can effect (be attacked)
     * @param enemy     the enemy attack
     */
    public void beAttacked(int attackHp, BattleCard enemy, boolean canEffect, boolean initiative) throws RemoteException {
        if (canEffect) {
            if (initiative) {
                this.effect(stageCardIn, enemy, EffectSituation.ATTACK_OTHER_BEFORE);
            }
            this.hp -= attackHp;
            if (initiative) {
                this.effect(stageCardIn, enemy, EffectSituation.ATTACK_OTHER_AFTER);
            }
            if (attackHp > 0) {
                this.effect(stageCardIn, enemy, EffectSituation.BE_ATTACK);
            }
        } else {
            this.hp -= attackHp;
        }
        if (this.hp <= 0) {
            if (desperate) {
                desperate = false;
                this.hp = 1;
                if (this.maxHp < 0) {
                    this.maxHp = 1;
                }
            } else {
                // effect die
                effect(stageCardIn, null, EffectSituation.CARD_DIE);
            }
        }
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
        return stageCardIn.getBuffAttackOnCard(this) + this.attack;
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
}
