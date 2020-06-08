package xktz.game.objects.card;

import xktz.game.objects.card.soldier.SoldierCard;
import xktz.game.objects.card.soldier.SoldierType;

public class BattleCard {
    SoldierCard soldierCard;

    SoldierType type;
    // can move or not
    boolean canMove;
    // can be attacked
    boolean canBeAttack;
    // can be resurrection
    boolean canResurrection;
    // the hp and attack
    int maxHp;
    int hp;
    int attack;


    public BattleCard(HandCard handCard) {
        soldierCard = (SoldierCard) handCard.getCard();
        initVariables();
    }

    public BattleCard(SoldierCard card) {
        soldierCard = card;
        initVariables();
    }

    private void initVariables() {
        canMove = false;
        // init the hp
        this.hp = soldierCard.getOriginalHP();
        this.maxHp = soldierCard.getOriginalHP();
        // init the attack
        this.attack = soldierCard.getOriginalAttack();
        // check if can be attacked
        this.canBeAttack = soldierCard.isSmoke();
        this.canResurrection = soldierCard.isLifeInDeath();
        // init the soldier type
        this.type = soldierCard.getSoldierType();
        if (isFlash()) {
            canMove = true;
        }
    }

    /**
     * Check if the soldier card is flash
     * @return is flash or not
     */
    public boolean isFlash() {
        return soldierCard.isFlash();
    }

    /**
     * Check if the soldier card is smoke
     * @return is smoke
     */
    public boolean isSmoke() {
        return soldierCard.isSmoke();
    }

    /**
     * Check if the soldier card is life in death
     * @return is life in death or not
     */
    public boolean canResurrection() {
        return canResurrection;
    }

    /**
     * be attacked
     * @param attackHp the hp decrease
     * @return the hp now
     */
    public void beAttacked(int attackHp) {
        this.hp -= attackHp;
        if (this.hp < 0) {
            this.hp = 0;
        }
        if (canResurrection) {
            canResurrection = false;
            this.hp = 1;
        }
    }

    /**
     * be killed
     */
    public void beKilled() {
        beAttacked(this.hp);
    }

    /**
     * add the hp of this card
     * @param addHpVal the hp added
     */
    public void addHp(int addHpVal) {
        this.hp += addHpVal;
        if (this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }
    }

    /**
     * add the max hp of this card
     * @param addMaxHp the max hp added
     */
    public void addMaxHp(int addMaxHp) {
        this.maxHp += addMaxHp;
        this.hp += addMaxHp;
    }

    /**
     * add the attack of this card
     * @param attackAdd the attack add
     */
    public void addAttack(int attackAdd) {
        this.attack += attackAdd;
    }
}
