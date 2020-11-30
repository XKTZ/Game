package xktz.fx.card.components.data;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class DataShape extends Pane {
    private int width;
    private int height;


    private Image image;
    private Label label;
    private ImageView imageView;

    public DataShape(Image image, int num, int width, int height, Font font, Paint paint) {
        // set height and width
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        // set the label
        label = new Label(String.valueOf(num));
        // set the font
        label.setFont(font);
        label.setTextFill(paint);
        label.setPrefWidth(width);
        label.setPrefHeight(height);
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(0);
        label.setLayoutY(0);
        // set the image
        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        // add all
        getChildren().addAll(imageView, label);
    }

    public void setValue(int value) {
        this.label.setText(String.valueOf(value));
    }
}
