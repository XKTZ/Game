package xktz.game.util;

import xktz.game.objects.stage.BattleStage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

public class StageUtil {
    public static ArrayList<BattleStage> stages = new ArrayList<>(2);

    public static void setStages(BattleStage stage1, BattleStage stage2) {
        stages = new ArrayList<>(Arrays.asList(stage1, stage2));
    }

    public static BattleStage getStage(int owner) throws RemoteException {
        for (BattleStage stage : stages) {
            if (stage.getOwner() == owner) {
                return stage;
            }
        }
        return null;
    }
}
