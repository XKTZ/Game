package xktz.fx.animation.component;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import xktz.fx.GamePanel;
import xktz.game.util.fx.FxUtil;

import java.util.Arrays;

public class MultipleObjectAnimationComponent implements AnimationComponent {

    private int timeKeep;
    private String imageName;
    private AnimationComponentImage image;

    private static int FRAME_DIFFER = 30;


    private static double getSlope(int[] from, int[] to) {
        return (1.0) * (from[1] - to[1]) / (from[0] - to[0]);
    }

    private static double getDegreeArcTan(double slope) {
        return Math.toDegrees(Math.atan(slope));
    }

    private static int[] getAbsolutePosition(Node node) {
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        return new int[]{(int) bounds.getMinX(), (int) bounds.getMinY()};
    }

    @Override
    public void animateMutual(Pane regionFrom, Pane regionTo, int[] startRef, int[] endRef) {
        // get the point
        int[] fromPosition = getAbsolutePosition(regionFrom);
        fromPosition[0] += startRef[0];
        fromPosition[1] += startRef[1];
        int[] toPosition = getAbsolutePosition(regionTo);
        toPosition[0] += endRef[0];
        toPosition[1] += endRef[1];
        // calculate the rotation & slope
        double slope = getSlope(fromPosition, toPosition);
        // get the rotation
        double rotation = getDegreeArcTan(slope);
        // get the image view
        ImageView imageView = new ImageView(image);
        imageView.setRotate(rotation);
        imageView.setLayoutX(fromPosition[0]);
        imageView.setLayoutY(fromPosition[1]);
        // set into the panel
        Platform.runLater(() -> FxUtil.getPanel().getChildren().add(imageView));
        AnimationComponent.transform(fromPosition, toPosition, imageView, timeKeep, FRAME_DIFFER);
    }

    public AnimationComponentImage getImage() {
        return image;
    }

    public void setImage(AnimationComponentImage image) {
        this.image = image;
    }

    public int getTimeKeep() {
        return timeKeep;
    }

    public void setTimeKeep(int timeKeep) {
        this.timeKeep = timeKeep;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
        this.image = new AnimationComponentImage(imageName);
    }
}
