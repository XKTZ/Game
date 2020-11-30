package xktz.game.util.animation.control;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import xktz.fx.GamePanel;
import xktz.fx.animation.component.Animation;
import xktz.fx.animation.component.AnimationComponent;
import xktz.fx.animation.component.DynamicTransformationAnimation;
import xktz.fx.card.FxBattleCard;
import xktz.fx.card.FxHandCard;
import xktz.fx.hand.FxAllianceHand;
import xktz.fx.hand.FxHand;
import xktz.fx.line.FxLine;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.util.animation.AnimationUtil;
import xktz.game.util.fx.FxUtil;
import xktz.game.util.fx.GameUtil;
import xktz.game.util.game.StageUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NormalAnimationController extends UnicastRemoteObject implements AnimationController {

    private GamePanel panel;

    public NormalAnimationController(GamePanel panel) throws RemoteException {
        this.panel = panel;
    }

    @Override
    public void dropCard(int owner, HandCard card) throws RemoteException {
        // if it is the alliance
//        if (owner == StageUtil.getOwner()) {
        FxHand hand = panel.allianceHand;
        for (Node node : hand.getChildren()) {
            if (((FxHandCard) node).getCard() != card) {
                continue;
            }
            // set the position to
            double rotation = 360 - node.getRotate();
            double scale = Math.tan(Math.toRadians(rotation));
            int xTo = (int) (node.getLayoutX() - (scale * 150));
            int yTo = (int) node.getLayoutY() - 150;
            AnimationComponent animation = new DynamicTransformationAnimation(xTo, yTo, 1000,
                    () -> {
                        Platform.runLater(() -> {
                            panel.getAllianceCards().remove(0);
                            hand.getChildren().remove(node);
                            hand.refresh();
                        });
                    });
            animation.animateTransform(node);
            return;
        }
//        }
    }

    @Override
    public void attack(BattleCard attacker, BattleCard beAttacked) throws RemoteException {
        Animation animationAttacker = getAnimation(attacker);
        FxBattleCard attackerFx = panel.findFxBattleCard(attacker);
        FxBattleCard beAttackedFx = panel.findFxBattleCard(beAttacked);
        new Thread(()->{animationAttacker.animate(attackerFx, beAttackedFx);}).start();
    }

    @Override
    public void move(BattleCard mover) throws RemoteException {
        FxBattleCard card = panel.lineAlliance.findCard(mover) != null ? panel.lineAlliance.findCard(mover):
                panel.lineEnemy.findCard(mover);
        moveIntoLine(card, panel.lineFront);
    }

    private void moveIntoLine(FxBattleCard card, FxLine line) throws RemoteException {
        boolean success = card.getCard().move(panel.lineFront.getLine());
        if (!success) {
            return;
        }
        // the point need to move to
        double[] pointNext = panel.lineFront.getNextAvailablePoint();
        line.getLine().add(card.getCard());
        // the point need to move into in parent
        int[] pointNextInParent = {(int) (pointNext[0]), (int) (pointNext[1])};
        // bounds now
        Bounds boundsNow = card.localToScene(card.getBoundsInLocal());
        // remove the line
        panel.lineAlliance.getChildren().remove(this);
        // remove the card in old line
        panel.lineAlliance.getLine().remove(card.getCard());
        // set the layout in stage
        card.setLayoutX(boundsNow.getMinX());
        card.setLayoutY(boundsNow.getMinY());
        // add it into stage
        panel.getChildren().add(card);
        // set on drag equals null
        card.setOnMouseDragged((e) -> {});
        card.setOnMouseReleased((e) -> {});
        // transformation
        new DynamicTransformationAnimation(pointNextInParent[0], pointNextInParent[1], 750, () -> {
            Platform.runLater(() -> {
                panel.getChildren().remove(this);
                panel.lineAlliance.refresh();
                panel.lineFront.refresh();
            });
        }).animateTransform(card);
    }

    private static Animation getAnimation(BattleCard card) {
        switch (card.getType()) {
            case INFANTRY:
                return AnimationUtil.ANIMATION_ATTACK_INFANTRY;
            case ARMOR:
                return AnimationUtil.ANIMATION_ATTACK_ARMOR;
            case ARTILLERY:
                return AnimationUtil.ANIMATION_ATTACK_ARTILLERY;
            case AIR_FIGHTER:
                return AnimationUtil.ANIMATION_ATTACK_AIR_FIGHTER;
            case AIR_BOMBER:
                return AnimationUtil.ANIMATION_ATTACK_AIR_BOMBER;
        }
        return null;
    }
}
