package xktz.game.objects.stage;

import xktz.game.objects.player.CardStack;

public class BattleStage {

    private Line enemyLine;
    private Line frontLine;
    private Line allianceLine;

    private CardStack cardStack;

    public BattleStage() {
        // create three lines
        enemyLine = new Line();
        frontLine = new Line();
        allianceLine = new Line();
    }

    public Line getEnemyLine() {
        return enemyLine;
    }

    public Line getFrontLine() {
        return frontLine;
    }

    public Line getAllianceLine() {
        return allianceLine;
    }
}
