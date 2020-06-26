package xktz.game.objects.player;

import xktz.game.objects.GameObject;

public class HeadQuarter implements GameObject {

    private int hpQuarter = 0;
    private int minusHpPerRound = 0;

    public HeadQuarter() {
        hpQuarter = 20;
    }

    /**
     * minus the hp of the head quarter
     * @param val the value need to minus
     */
    public void minusHp(int val) {
        hpQuarter -= val;
    }

    /**
     * minus the hp per round
     */
    public void roundMinusHp() {
        minusHpPerRound ++;
        minusHp(minusHpPerRound);
    }
}
