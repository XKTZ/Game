package xktz.fx.animation.component;

import javafx.scene.Node;

import javax.lang.model.type.NullType;
import java.util.function.Consumer;

public class DynamicTransformationAnimation extends TransformationAnimationComponent {


    public DynamicTransformationAnimation(int x, int y, int timeKeep, Runnable afterThat) {
        this.end = new int[]{x, y};
        this.timeKeep = timeKeep;
        this.afterThat = afterThat;
    }

}
