package xktz.game.attribute.target;

import xktz.game.objects.GameObject;

public enum EffectTargetType implements GameObject {
    // the addition to all of the cards
    ALL,
    // the addition to enemy line
    ENEMY_LINE,
    // the addition to alliance line
    ALLIANCE_LINE,
    // the addition to front line
    FRONT_LINE,
    // the addition to enemies
    ALL_ENEMY,
    // the addition to alliance
    ALL_ALLIANCE,
    // the addition to one enemy
    ENEMY,
    // the addition to one alliance
    ALLIANCE,
    // draw a card
    DRAW_CARD,
    // drop a card (on stack)
    DROP_CARD,
    // throw a card (on hand)
    THROW_CARD,
    // add the addition self
    SELF,
    // add the addition to enemy attack
    ENEMY_ATTACK,
    // custom
    CUSTOM,
}
