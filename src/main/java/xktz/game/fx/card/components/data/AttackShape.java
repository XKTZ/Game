package xktz.game.fx.card.components.data;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;


public class AttackShape extends DataShape {

    private static Image IMAGE_ATTACK;
    private static BackgroundImage BACKGROUND;
    private static Font FONT = new Font(15);
    private static final int WIDTH = 30, HEIGHT = 30;
    private static final Paint PAINT = Paint.valueOf("#000000");

    static {
        IMAGE_ATTACK = new Image(BloodShape.class.getResourceAsStream("/fx/image/attack.png"), WIDTH, HEIGHT, false, false);
    }

    public AttackShape(int num) {
        super(IMAGE_ATTACK, num, WIDTH, HEIGHT, FONT, PAINT);
    }

}
