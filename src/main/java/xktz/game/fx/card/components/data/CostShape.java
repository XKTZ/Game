package xktz.game.fx.card.components.data;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;


public class CostShape extends DataShape {
    private static Image image = new Image(CostShape.class.getResourceAsStream("/fx/image/cost.png"));
    private static int WIDTH = 30;
    private static int HEIGHT = 30;
    private static Font FONT = new Font(20.0);
    private static Paint PAINT = Paint.valueOf("#000000");

    public CostShape(int num) {
        super(image, num, WIDTH, HEIGHT, FONT, PAINT);
    }
}
