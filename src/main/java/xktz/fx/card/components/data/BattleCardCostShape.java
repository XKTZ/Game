package xktz.fx.card.components.data;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import xktz.fx.card.FxBattleCard;

public class BattleCardCostShape extends Pane {
    private int num;
    private Label label;

    private static final Paint PAINT_BACK_LBL = Paint.valueOf("603b28");
    private static final Background BACK_LBL = new Background(new BackgroundFill(PAINT_BACK_LBL, CornerRadii.EMPTY, Insets.EMPTY));
    private static final Paint PAINT_TEXT_LBL = Paint.valueOf("ffffff");
    private static final Font FONT = new Font(15);
    private static final Paint PAINT_BACK_THIS = Paint.valueOf("888c7b");
    private static final Border BORDER_THIS = new Border(new BorderStroke(PAINT_BACK_THIS, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.EMPTY, Insets.EMPTY));

    public BattleCardCostShape(int num) {
        this.setPrefWidth(100);
        this.setPrefHeight(20);
        // label
        label = new Label(String.valueOf(num));
        label.setLayoutX(0);
        label.setLayoutY(0);
        label.setBackground(BACK_LBL);
        label.setTextFill(PAINT_TEXT_LBL);
        label.setPrefWidth(20);
        label.setPrefHeight(20);
        label.setFont(FONT);
        label.setAlignment(Pos.CENTER);
        // this background
        this.setBorder(BORDER_THIS);
        getChildren().add(label);
    }
}
