package xktz.fx.animation.component;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import xktz.fx.GamePanel;

import javax.lang.model.type.NullType;
import java.util.function.Consumer;
public interface AnimationComponent {

    /**
     * Animate mutually
     *
     * @param regionFrom from
     * @param regionTo   to
     * @param startRef   start point's ref
     * @param endRef     end point's ref
     */
    public default void animateMutual(Pane regionFrom, Pane regionTo, int[] startRef, int[] endRef) {
    }

    ;

    /**
     * Animate single
     *
     * @param regionFrom from
     * @param point      the point at
     */
    public default void animateSingle(Pane regionFrom, int[] point) {
    }

    ;

    /**
     * Animate transformation
     *
     * @param node the pane need to transformate
     */
    public default void animateTransform(Node node) {
    };

    public default InfluenceObject getInfluenceObject() {
        return InfluenceObject.SELF;
    }

    public static enum InfluenceObject {
        SELF,
        OTHER
    }

    static void transform(int[] start, int[] end, Node node, int timeKeep, int frameDiffer) {
        int numbers = timeKeep / frameDiffer;
        double xOn = 1.0 * start[0], yOn = 1.0 * start[1];
        double xAdd = 1.0 * (end[0] - start[0]) / numbers;
        double yAdd = 1.0 * (end[1] - start[1]) / numbers;
        for (int i = 0; i < numbers; i++) {
            xOn += xAdd;
            yOn += yAdd;
            double finalXOn = xOn;
            double finalYOn = yOn;
            Platform.runLater(() -> {
                node.setLayoutX(finalXOn);
                node.setLayoutY(finalYOn);
            });
            try {
                Thread.sleep(frameDiffer);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.exit(1);
            }
        }
        node.setLayoutX(end[0]);
        node.setLayoutY(end[1]);
        Platform.runLater(() -> {((Pane) node.getParent()).getChildren().remove(node);});
    }
}
