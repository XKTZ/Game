package xktz.game.fx.card.components.data;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class MoveCostShape extends DataShape {
    private static int WIDTH = 20;
    private static int HEIGHT = 20;
    private static Font FONT = new Font(15.0);
    private static Image image = new Image(CostShape.class.getResourceAsStream("/fx/image/movecost.png"));
    private static Paint PAINT = Paint.valueOf("#000000");

    public MoveCostShape(int moveCost) {
        super(image, moveCost, WIDTH, HEIGHT, FONT, PAINT);
    }
}
