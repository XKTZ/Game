package xktz.game.fx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Blood extends Label {
    private static Image IMAGE_BLOOD;
    private static BackgroundImage BACKGROUND;
    static {
        IMAGE_BLOOD = new Image(Blood.class.getResourceAsStream("/fx/image/blood.png"), 20, 20, false, false);
        BACKGROUND = new BackgroundImage(IMAGE_BLOOD, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    public Blood(int num) {
        super(String.valueOf(num));
        this.setPrefHeight(20);
        this.setPrefWidth(20);
        this.setAlignment(Pos.CENTER);
        this.setTextFill(Color.WHITE);
        this.setBackground(new Background(BACKGROUND));
    }

    public void setValue(int value) {
        this.setText(String.valueOf(value));
    }
}
