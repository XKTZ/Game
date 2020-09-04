package xktz.game.fx.card.components.data;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class DataShape extends Label {
    private int width;
    private int height;


    private Image image;
    private BackgroundImage backgroundImage;

    public DataShape(Image image, int num, int width, int height, Font font, Paint paint) {
        super(String.valueOf(num));
        this.image = image;
        backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setPrefHeight(width);
        this.setPrefWidth(height);
        this.setAlignment(Pos.CENTER);
        this.setTextFill(Color.WHITE);
        this.setBackground(new Background(backgroundImage));
        this.setFont(font);
        this.setTextFill(paint);
        this.setBorder(new Border(new BorderStroke[]{}));
    }

    public void setValue(int value) {
        this.setText(String.valueOf(value));
    }
}
