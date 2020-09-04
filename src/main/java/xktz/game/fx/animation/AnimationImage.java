package xktz.game.fx.animation;

import javafx.scene.image.Image;

public class AnimationImage extends Image{
    public AnimationImage(String name) {
        super(AnimationImage.class.getResourceAsStream("/animation/images/" + name + ".png"));
    }
}
