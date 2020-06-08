package xktz.game.objects.card.soldier;

import xktz.game.objects.card.*;
import xktz.game.effect.Effect;

import java.util.ArrayList;
import java.util.List;

public class SoldierCard implements Card {

    private Effect[] effects;
    private Effect[] assaultEffects;
    private Effect[] crashEffects;
    private Effect[] keepEffects;

    private boolean flash;
    private boolean smoke;
    private boolean decoy;
    private boolean lifeInDeath;

    private int originalAttack;
    private int originalHP;
    private int originalCost;
    private SoldierType soldierType;

    private Rarity rarity;

    private final CardType cardType = CardType.SOLDIER;

    private void classifyEffects() {
        int maxLen = effects.length;
        // create three lists
        List<Effect> keepEffectList = new ArrayList<Effect>(maxLen);
        List<Effect> assaultEffectList = new ArrayList<Effect>(maxLen);
        List<Effect> crashEffectList = new ArrayList<Effect>(maxLen);
        // add the effects for crashing, attacking and keeping
        for (Effect effect: effects) {
            switch (effect.getEffectType()) {
                case KEEP:
                    keepEffectList.add(effect);
                    break;
                case ASSAULT:
                    assaultEffectList.add(effect);
                    break;
                case CRASH:
                    crashEffectList.add(effect);
            }
        }
        // set the three effects
        keepEffects = (Effect[]) keepEffectList.toArray();
        assaultEffects = (Effect[]) assaultEffectList.toArray();
        crashEffects = (Effect[]) crashEffectList.toArray();
    }

    public BattleCard createBattleCard() {
        return new BattleCard(this);
    }

    public SoldierType getSoldierType() {
        return soldierType;
    }

    public void setSoldierType(SoldierType soldierType) {
        this.soldierType = soldierType;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public void setEffects(Effect[] effects) {
        this.effects = effects;
        classifyEffects();
    }

    public int getOriginalAttack() {
        return originalAttack;
    }

    public void setOriginalAttack(int originalAttack) {
        this.originalAttack = originalAttack;
    }

    public int getOriginalHP() {
        return originalHP;
    }

    public void setOriginalHP(int originalHP) {
        this.originalHP = originalHP;
    }

    public void setOriginalCost(int cost) {
        this.originalCost = cost;
    }

    public int getOriginalCost() {
        return originalCost;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public boolean isFlash() {
        return flash;
    }

    public void setFlash(boolean flash) {
        flash = flash;
    }

    public boolean isSmoke() {
        return smoke;
    }

    public void setSmoke(boolean smoke) {
        smoke = smoke;
    }

    public boolean isDecoy() {
        return decoy;
    }

    public void setDecoy(boolean decoy) {
        decoy = decoy;
    }

    public boolean isLifeInDeath() {
        return lifeInDeath;
    }

    public void setLifeInDeath(boolean lifeInDeath) {
        lifeInDeath = lifeInDeath;
    }
}
