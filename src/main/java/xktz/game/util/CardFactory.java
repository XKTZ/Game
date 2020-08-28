package xktz.game.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import xktz.game.attribute.buff.Buff;
import xktz.game.attribute.effect.Effect;
import xktz.game.attribute.target.BuffTargetType;
import xktz.game.objects.card.Card;
import xktz.game.objects.card.Rarity;
import xktz.game.objects.card.soldier.SoldierCard;
import xktz.game.objects.card.soldier.SoldierType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardFactory {

    private static Map<String, SoldierCard> SOLDIER_MAP = new HashMap<>();
    private static File XML_FILE = new File(CardFactory.class.getResource("/data/soldier.xml").getPath());

    static {
        initSoldierMap();
    }

    public static String getValue(Element element, String name) {
        return element.element(name).getText();
    }

    private static void initSoldierMap() {
        try {
            Document document = new SAXReader().read(XML_FILE);
            Element root = document.getRootElement();
            List<Element> soldiers = root.selectNodes("/Soldiers/Soldier");
            for (Element element : soldiers) {
                String name = element.attributeValue("name");
                int attack = Integer.parseInt(getValue(element, "Attack"));
                int hp = Integer.parseInt(getValue(element, "Hp"));
                int cost = Integer.parseInt(getValue(element, "Cost"));
                int moveCost = Integer.parseInt(getValue(element, "MoveCost"));
                Rarity rarity = Rarity.valueOf(getValue(element, "Rarity"));
                SoldierType type = SoldierType.valueOf(getValue(element, "Type"));
                boolean smoke = Boolean.parseBoolean(getValue(element, "Smoke"));
                boolean decoy = Boolean.parseBoolean(getValue(element, "Decoy"));
                boolean flash = Boolean.parseBoolean(getValue(element, "Flash"));
                boolean desperate = Boolean.parseBoolean(getValue(element, "Desperate"));
                List<Effect> effects = new ArrayList<>();
                for (Element effectElement : (List<Element>) element.element("Effects").elements()) {
                    String nameEffect = effectElement.attributeValue("name");
                    effects.add(EffectFactory.getEffect(nameEffect));
                }
                List<Buff> buffs = new ArrayList<>();
                for (Element elementBuff : (List<Element>) element.element("Buffs").elements()) {
                    BuffTargetType buffType = BuffTargetType.valueOf(getValue(elementBuff, "Target"));
                    int attackAdd = Integer.parseInt(getValue(elementBuff, "Attack"));
                    buffs.add(new Buff(attackAdd, buffType));
                }
                SOLDIER_MAP.put(name, new SoldierCard(name,
                        EffectFactory.toEffectArray(effects), toBuffArray(buffs),
                        flash, smoke, decoy, desperate, attack, hp, cost, moveCost, type, rarity));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static SoldierCard getSoldierCard(String name) {
        return SOLDIER_MAP.get(name);
    }

    private static Buff[] toBuffArray(List<Buff> buffs) {
        Buff[] result = new Buff[buffs.size()];
        for (int i = 0; i < buffs.size(); i++) {
            result [i] = buffs.get(i);
        }
        return result;
    }

}
