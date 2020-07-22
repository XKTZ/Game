package test;


import xktz.game.attribute.effect.content.Effector;
import xktz.game.attribute.target.EffectTargetType;
import xktz.game.objects.card.Card;
import xktz.game.page.GamePage;
import xktz.game.serializable.SerializableList;
import xktz.game.util.custom.script.CustomEffectFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception {
        GamePage page = new GamePage(new SerializableList<Card>(), new SerializableList<Card>(), 8889);
    }

}
