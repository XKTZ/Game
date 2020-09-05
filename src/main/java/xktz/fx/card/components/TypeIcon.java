package xktz.fx.card.components;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import xktz.game.objects.card.soldier.SoldierType;

public class TypeIcon extends BorderPane {

    private static Image ICON_INFANTRY;
    private static Image ICON_ARMOR;
    private static Image ICON_ARTILLERY;
    private static Image ICON_AIR_FIGHTER;
    private static Image ICON_AIR_BOMBER;
    private static Border BORDER = new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(20), BorderWidths.DEFAULT));

    static {
        ICON_INFANTRY = new Image(TypeIcon.class.getResourceAsStream("/fx/image/icon_infantry.png"));
        ICON_ARMOR = new Image(TypeIcon.class.getResourceAsStream("/fx/image/icon_armor.png"));
        ICON_ARTILLERY = new Image(TypeIcon.class.getResourceAsStream("/fx/image/icon_artillery.png"));
        ICON_AIR_FIGHTER = new Image(TypeIcon.class.getResourceAsStream("/fx/image/icon_air_fighter.png"));
        ICON_AIR_BOMBER = new Image(TypeIcon.class.getResourceAsStream("/fx/image/icon_air_bomber.png"));
    }

    private ImageView imageView = new ImageView();

    public TypeIcon(SoldierType type) {
        Image image;
        switch (type) {
            case INFANTRY:
                image = ICON_INFANTRY;
                break;
            case ARMOR:
                image = ICON_ARMOR;
                break;
            case ARTILLERY:
                image = ICON_ARTILLERY;
                break;
            case AIR_FIGHTER:
                image = ICON_AIR_FIGHTER;
                break;
            case AIR_BOMBER:
                image = ICON_AIR_BOMBER;
                break;
            default:
                image = ICON_INFANTRY;
                break;
        }
        imageView.setImage(image);
        this.setBackground(new Background(new BackgroundFill(Paint.valueOf("ffffff"), new CornerRadii(20), new Insets(0))));
        this.setBorder(BORDER);
        this.setPrefWidth(40);
        this.setPrefHeight(40);
        this.setCenter(imageView);
    }
}
