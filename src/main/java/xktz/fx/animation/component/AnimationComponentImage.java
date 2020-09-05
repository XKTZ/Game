package xktz.fx.animation.component;

import javafx.scene.image.Image;

public class AnimationComponentImage extends Image{
    public AnimationComponentImage(String name) {
        super(AnimationComponentImage.class.getResourceAsStream("/animation/images/" + name + ".png"));
    }
}
