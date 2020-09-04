package xktz.game.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import javafx.scene.layout.Pane;
import org.apache.commons.io.FileUtils;
import xktz.game.fx.animation.Animation;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AnimationFactory {
    private static Map<String, Animation> ANIMATION_MAP;
    static {
        try {
            ANIMATION_MAP = JSON.parseObject(FileUtils.readFileToString(
                    new File(AnimationFactory.class.getResource("/animation/animations.json").getPath()), StandardCharsets.UTF_8),
                    new TypeReference<HashMap<String, Animation>>(){});
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    public static Animation getAnimation(String animation) {
        return ANIMATION_MAP.get(animation);
    }

    public static void doAnimationFrom(Pane from, String animationName) {
        ANIMATION_MAP.get(animationName).animateFrom(from);
    }
}
