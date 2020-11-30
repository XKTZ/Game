package xktz.fx.animation.component;

import javafx.application.Platform;
import javafx.scene.Node;

import javax.lang.model.type.NullType;
import java.util.function.Consumer;

public class TransformationAnimationComponent implements AnimationComponent {
    protected int[] end;
    protected int timeKeep;
    protected static int FRAME_DIFFER = 30;
    protected Runnable afterThat;

    @Override
    public void animateTransform(Node node) {
        int[] start = {(int) node.getLayoutX(), (int) node.getLayoutY()};
        new Thread(() -> {
            AnimationComponent.transform(start, end, node, timeKeep, FRAME_DIFFER);
            afterThat.run();
        }).start();
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
