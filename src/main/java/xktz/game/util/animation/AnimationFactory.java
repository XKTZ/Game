package xktz.game.util.animation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.io.FileUtils;
import xktz.fx.animation.component.Animation;
import xktz.fx.animation.component.AnimationComponent;
import xktz.fx.animation.component.MultipleObjectAnimationComponent;
import xktz.fx.animation.component.SingleObjectAnimationComponent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AnimationFactory {
    private static Map<String, SingleObjectAnimationComponent> ANIMATION_SINGLE_MAP;
    private static Map<String, MultipleObjectAnimationComponent> ANIMATION_MUTUAL_MAP;
    private static Map<String, AnimationComponent> ALL_ANIMATION_COMPONENTS;
    private static Map<String, Animation> ANIMATIONS;

    static {
        try {
            ANIMATION_SINGLE_MAP = JSON.parseObject(FileUtils.readFileToString(
                    new File(AnimationFactory.class.getResource("/animation/singleAnimationComponents.json").getPath()), StandardCharsets.UTF_8),
                    new TypeReference<HashMap<String, SingleObjectAnimationComponent>>() {
                    });
            ANIMATION_MUTUAL_MAP = JSON.parseObject(FileUtils.readFileToString(
                    new File(AnimationFactory.class.getResource("/animation/mutualAnimationComponents.json").getPath()), StandardCharsets.UTF_8),
                    new TypeReference<HashMap<String, MultipleObjectAnimationComponent>>() {
                    });
            ALL_ANIMATION_COMPONENTS = new HashMap<>();
            ALL_ANIMATION_COMPONENTS.putAll(ANIMATION_SINGLE_MAP);
            ALL_ANIMATION_COMPONENTS.putAll(ANIMATION_MUTUAL_MAP);
            ANIMATIONS = JSON.parseObject(FileUtils.readFileToString(
                    new File(AnimationFactory.class.getResource("/animation/animations.json").getPath()), StandardCharsets.UTF_8),
                    new TypeReference<HashMap<String, Animation>>(){});
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Get single-object component
     * @param animationComponent name
     * @return component
     */
    public static SingleObjectAnimationComponent getSingleAnimationComponent(String animationComponent) {
        return ANIMATION_SINGLE_MAP.get(animationComponent);
    }

    /**
     * Get mutual-object component
     * @param animationComponent name
     * @return component
     */
    public static MultipleObjectAnimationComponent getMutualAnimationComponent(String animationComponent) {
        return ANIMATION_MUTUAL_MAP.get(animationComponent);
    }

    /**
     * Get component
     * @param animationComponent name
     * @return component
     */
    public static AnimationComponent getAnimationComponent(String animationComponent) {
        return ALL_ANIMATION_COMPONENTS.get(animationComponent);
    }

    /**
     * Get animation
     * @param animation name
     * @return animation
     */
    public static Animation getAnimation(String animation) {
        return ANIMATIONS.get(animation);
    }

}
