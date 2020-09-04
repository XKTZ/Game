package xktz.game.fx.card.components.data;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class BigAttackShape extends DataShape {

    private static Image IMAGE_ATTACK;
    private static BackgroundImage BACKGROUND;
    private static Font FONT = new Font(15);
    private static final int WIDTH = 30, HEIGHT = 30;
    private static final Paint PAINT = Paint.valueOf("#000000");

    static {
        IMAGE_ATTACK = new javafx.scene.image.Image(BloodShape.class.getResourceAsStream("/fx/image/attack.png"), WIDTH, HEIGHT, false, false);
        BACKGROUND = new BackgroundImage(IMAGE_ATTACK, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    public BigAttackShape(int num) {
        super(IMAGE_ATTACK, Integer.valueOf(num), WIDTH, HEIGHT, FONT, PAINT);
    }
}
