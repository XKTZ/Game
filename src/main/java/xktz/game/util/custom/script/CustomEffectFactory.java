package xktz.game.util.custom.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xktz.game.attribute.effect.content.Effector;
import xktz.game.script.EffectScript;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CustomEffectFactory implements CustomScriptFactory{
    private static final Map<String, Effector> customEffectMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CustomEffectFactory.class);

    static {
        // init all the effects in the effect folder
        // get the effect folder
        File effectFolder = new File(CustomEffectFactory.class.getResource("/effect").getPath());
        if (!effectFolder.exists() || !effectFolder.isDirectory()) {
            // log error
            logger.error("can not find the custom effect folder");
            System.exit(1);
        }
        // iterate to get the files
        for (File file : Objects.requireNonNull(effectFolder.listFiles())) {
            // check if file need to disable to see
            if (file.getName().startsWith(DISABLE_SYMBOL)) {
                // continue
                continue;
            }
            // get the effect name
            String effectName = file.getName().replace(EXTENSION_SCRIPT, "");
            // read the path into the map
            Effector effector = new EffectScript(file.getAbsolutePath(), effectName).getEffector();
            // add the effect into map
            customEffectMap.put(effectName, effector);
        }
    }
    /**
     * Get the effect
     *
     * @param name the name of effect
     * @return effect
     */
    public static Effector getEffector(String name) {
        // if there is no such effect, exit
        if (!customEffectMap.containsKey(name)) {
            logger.error("Lack of effect: {}", name);
            System.exit(1);
        }
        // if there is such effect, return effect
        return customEffectMap.get(name);
    }

    /**
     * Get the entry set in map
     * @return the entry set
     */
    public Set<Map.Entry<String, Effector>> getEffectorSet() {
        return customEffectMap.entrySet();
    }
}
