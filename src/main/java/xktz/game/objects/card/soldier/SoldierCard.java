package xktz.game.objects.card.soldier;

import xktz.game.attribute.buff.Buff;
import xktz.game.objects.card.*;
import xktz.game.attribute.effect.Effect;
import xktz.game.serializable.SerializableList;
import xktz.game.util.game.EffectFactory;

import java.rmi.RemoteException;
import java.util.List;

public class SoldierCard implements Card {

    private String name;

    private Effect[] effects;
    private Effect[] assaultEffects;
    private Effect[] crashEffects;
    private Effect[] keepEffects;
    private Buff[] buffs;

    private boolean flash;
    private boolean smoke;
    private boolean decoy;
    private boolean desperateFight;

    private int originalAttack;
    private int originalHP;
    private int originalCost;
    private int originalMoveCost;
    private int originalDefense;
    private SoldierType soldierType;

    private Rarity rarity;

    private final CardType cardType = CardType.SOLDIER;


    public SoldierCard(String name, Effect[] effects, Buff[] buffs, boolean flash, boolean smoke, boolean decoy, boolean desperateFight,
                       int originalAttack, int originalHP, int originalCost, int moveCost,
                       int originalDefense, SoldierType soldierType, Rarity rarity) {
        // set all variables
        setName(name);
        setEffects(effects);
        setBuffs(buffs);
        setFlash(flash);
        setSmoke(smoke);
        setDecoy(decoy);
        setDesperateFight(desperateFight);
        setOriginalAttack(originalAttack);
        setOriginalHP(originalHP);
        setOriginalCost(originalCost);
        setOriginalMoveCost(moveCost);
        setOriginalDefense(originalDefense);
        setSoldierType(soldierType);
        setRarity(rarity);
    }

    private void classifyEffects() {
        int maxLen = effects.length;
        // create three lists
        List<Effect> keepEffectList = new SerializableList<>(maxLen);
        List<Effect> assaultEffectList = new SerializableList<>(maxLen);
        List<Effect> crashEffectList = new SerializableList<>(maxLen);
        // add the effects for crashing, attacking and keeping
        for (Effect effect : effects) {
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
        if (keepEffectList.size() == 0) {
            keepEffects = new Effect[]{};
        } else {
            keepEffects = EffectFactory.toEffectArray(keepEffectList);
        }
        if (assaultEffectList.size() == 0) {
            assaultEffects = new Effect[]{};
        } else {
            assaultEffects = EffectFactory.toEffectArray(assaultEffectList);
        }
        if (crashEffectList.size() == 0) {
            crashEffects = new Effect[]{};
        } else {
            crashEffects = EffectFactory.toEffectArray(crashEffectList);
        }
    }

    public BattleCard createBattleCard(int owner) throws RemoteException {
        return new BattleCard(this, owner);
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

    public int getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(int cost) {
        this.originalCost = cost;
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

    public boolean isDesperateFight() {
        return desperateFight;
    }

    public void setDesperateFight(boolean desperateFight) {
        desperateFight = desperateFight;
    }

    public Buff[] getBuffs() {
        return buffs;
    }

    public void setBuffs(Buff[] buffs) {
        this.buffs = buffs;
    }

    public Effect[] getAssaultEffects() {
        return assaultEffects;
    }

    public Effect[] getCrashEffects() {
        return crashEffects;
    }

    public Effect[] getKeepEffects() {
        return keepEffects;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOriginalMoveCost() {
        return originalMoveCost;
    }

    public void setOriginalMoveCost(int originalMoveCost) {
        this.originalMoveCost = originalMoveCost;
    }
    public int getOriginalDefense() {
        return originalDefense;
    }

    public void setOriginalDefense(int originalDefense) {
        this.originalDefense = originalDefense;
    }

    @Override
    public int getCost() {
        return originalCost;
    }
}
