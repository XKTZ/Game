package xktz.game.util.custom.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xktz.game.attribute.effect.condition.EffectConditionChecker;
import xktz.game.script.ConditionScript;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CustomConditionFactory implements CustomScriptFactory {
    private static final Map<String, EffectConditionChecker> customConditionMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CustomEffectFactory.class);

    static {
        // init all conditions in condition folder
        // get the condition folder
        File conditionFolder = new File(CustomConditionFactory.class.getResource("/condition").getPath());
        if (!conditionFolder.exists() || !conditionFolder.isDirectory()) {
            // log error
            logger.error("can not find the custom condition folder");
            System.exit(1);
        }
        // iterate to get the file
        for (File file: Objects.requireNonNull(conditionFolder.listFiles())) {
            // check if the file need to disable to see
            if (file.getName().startsWith(DISABLE_SYMBOL)) {
                // continue
                continue;
            }
            // get the condition name
            String conditionName = file.getName().replace(EXTENSION_SCRIPT, "");
            // read the path into the map
            EffectConditionChecker checker = new ConditionScript(file.getAbsolutePath(), conditionName).getChecker();
            // add the condition into map
            customConditionMap.put(conditionName, checker);
        }
    }

    /**
     * Get the effect
     *
     * @param name the name of effect
     * @return effect
     */
    public static EffectConditionChecker getChecker(String name) {
        // if there is no such effect, exit
        if (!customConditionMap.containsKey(name)) {
            logger.error("Lack of effect: {}", name);
            System.exit(1);
        }
        // if there is such effect, return effect
        return customConditionMap.get(name);
    }


    /**
     * Get the entry set in mpa
     * @return the entry set
     */
    public Set<Map.Entry<String, EffectConditionChecker>> getConditionSet() {
        return customConditionMap.entrySet();
    }
}
