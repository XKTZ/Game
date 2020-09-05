package xktz.fx.animation.component;

import javafx.application.Platform;
import javafx.scene.Node;

public class TransformationAnimationComponent implements AnimationComponent {
    private int[] end;
    private int timeKeep;
    private static int FRAME_DIFFER = 30;

    @Override
    public void animateTransform(Node node) {
        int[] start = {(int) node.getLayoutX(), (int) node.getLayoutY()};
        AnimationComponent.transform(start, end, node, timeKeep, FRAME_DIFFER);
    }

    public int[] getEnd() {
        return end;
    }

    public void setEnd(int[] end) {
        this.end = end;
    }

    public int getTimeKeep() {
        return timeKeep;
    }

    public void setTimeKeep(int timeKeep) {
        this.timeKeep = timeKeep;
    }
}
