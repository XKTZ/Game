package xktz.game.run.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xktz.fx.GamePanel;
import xktz.game.util.fx.FxUtil;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Hello World");
        GamePanel panel = new GamePanel(null);
        FxUtil.setPanel(panel);
        primaryStage.setScene(new Scene(panel, 800, 600));
        primaryStage.show();
    }
}
