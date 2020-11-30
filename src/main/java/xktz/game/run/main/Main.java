package xktz.game.run.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import xktz.fx.ChooserPane;
import xktz.fx.GamePanel;
import xktz.game.objects.stage.BattleStage;
import xktz.game.util.animation.AnimationFactory;
import xktz.game.util.animation.AnimationUtil;
import xktz.game.util.animation.control.AnimationController;
import xktz.game.util.animation.control.NormalAnimationController;
import xktz.game.util.fx.ChoosingUtil;
import xktz.game.util.fx.FxUtil;
import xktz.game.util.fx.GameUtil;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World");
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        GamePanel panel = new GamePanel(null);
        FxUtil.setPanel(panel);
        GameUtil.PANEL_ALLIANCE = panel;
        initAnimations();
        AnimationController animationUtil = new NormalAnimationController(panel);
        AnimationUtil.CONTROLLER_ALLIANCE = animationUtil;
        primaryStage.setScene(new Scene(panel));
        primaryStage.show();
        ChoosingUtil.chooseLine(0, System.out::println);
    }

    private static void initAnimations() {
        AnimationUtil.ANIMATION_ATTACK_INFANTRY = AnimationFactory.getAnimation("attack");
    }
}
