package xktz.game.fx.card.components.data;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class BloodShape extends DataShape {
    private static Image IMAGE_BLOOD;
    private static BackgroundImage BACKGROUND;
    private static int WIDTH = 30, HEIGHT =30;
    private static Font FONT = new Font(15);
    private static Paint PAINT = Paint.valueOf("#ffffff");
    static {
        IMAGE_BLOOD = new Image(BloodShape.class.getResourceAsStream("/fx/image/blood.png"), WIDTH, HEIGHT, false, false);
        BACKGROUND = new BackgroundImage(IMAGE_BLOOD, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    public BloodShape(int num) {
        super(IMAGE_BLOOD, Integer.valueOf(num), WIDTH, HEIGHT, FONT, PAINT);
    }
}
