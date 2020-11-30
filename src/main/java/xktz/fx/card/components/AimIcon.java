package xktz.fx.card.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class AimIcon extends ImageView {

    private static Image IMAGE_AIM = new Image(AimIcon.class.getResourceAsStream("/fx/image/aim.png"));

    public AimIcon() {
        super(IMAGE_AIM);
    }
}
