package xktz.game.util.fx;

import xktz.fx.GamePanel;

public class FxUtil {

    private static GamePanel panel;

    public static void setPanel(GamePanel panel) {
        FxUtil.panel = panel;
    }

    public static GamePanel getPanel() {
        return panel;
    }
}
