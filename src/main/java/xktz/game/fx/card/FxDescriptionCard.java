package xktz.game.fx.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import xktz.game.fx.card.components.data.BigAttackShape;
import xktz.game.fx.card.components.data.BigBloodShape;
import xktz.game.fx.card.components.TypeIcon;
import xktz.game.fx.card.components.data.CostShape;
import xktz.game.objects.card.Card;
import xktz.game.objects.card.soldier.SoldierCard;
import xktz.game.util.ImageUtil;
import xktz.game.util.LanguageUtil;

public class FxDescriptionCard extends Pane {
    private String name;
    private Card card;

    protected ImageView imageView;
    protected Label lblName;
    protected Label lblDesc;
    protected TypeIcon typeIcon;
    protected BigBloodShape blood;
    protected BigAttackShape attack;
    protected CostShape costShape;

    public static final int POPUP_CARD_WIDTH = 170;
    public static final int POPUP_CARD_HEIGHT = 240;
    public static final int LBL_NAME_HEIGHT = 15;
    public static final int LBL_DESC_HEIGHT = 65;
    public static final int IMAGE_VIEW_HEIGHT = 160;

    protected static final Background BACK = new Background(new BackgroundFill(Paint.valueOf("#2AFFD8"), CornerRadii.EMPTY, new Insets(0)));

    public FxDescriptionCard(Card card) {
        this.card = card;
        name = card.getName();
        if (card instanceof SoldierCard) {
            SoldierCard soldierCard =(SoldierCard) card;
            lblName = new Label(LanguageUtil.getCardString(name));
            lblDesc = new Label(LanguageUtil.getDescriptionString(soldierCard.getName()));
            blood = new BigBloodShape(soldierCard.getOriginalHP());
            attack = new BigAttackShape(soldierCard.getOriginalAttack());
        } else {
        }
        imageView = new ImageView(ImageUtil.getPaint(name));
        // ui
        initUi();
        this.setBackground(BACK);
    }

    private void initUi() {
        // size
        this.setPrefHeight(POPUP_CARD_HEIGHT);
        this.setPrefWidth(POPUP_CARD_WIDTH);
        this.setBorder(new Border(new BorderStroke(Paint.valueOf("3969cf"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3), new Insets(-3))));
        initImageView();
        initLblName();
        initLblDesc();
        initTypeIcon();
        initDataIcon();
        addComponents();
    }

    private void initLblName() {
        lblName.setPrefWidth(POPUP_CARD_WIDTH);
        lblName.setPrefHeight(LBL_NAME_HEIGHT);
        lblName.setLayoutX(0);
        lblName.setLayoutY(IMAGE_VIEW_HEIGHT);
        lblName.setAlignment(Pos.TOP_CENTER);
        lblName.setTextAlignment(TextAlignment.CENTER);
    }

    private void initImageView() {
        imageView.setFitWidth(POPUP_CARD_WIDTH);
        imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
        imageView.setX(0);
        imageView.setY(0);
    }

    private void initLblDesc() {
        lblDesc.setPrefWidth(POPUP_CARD_WIDTH);
        lblDesc.setPrefHeight(LBL_DESC_HEIGHT);
        lblDesc.setLayoutX(0);
        lblDesc.setLayoutY(IMAGE_VIEW_HEIGHT+LBL_NAME_HEIGHT);
        lblDesc.setAlignment(Pos.TOP_CENTER);
        lblDesc.setTextAlignment(TextAlignment.CENTER);
    }

    private void initTypeIcon() {
        typeIcon = new TypeIcon(((SoldierCard) card).getSoldierType());
        typeIcon.setLayoutX(5);
        typeIcon.setLayoutY(5);
    }

    private void initDataIcon() {
        initCost();
        if (card instanceof SoldierCard) {
            blood.setLayoutX(135);
            blood.setLayoutY(200);
            attack.setLayoutX(5);
            attack.setLayoutY(200);
        } else {

        }
    }

    private void initCost() {
        costShape = new CostShape(card.getCost());
        costShape.setLayoutX(140);
        costShape.setLayoutY(0);
    }

    private void addComponents() {
        // add
        this.getChildren().addAll(imageView, lblName, lblDesc, typeIcon, blood, attack, costShape);
    }

}
