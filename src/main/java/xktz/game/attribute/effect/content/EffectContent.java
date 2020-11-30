package xktz.game.attribute.effect.content;

import xktz.game.attribute.effect.condition.EffectSituation;
import xktz.game.objects.stage.IBattleStage;
import xktz.game.util.fx.ChoosingUtil;
import xktz.game.objects.GameObject;
import xktz.game.objects.card.soldier.BattleCard;
import xktz.game.objects.stage.BattleStage;
import xktz.game.objects.stage.Line;
import xktz.game.attribute.target.EffectTargetType;
import xktz.game.util.custom.script.CustomEffectFactory;

import java.rmi.RemoteException;

public class EffectContent implements GameObject {
    private String param;
    private EffectTargetType target;

    public static final String SPLITTER = "/";

    public EffectContent(String param, EffectTargetType targetType) {
        this.param = param;
        this.target = targetType;
    }

    public boolean effect(IBattleStage stageMain, BattleCard mainCard, BattleCard enemyCard) throws RemoteException {
        // init three lines
        Line enemyLine = stageMain.getEnemyLine();
        Line allianceLine = stageMain.getAllianceLine();
        Line frontLine = stageMain.getFrontLine();
        // all of the lines
        Line[] allLines = {enemyLine, allianceLine, frontLine};
        // the parameters
        String[] params = param.split(SPLITTER);
        // has active
        boolean hasActive = false;
        // success
        boolean success;
        switch (target) {
            // the addition to all of the cards
            case ALL:
                // if all lines is empty, return that skill is not success
                if (allianceLine.isEmpty() && enemyLine.isEmpty() && frontLine.isEmpty()) {
                    return false;
                }
                // iterate the lines
                for (Line line : allLines) {
                    // set
                    setAdditionsOnLine(params, stageMain, line);
                }
                return true;
            // the addition to enemy line
            case ENEMY_LINE:
                // if enemy line are empty, return that skill is not success
                if (enemyLine.isEmpty()) {
                    return false;
                }
                // add to the enemy line
                setAdditionsOnLine(params, stageMain, enemyLine);
                return true;
            // the addition to alliance line
            case ALLIANCE_LINE:
                // if alliance line is empty, return that skill is not success
                if (allianceLine.isEmpty()) {
                    return false;
                }
                // add to the alliance line
                setAdditionsOnLine(params, stageMain, allianceLine);
                return true;
            // the addition to front line
            case FRONT_LINE:
                // if front line is empty, return that skill is not success
                if (frontLine.isEmpty()) {
                    return false;
                }
                // add to the empty line
                setAdditionsOnLine(params, stageMain, frontLine);
                return true;
            // the addition to enemies
            case ALL_ENEMY:
                // check if the front line is empty, if it is not empty, check if it is owned by enemy
                if (!frontLine.isEmpty()) {
                    // if it is owned by enemy, add all enemies on line
                    if (frontLine.getOwner() != -1 && frontLine.getOwner() == stageMain.getOwner()) {
                        setAdditionsOnLine(params, stageMain, frontLine);
                        hasActive = true;
                    }
                }
                // check if the enemy line is empty, if it is empty and front line is not owned by enemy, return false, else, return true
                if (enemyLine.isEmpty() && !hasActive) {
                    return false;
                } else {
                    setAdditionsOnLine(params, stageMain, enemyLine);
                    return true;
                }
                // the addition to alliance
            case ALL_ALLIANCE:
                // check if the front line is empty, if it is not empty, check if it is owned by alliance
                if (!frontLine.isEmpty()) {
                    // if it is owned by alliance, add all enemies on line
                    if (frontLine.getOwner() != -1 && frontLine.getOwner() == stageMain.getOwner()) {
                        setAdditionsOnLine(params, stageMain, frontLine);
                        hasActive = true;
                    }
                }
                // check if the alliance line is empty, if it is empty and front line is not owned by alliance, return false, else, return true
                if (frontLine.isEmpty() && !hasActive) {
                    return false;
                } else {
                    setAdditionsOnLine(params, stageMain, enemyLine);
                    return true;
                }
                // the addition to one enemy
            case ENEMY:
                // if there is no enemy on the stage, return unsuccess
                success = ChoosingUtil.chooseCardByOwner(enemyLine.getOwner(), (enemyChoose) -> {
                    setAdditions(params, stageMain, enemyChoose);
                });
                return success;
            // the addition to one alliance
            case ALLIANCE:
                // if there is no enemy on the stage, return unsuccess
                success = ChoosingUtil.chooseCardByOwner(allianceLine.getOwner(), (allianceChoose) -> {
                    setAdditions(params, stageMain, allianceChoose);
                });
                return success;
            // draw a card
            case DRAW_CARD:
                drawCard(stageMain);
                return true;
            // drop a card (on stack)
            case DROP_CARD:
                dropCard(stageMain);
                return true;
            // throw a card (on hand)
            case THROW_CARD:
                throwCard(stageMain);
                return true;
            case SELF:
                setAdditions(params, stageMain, mainCard);
                return true;
            case ENEMY_ATTACK:
                setAdditions(params, stageMain, enemyCard);
                return true;
            case CUSTOM:
                return CustomEffectFactory.getEffector(param).effect(stageMain, mainCard, enemyCard);
        }
        return false;
    }

    /**
     * Draw a card
     *
     * @param stage the main stage
     */
    private void drawCard (IBattleStage stage) throws RemoteException {
        // get the number of cards need to draw
        int numDraw = Integer.parseInt(param);
        while (numDraw > 0) {
            // while also have card to draw, draw card
            stage.drawCard();
            numDraw--;
        }
    }

    /**
     * Drop a card
     *
     * @param stage the main stage
     */
    private void dropCard (IBattleStage stage) throws RemoteException {
        // get the number of cards need to drop
        int numDrop = Integer.parseInt(param);
        while (numDrop > 0) {
            // while also have card to drop, drop card
            stage.dropCard();
            numDrop--;
        }
    }

    /**
     * Throw a card
     *
     * @param stage the main stage
     */
    private void throwCard (IBattleStage stage) throws RemoteException {
        // get the number of cards need to throw
        int numThrow = Integer.parseInt(param);
        while (numThrow > 0) {
            stage.throwCard();
            numThrow--;
        }
    }

    /**
     * Give all additions on line
     *
     * @param params the addition parameter
     * @param line   the line
     */
    private void setAdditionsOnLine(String[] params, IBattleStage stage, Line line) throws RemoteException {
        // check if is need to kill all people, if it is, just need to kill all
        if (param.equals("KILL")) {
            line.killAll();
        }
        for (BattleCard card: line.getCards()) {
            setAdditions(params, stage, card);
        }
    }

    /**
     * Give addition to one card
     *
     * @param params the addition parameter
     * @param card   the card
     */
    private void setAdditions(String[] params, IBattleStage stage, BattleCard card) throws RemoteException {
        // check if need to kill
        // else, add or minus the values
        // get the add / minus in the parameters
        int attack = Integer.parseInt(params[0]), hp = Integer.parseInt(params[1]);
        // if need add max hp, add to max
        if (params.length == 3 && params[2].equals("MAX")) {
            // add the max hp
            card.addMaxHp(hp);
        } else {
            // else, add the hp
            card.addHp(hp);
            // if it is minus hp (hp < 0), effect "be attacked"
            if (hp < 0) {
                card.effect(null, EffectSituation.BE_ATTACK);
            }
        }
        card.addAttack(attack);
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public EffectTargetType getTarget() {
        return target;
    }

    public void setTarget(EffectTargetType target) {
        this.target = target;
    }
}