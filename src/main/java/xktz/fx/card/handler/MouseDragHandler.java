package xktz.fx.card.handler;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MouseDragHandler implements EventHandler<MouseEvent> {
    private Node node;
    private int oldX;
    private int oldY;

    public MouseDragHandler(Node node) {
        this.node = node;
    }

    @Override
    public void handle(MouseEvent event) {
    }
}
