package xktz.game.objects.card.soldier;

public class AttackResult {

    private boolean success;
    private String info;

    /**
     * Create the attack result
     * @param success success or not
     * @param info the info need to say
     */
    public AttackResult(boolean success, String info) {
        this.success = success;
        this.info = info;
    }

    public boolean isSuccess() {
        return success;
    }


    public String getInfo() {
        return info;
    }

}
