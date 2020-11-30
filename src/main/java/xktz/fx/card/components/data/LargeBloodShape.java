package xktz.fx.card.components.data;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class LargeBloodShape extends DataShape {
    private static Image IMAGE_BLOOD = new Image(BloodShape.class.getResourceAsStream("/fx/image/blood.png"));
    private static int WIDTH = 50, HEIGHT = 50;
    private static Font FONT = new Font(30);
    private static Paint PAINT = Paint.valueOf("#ffffff");

    public LargeBloodShape(int num) {
        super(IMAGE_BLOOD, num, WIDTH, HEIGHT, FONT, PAINT);
    }
}
