package xktz.fx.card.components.data;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class BigCostShape extends Pane {
    private static final Image IMAGE = new Image(CostShape.class.getResourceAsStream("/fx/image/cost.png"));
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private static final Font FONT_BIG = new Font(28);
    private static final Font FONT_SMALL = new Font(15);
    private static final Paint PAINT = Paint.valueOf("#000000");

    private final ImageView imageView;
    private final Label lblCost;

    public BigCostShape(int cost, int moveCost) {
        this.setPrefSize(40, 40);
        // set image view
        imageView = new ImageView(IMAGE);
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        // set the labels
        lblCost = new Label(String.valueOf(cost));
        Label lblMoveCost = new Label(String.valueOf(moveCost));
        // init the position
        lblCost.setLayoutX(7.0);
        lblCost.setLayoutY(2.0);
        lblCost.setFont(FONT_BIG);
        lblMoveCost.setLayoutX(25.0);
        lblMoveCost.setLayoutY(18);
        lblMoveCost.setFont(FONT_SMALL);
        getChildren().addAll(imageView, lblCost, lblMoveCost, mk$Label());
    }

    public BigCostShape(int cost) {
        this.setPrefSize(50.0, 50.0);
        // set image view
        imageView = new ImageView(IMAGE);
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        // set the labels
        lblCost = new Label(String.valueOf(cost));
        // init the position
        lblCost.setLayoutX(5.0);
        lblCost.setLayoutY(5.0);
        lblCost.setFont(FONT_BIG);
        getChildren().addAll(imageView, lblCost, mk$Label());
    }

    private static Label mk$Label() {
        Label label = new Label("$");
        label.setFont(FONT_SMALL);
        label.setLayoutX(25.0);
        label.setLayoutY(3);
        return label;
    }

}
