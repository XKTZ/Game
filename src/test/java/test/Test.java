package test;


import xktz.game.attribute.effect.content.Effector;
import xktz.game.util.custom.script.CustomEffectFactory;

import java.io.File;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception {
        for (Map.Entry<String, Effector> effectorEntry: CustomEffectFactory.getCustomEffectMap().entrySet()) {
            System.out.println(effectorEntry.getKey());
            effectorEntry.getValue().effect(null, null, null);
        }
    }

    public static File getFile(String path) {
        return new File(Test.class.getResource(path).getPath());
    }
}
