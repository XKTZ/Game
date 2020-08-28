package xktz.game.attribute.buff;

import xktz.game.attribute.target.BuffTargetType;
import xktz.game.objects.GameObject;
import java.io.File;

public class Buff implements GameObject {

    private int addAttack;

    private BuffTargetType targetType;

    public Buff(int addAttack, BuffTargetType targetType) {
        this.addAttack = addAttack;
        this.targetType = targetType;
    }

    public int getAddAttack() {
        return addAttack;
    }

    public void setAddAttack(int addAttack) {
        this.addAttack = addAttack;
    }

    public BuffTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(BuffTargetType targetType) {
        this.targetType = targetType;
    }
}
