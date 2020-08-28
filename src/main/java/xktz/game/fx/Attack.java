package xktz.game.fx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class Attack extends Label {


    private static Image IMAGE_ATTACK;
    private static BackgroundImage BACKGROUND;
    static {
        IMAGE_ATTACK = new javafx.scene.image.Image(Blood.class.getResourceAsStream("/fx/image/attack.png"), 20, 20, false, false);
        BACKGROUND = new BackgroundImage(IMAGE_ATTACK, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    public Attack(int num) {
        super(String.valueOf(num));
        this.setPrefHeight(20);
        this.setPrefWidth(20);
        this.setAlignment(Pos.CENTER);
        this.setTextFill(Color.BLACK);
        this.setBackground(new Background(BACKGROUND));
    }

    public void setValue(int value) {
        this.setText(String.valueOf(value));
    }

}
