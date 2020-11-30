package xktz.fx.card.components.data;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class AttackShape extends DataShape {

    private static Image IMAGE_ATTACK = new javafx.scene.image.Image(BloodShape.class.getResourceAsStream("/fx/image/attack.png"));
    private static Font FONT = new Font(18);
    private static final int WIDTH = 35, HEIGHT = 35;
    private static final Paint PAINT = Paint.valueOf("#000000");

    public AttackShape(int num) {
        super(IMAGE_ATTACK, Integer.valueOf(num), WIDTH, HEIGHT, FONT, PAINT);
    }
}
