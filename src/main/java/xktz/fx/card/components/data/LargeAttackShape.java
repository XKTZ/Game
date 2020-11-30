package xktz.fx.card.components.data;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class LargeAttackShape extends DataShape {
    private static Image IMAGE_ATTACK = new javafx.scene.image.Image(BloodShape.class.getResourceAsStream("/fx/image/attack.png"));
    private static BackgroundImage BACKGROUND;
    private static Font FONT = new Font(30);
    private static final int WIDTH = 50, HEIGHT = 50;
    private static final Paint PAINT = Paint.valueOf("#000000");

    public LargeAttackShape(int num) {
        super(IMAGE_ATTACK, Integer.valueOf(num), WIDTH, HEIGHT, FONT, PAINT);
    }
}
