package xktz.game.attribute.buff;

import xktz.game.attribute.target.BuffTargetType;
import xktz.game.objects.GameObject;

public class Buff implements GameObject {

    private int addAttack;

    private int other;

    private BuffTargetType targetType;

    public Buff(int addAttack, int other, BuffTargetType targetType) {
        this.addAttack = addAttack;
        this.targetType = targetType;
        this.other = other;
    }

    public int getAddAttack() {
        return addAttack;
    }

    public void setAddAttack(int addAttack) {
        this.addAttack = addAttack;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public BuffTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(BuffTargetType targetType) {
        this.targetType = targetType;
    }
}
