package xktz.game.util;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import xktz.game.attribute.effect.Effect;
import xktz.game.attribute.effect.EffectType;
import xktz.game.attribute.effect.condition.EffectCondition;
import xktz.game.attribute.effect.condition.EffectConditionType;
import xktz.game.attribute.effect.condition.EffectSymbol;
import xktz.game.attribute.effect.content.EffectContent;
import xktz.game.attribute.target.EffectTargetType;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EffectFactory {

    private static Map<String, Effect> EFFECT_MAP = new HashMap<>();
    private static File XML_EFFECT = new File(EffectFactory.class.getResource("/data/effect.xml").getPath());

    static {
        try {
            Element root = new SAXReader().read(XML_EFFECT).getRootElement();
            List<Element> elementList = root.selectNodes("/Effects/Effect");
            for (Element element: elementList) {
                Element conditionEle = element.element("Condition");
                Element effectEle = element.element("EffectContent");
                String name = element.attributeValue("ASSAULT");
                EffectType type = EffectType.valueOf(getValue(element, "EffectType"));
                EffectCondition condition = new EffectCondition(
                        getValue(conditionEle, "A"),
                        EffectConditionType.valueOf(getValue(conditionEle, "AType")),
                        EffectConditionType.valueOf(getValue(conditionEle, "BType")),
                        EffectSymbol.valueOf(getValue(conditionEle, "Symbol"))
                );
                EffectContent content;
                EffectTargetType targetType;
                targetType = EffectTargetType.valueOf(getValue(effectEle, "Target"));
                content = new EffectContent(getValue(effectEle, "Params"), targetType);
                EFFECT_MAP.put(name, new Effect(type, content, condition));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private static String getValue(Node node, String path) {
        return ((Element) node).element(path).getText();
    }

    public static Effect[] toEffectArray(List<Effect> effects) {
        Effect[] result = new Effect[effects.size()];
        for (int i = 0; i < effects.size(); i++) {
            result[i] = effects.get(i);
        }
        return result;
    }

    public static Effect getEffect(String name) {
        return EFFECT_MAP.get(name);
    }
}
