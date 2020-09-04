package xktz.game.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import xktz.game.util.language.Language;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LanguageUtil {
    private static Map<String, String> STR_CARD = new HashMap<>();
    private static Map<String, String> STR_DESC = new HashMap<>();
    private static Language language = Language.EN;
    static {
        String json = "";
        try {
            json = FileUtils.readFileToString(new File(LanguageUtil.class.getResource("/language/EN.json").getPath()), StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            System.exit(1);
            ioe.printStackTrace();
        }
        inputWords(json);
    }

    private static void inputWords(String json) {
        JSONObject obj = JSON.parseObject(json);
        for (Map.Entry<String, Object> entry: obj.entrySet()) {
            Map<String, String> mapOperation = null;
            switch (entry.getKey()) {
                case "card":
                    mapOperation = STR_CARD;
                    break;
                case "description":
                    mapOperation = STR_DESC;
                    break;
            }
            for (Map.Entry<String, Object> entryWords: ((JSONObject) entry.getValue()).entrySet()) {
                mapOperation.put(entryWords.getKey(), entryWords.getValue().toString());
            }
        }
    }

    public static String getCardString(String value) {
        return STR_CARD.get(value);
    }

    public static String getDescriptionString(String value) {
        return STR_DESC.get(value);
    }
}
