package xktz.fx.card.components.data;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class BigBloodShape extends DataShape{
    private static Image IMAGE_BLOOD = new Image(BloodShape.class.getResourceAsStream("/fx/image/blood.png"));
    private static int WIDTH = 35, HEIGHT = 35;
    private static Font FONT = new Font(20);
    private static Paint PAINT = Paint.valueOf("#ffffff");

    public BigBloodShape(int num) {
        super(IMAGE_BLOOD, num, WIDTH, HEIGHT, FONT, PAINT);
    }
}
