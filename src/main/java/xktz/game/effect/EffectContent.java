package xktz.game.effect;

import xktz.game.objects.card.BattleCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.Line;

public class EffectContent {
    private int value;
    private String param;
    private TargetType target;

    public void effect(BattleStage stageMain, int... chooseBattleCards) {
        // get the splited parameters
        String[] params = param.split("/");
        switch (target) {
            case ALL:
                // check if it is to kill
                Line[] threeLines = new Line[]{stageMain.getEnemyLine(), stageMain.getFrontLine(), stageMain.getAllianceLine()};
                boolean addMax = params[2].equals("MAX");
                if (params[1].equals("KILL")) {
                    // kill all
                    for (Line lineOn : threeLines) {
                        for (BattleCard card : lineOn.getCards()) {
                            card.beKilled();
                        }
                    }
                } else {
                    // add or minus the values
                    int attackAdding = Integer.parseInt(params[0]), hpAdding = Integer.parseInt(params[1]);
                    // add all
                    for (Line lineOn : threeLines) {
                        for (BattleCard card : lineOn.getCards()) {
                            if (addMax) {
                                card.addMaxHp(hpAdding);
                                card.addAttack(attackAdding);
                            }
                        }
                    }
                }
                break;
            case ALL_ENEMY:
                // get the splited parameters
                Line enemyLine = stageMain.getEnemyLine();
                // check if it is kill
                if (params[1].equals("KILL")) {
                    // kill
                    for (BattleCard card : enemyLine.getCards()) {
                        card.beKilled();
                    }
                } else {
                    // get the values
                    int attackAdding = Integer.parseInt(params[0]), hpAdding = Integer.parseInt(params[1]);
                    if (params[2].equals("MAX")) {
                        for (BattleCard card : enemyLine.getCards()) {
                            card.addAttack(attackAdding);
                            card.addMaxHp(hpAdding);
                        }
                    } else {
                        for (BattleCard card : enemyLine.getCards()) {
                            card.addAttack(attackAdding);
                            card.addHp(hpAdding);
                        }
                    }
                }
                break;
            case ALL_ALLIANCE:
                // get the alliance line
                Line allianceLine = stageMain.getAllianceLine();
                // check if it is kill
                if (params[1].equals("KILL")) {
                    // kill
                    for (BattleCard card : allianceLine.getCards()) {
                        // kill
                        card.beKilled();
                    }
                } else {
                    // get the values
                    int attackAdding = Integer.parseInt(params[0]), hpAdding = Integer.parseInt(params[1]);
                    if (params[2].equals("MAX")) {
                        for (BattleCard card : allianceLine.getCards()) {
                            card.addMaxHp(hpAdding);
                            card.addAttack(attackAdding);
                        }
                    } else {
                        for (BattleCard card : allianceLine.getCards()) {
                            card.addHp(hpAdding);
                            card.addAttack(attackAdding);
                        }
                    }
                }
                break;
            case DRAW_CARD:
        }
    }
}
