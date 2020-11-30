package xktz.game.objects.card.strategy;

import xktz.game.attribute.effect.Effect;
import xktz.game.attribute.effect.condition.EffectSituation;
import xktz.game.objects.card.Card;
import xktz.game.objects.card.CardType;
import xktz.game.objects.card.Rarity;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.IBattleStage;

import java.rmi.RemoteException;

public class StrategyCard implements Card {

    private int cost;
    private Effect effect;
    private Rarity rarity;
    private String name;
    private String description;

    public StrategyCard(Rarity rarity, String name, String description, int cost, Effect effect) {
        this.cost = cost;
        this.effect = effect;
        this.effect = effect;
        this.description = description;
        this.name = name;
    }

    /**
     * Effect the strategy
     * @param stage the stage
     * @return success
     * @throws RemoteException
     */
    public boolean effect(IBattleStage stage) throws RemoteException {
        return effect.effect(stage, null, null, EffectSituation.STRATEGY);
    }

    @Override
    public int getOriginalCost() {
        return cost;
    }

    @Override
    public CardType getCardType() {
        return CardType.STRATEGY;
    }

    @Override
    public Rarity getRarity() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getCost() {
        return 0;
    }
}
