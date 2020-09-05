package xktz.fx.card;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FxUnknownCard extends ImageView {

    private static Image UNKNOWN_CARD = new Image(FxUnknownCard.class.getResourceAsStream("/fx/image/unknown_card.png"));

    public FxUnknownCard() {
        super(UNKNOWN_CARD);
        this.setFitWidth(100);
        this.setFitHeight(150);
    }
}
