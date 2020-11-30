package xktz.fx.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import xktz.fx.card.components.data.*;
import xktz.fx.card.components.TypeIcon;
import xktz.game.objects.card.Card;
import xktz.game.objects.card.soldier.SoldierCard;
import xktz.game.util.fx.ImageUtil;
import xktz.game.util.language.LanguageUtil;

public class FxDescriptionCard extends Pane {
    private String name;
    private Card card;

    protected ImageView imageView;
    protected Label lblName;
    protected Label lblDesc;
    protected TypeIcon typeIcon;
    protected LargeBloodShape blood;
    protected LargeAttackShape attack;
    protected BigCostShape costShape;

    public static final int POPUP_CARD_WIDTH = 200;
    public static final int POPUP_CARD_HEIGHT = 300;
    public static final int LBL_NAME_HEIGHT = 40;
    public static final int LBL_DESC_HEIGHT = 110;
    public static final int IMAGE_VIEW_HEIGHT = 160;

    private static Font FONT_DESC = new Font(14);
    private static Font FONT_NAME = Font.font("Microsoft YaHei", FontWeight.BOLD, 18);
    protected static final Paint PAINT_LBL_NAME = Paint.valueOf("#ffffff");

    protected static final Background BACK = new Background(new BackgroundFill(Paint.valueOf("#2AFFD8"), new CornerRadii(5), new Insets(0)));
    protected static final Background BACK_LBL_NAME = new Background(new BackgroundFill(Paint.valueOf("#868a79"), new CornerRadii(0), new Insets(0)));

    public FxDescriptionCard(Card card) {
        this.card = card;
        name = card.getName();
        if (card instanceof SoldierCard) {
            SoldierCard soldierCard =(SoldierCard) card;
            lblName = new Label(LanguageUtil.getCardString(name));
            lblDesc = new Label(LanguageUtil.getDescriptionString(soldierCard.getName()));
            blood = new LargeBloodShape(soldierCard.getOriginalHP());
            attack = new LargeAttackShape(soldierCard.getOriginalAttack());
        } else {
        }
        imageView = new ImageView(ImageUtil.getPaint(name));
        // ui
        initUi();
    }

    private void initUi() {
        // size
        this.setPrefHeight(POPUP_CARD_HEIGHT);
        this.setPrefWidth(POPUP_CARD_WIDTH);
        this.setBackground(BACK);
        this.setBorder(new Border(new BorderStroke(Paint.valueOf("3969cf"),
                BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3), new Insets(-3))));
        initImageView();
        initLblName();
        initLblDesc();
        initTypeIcon();
        initDataIcon();
        addComponents();
    }

    private void initLblName() {
        lblName.setPrefWidth(POPUP_CARD_WIDTH - LBL_NAME_HEIGHT);
        lblName.setPrefHeight(LBL_NAME_HEIGHT);
        lblName.setFont(FONT_NAME);
        lblName.setLayoutX(LBL_NAME_HEIGHT);
        lblName.setLayoutY(0);
        lblName.setAlignment(Pos.CENTER);
        lblName.setTextAlignment(TextAlignment.CENTER);
        lblName.setBackground(BACK_LBL_NAME);
        lblName.setTextFill(PAINT_LBL_NAME);
    }

    private void initImageView() {
        imageView.setFitWidth(POPUP_CARD_WIDTH);
        imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
        imageView.setX(0);
        imageView.setY(LBL_NAME_HEIGHT);
    }

    private void initLblDesc() {
        lblDesc.setPrefWidth(POPUP_CARD_WIDTH-10);
        lblDesc.setPrefHeight(LBL_DESC_HEIGHT);
        lblDesc.setFont(FONT_DESC);
        lblDesc.setLayoutX(5);
        lblDesc.setLayoutY(IMAGE_VIEW_HEIGHT+LBL_NAME_HEIGHT+10);
        lblDesc.setAlignment(Pos.TOP_CENTER);
        lblDesc.setTextAlignment(TextAlignment.CENTER);
        lblDesc.setWrapText(true);
        lblDesc.setTextAlignment(TextAlignment.CENTER);
    }

    private void initTypeIcon() {
        typeIcon = new TypeIcon(((SoldierCard) card).getSoldierType());
        typeIcon.setLayoutX(160);
        typeIcon.setLayoutY(40);
    }

    private void initDataIcon() {
        initCost();
        if (card instanceof SoldierCard) {
            blood.setLayoutX(145);
            blood.setLayoutY(160);
            attack.setLayoutX(5);
            attack.setLayoutY(160);
        }
    }

    private void initCost() {
        if (card instanceof SoldierCard) {
            costShape = new BigCostShape(card.getCost(), ((SoldierCard) card).getOriginalMoveCost());
        } else {
            costShape = new BigCostShape(card.getCost());
        }
        costShape.setLayoutX(0);
        costShape.setLayoutY(0);
    }

    private void addComponents() {
        // add
        this.getChildren().addAll(imageView, lblName, lblDesc, typeIcon, blood, attack, costShape);
    }

}
